
package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.dataaccess.DataBaseConnection;
import mx.fei.ca.domain.Book;
import mx.fei.ca.domain.ChapterBook;
import mx.fei.ca.domain.Evidence;
import mx.fei.ca.domain.InvestigationProject;

/**
 * Clase para representar el Objeto de acceso a datos de una evidencia de tipo capítulo de libro
 * @author Gloria Mendoza González
 * @version 17-06-2021
 */

public class ChapterBookDAO implements IChapterBookDAO{
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;  
    
    /**
     * Constructor para la creación de un ChapterBookDAO permitiendo también la creación de la conexión a la base de datos
     */
 
    public ChapterBookDAO(){
        dataBaseConnection = new DataBaseConnection();       
    } 
    
    /**
     * Método que guarda una nueva evidencia de tipo capítulo de libro en la base de datos y retorna su identificador
     * @param chapterBook Define el capítulo de libro a guardar
     * @return Booleano con el resultado de guardado, devuelve true si guardó, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    @Override
    public int saveAndReturnIdNewChapterBook (ChapterBook chapterBook) throws BusinessConnectionException{
       String sql = "INSERT INTO chapterbook (impactCA, titleEvidence, author, homepage, endPage, ISBN, idProject, curp, chapterNumber)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int idChapterBookResult = 0;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, chapterBook.getImpactCA());
            preparedStatement.setString(2, chapterBook.getTitleEvidence()); 
            preparedStatement.setString(3, chapterBook.getAuthor());           
            preparedStatement.setInt(4, chapterBook.getHomePage());
            preparedStatement.setInt(5, chapterBook.getEndPage()); 
            preparedStatement.setString(6, chapterBook.getBook().getIsbn()); 
            preparedStatement.setInt(7, chapterBook.getInvestigationProject().getIdProject()); 
            preparedStatement.setString(8, chapterBook.getCurp());  
            preparedStatement.setInt(9, chapterBook.getChapterNumber());            
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();            
            if(resultSet.next()){
                idChapterBookResult = resultSet.getInt(1);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return idChapterBookResult;
    }
    
    /**
     * Método que modifica una evidencia de tipo capítulo de libro existente en la base de datos
     * @param chapterBook Define el capítulo de libro modificado
     * @param idChapterBook Define el número de identificador del capítulo de libro a modificar
     * @return Booleano con el resultado de modificación, devuelve true si guardó, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    @Override
    public boolean updatedChapterBook (ChapterBook chapterBook, int idChapterBook) throws BusinessConnectionException{
       String sql = "UPDATE chapterBook SET impactCA = ?, titleEvidence = ?, author = ?, homepage =?, endPage = ?, ISBN = ?, idProject = ?, chapterNumber = ?, curp = ? "
               + " WHERE idChapterBook = ? ";
        boolean updateResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, chapterBook.getImpactCA());
            preparedStatement.setString(2, chapterBook.getTitleEvidence()); 
            preparedStatement.setString(3, chapterBook.getAuthor());
            preparedStatement.setInt(4, chapterBook.getHomePage());
            preparedStatement.setInt(5, chapterBook.getEndPage()); 
            preparedStatement.setString(6, chapterBook.getBook().getIsbn());
            preparedStatement.setInt(7, chapterBook.getInvestigationProject().getIdProject()); 
            preparedStatement.setInt(8, chapterBook.getChapterNumber()); 
            preparedStatement.setString(9, chapterBook.getCurp());            
            preparedStatement.setInt(10, idChapterBook);          
            preparedStatement.executeUpdate();
            updateResult = true;
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return updateResult;
    }
    
    /**
     * Método que recupera de la base de datos las evidencias de tipo capítulo de libro que impactan al CA
     * @return ArrayList con evidencias de tipo capítulo de libro que impactan al CA
     * @throws BusinessConnectionException 
     */
    
    @Override
    public ArrayList<ChapterBook> findChapterBooksByPositiveImpactCA() throws BusinessConnectionException {
        String sql = "SELECT * FROM chapterbook WHERE impactCA = 'SI'";
        ArrayList<ChapterBook> chapterBooks = new ArrayList<>();
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Evidence evidence;
                ChapterBook chapterBook;
                int idChapterBook = resultSet.getInt("idChapterBook");
                String impactCA = resultSet.getString("impactCA");
                String titleEvidence = resultSet.getString("titleEvidence");
                String author = resultSet.getString("author");                 
                int homePage = resultSet.getInt("homepage");
                int endPage = resultSet.getInt("endPage");
                int chapterNumber = resultSet.getInt("chapterNumber");                                  
                evidence = new Evidence(impactCA, titleEvidence, author);
                chapterBook = new ChapterBook(evidence, chapterNumber, homePage, endPage);
                chapterBook.setIdChapterBook(idChapterBook);
                chapterBooks.add(chapterBook);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return chapterBooks;
    }
    
    /**
     * Método que recupera de la base de datos las últimas dos evidencias de tipo capítulo de libro de un integrante
     * @param curp Define la curp del integrante del cual se quiere recuperar los capítulos de libros
     * @return ArrayList con máximo dos capítulos de libros
     * @throws BusinessConnectionException 
     */
    
    @Override
    public ArrayList<ChapterBook> findLastTwoChapterBooksByCurpIntegrant(String curp) throws BusinessConnectionException {
        String sql = "SELECT * FROM chapterbook WHERE curp = ? ORDER by chapterNumber DESC LIMIT 2";
        ArrayList<ChapterBook> chapterBooks = new ArrayList<>();
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, curp);           
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Evidence evidence;
                ChapterBook chapterBook;
                int idChapterBook = resultSet.getInt("idChapterBook");                
                String impactCA = resultSet.getString("impactCA");
                String titleEvidence = resultSet.getString("titleEvidence");
                String author = resultSet.getString("author");                 
                int homePage = resultSet.getInt("homepage");
                int endPage = resultSet.getInt("endPage");
                int chapterNumber = resultSet.getInt("chapterNumber");  
                String isbn = resultSet.getString("ISBN");
                int idProject = resultSet.getInt("idProject");   
                evidence = new Evidence(impactCA, titleEvidence, author);
                chapterBook = new ChapterBook(evidence, chapterNumber, homePage, endPage);
                InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
                InvestigationProject investigationProject = investigationProjectDAO.findInvestigationProjectById(idProject); 
                chapterBook.setInvestigationProject(investigationProject); 
                chapterBook.setIdChapterBook(idChapterBook);                
                BookDAO bookDAO = new BookDAO();
                Book book = bookDAO.findBookByIsbn(isbn);                
                chapterBook.setBook(book);
                chapterBooks.add(chapterBook);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return chapterBooks;
    }
    
    /**
     * Método que recupera de la base de datos el número de evidencias de tipo capítulo de libro de un integrante
     * @param curp Define la curp del integrante del cual se quiere recuperar los capítulos de libros
     * @return int con el número total de evidencias de tipo capítulo de libro de un integrante
     * @throws BusinessConnectionException 
     */
    
    @Override
    public int findChapterBooksByCurpIntegrant(String curp) throws BusinessConnectionException {
        String sql = "SELECT * FROM chapterbook WHERE curp = ?";
        ArrayList<ChapterBook> chapterBooks = new ArrayList<>();
        int numberChapterBooks = 0;        
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, curp);           
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Evidence evidence;
                ChapterBook chapterBook;
                int idChapterBook = resultSet.getInt("idChapterBook");                
                String impactCA = resultSet.getString("impactCA");
                String titleEvidence = resultSet.getString("titleEvidence");
                String author = resultSet.getString("author");                 
                int homePage = resultSet.getInt("homepage");
                int endPage = resultSet.getInt("endPage");
                int chapterNumber = resultSet.getInt("chapterNumber");  
                String isbn = resultSet.getString("ISBN");
                int idProject = resultSet.getInt("idProject");   
                evidence = new Evidence(impactCA, titleEvidence, author);
                chapterBook = new ChapterBook(evidence, chapterNumber, homePage, endPage);
                InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
                InvestigationProject investigationProject = investigationProjectDAO.findInvestigationProjectById(idProject); 
                chapterBook.setInvestigationProject(investigationProject); 
                chapterBook.setIdChapterBook(idChapterBook);                
                BookDAO bookDAO = new BookDAO();
                Book book = bookDAO.findBookByIsbn(isbn);                
                chapterBook.setBook(book);
                chapterBooks.add(chapterBook);
            }
            numberChapterBooks = chapterBooks.size();             
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return numberChapterBooks;
    }    
    
    /**
     * Método que recupera de la base de datos las evidencias de tipo capítulo de acuerdo a las iniciales del título
     * @param initialesTitleChapter Define las iniciales del título del capítulo de libro a recuperar
     * @param curp Define la curp del integrante del cual se quiere recuperar los capítulos de libros
     * @return ArrayList con capítulos de libro que coincidieron
     * @throws BusinessConnectionException 
     */
    
    @Override
    public ArrayList<ChapterBook> findChapterBookByCurpIntegrantInitialesOfTitle(String initialesTitleChapter, String curp) throws BusinessConnectionException {
        String sql = "SELECT * FROM chapterBook WHERE titleEvidence LIKE CONCAT('%',?,'%') AND curp = ?";
        ArrayList<ChapterBook> chapterBooks = new ArrayList<>();
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, initialesTitleChapter);            
            preparedStatement.setString(2, curp);           
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Evidence evidence;
                ChapterBook chapterBook;
                int idChapterBook = resultSet.getInt("idChapterBook");                
                String impactCA = resultSet.getString("impactCA");
                String titleEvidence = resultSet.getString("titleEvidence");
                String author = resultSet.getString("author");                 
                int homePage = resultSet.getInt("homepage");
                int endPage = resultSet.getInt("endPage");
                int chapterNumber = resultSet.getInt("chapterNumber");  
                String isbn = resultSet.getString("ISBN");
                int idProject = resultSet.getInt("idProject");   
                evidence = new Evidence(impactCA, titleEvidence, author);
                chapterBook = new ChapterBook(evidence, chapterNumber, homePage, endPage);
                InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
                InvestigationProject investigationProject = investigationProjectDAO.findInvestigationProjectById(idProject); 
                chapterBook.setInvestigationProject(investigationProject); 
                chapterBook.setIdChapterBook(idChapterBook);                
                BookDAO bookDAO = new BookDAO();
                Book book = bookDAO.findBookByIsbn(isbn);                
                chapterBook.setBook(book);
                chapterBooks.add(chapterBook);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return chapterBooks;
    }    
    
    /**
     * Método que verifica si existe el título de un capítulo de libro en la base de datos
     * @param titleChapterBook Define el título a verificar existencia
     * @return Booleano con el resultado de verificación, devuelve true si existe, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */

    @Override
    public boolean existsChapterBookTitle(String titleChapterBook) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM chapterBook WHERE titleEvidence = ?";
        boolean exists = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, titleChapterBook);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                exists = true;
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return exists;
    }
    
    /**
     * Método que verifica si existe el número de un capítulo de libro de un libro específico en la base de datos
     * @param numberChapter Define el número de capítulo a verificar existencia
     * @param isbn Define el ISBN del libro a verificar
     * @return Booleano con el resultado de verificación, devuelve true si existe, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */

    @Override
    public boolean existsNumberChapterByBook(int numberChapter, String isbn) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM chapterBook WHERE chapterNumber = ? AND isbn = ?";
        boolean exists = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, numberChapter);
            preparedStatement.setString(2, isbn);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                exists = true;
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return exists;
    }    
    
}
