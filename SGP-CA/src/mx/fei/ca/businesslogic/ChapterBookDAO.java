
package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.Date;
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
     * Método que guarda una nueva evidencia de tipo capítulo de libro en la base de datos
     * @param chapterBook Define el capítulo de libro a guardar
     * @return Booleano con el resultado de guardado, devuelve true si guardó, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    @Override
    public boolean savedChapterBook (ChapterBook chapterBook) throws BusinessConnectionException{
       String sql = "INSERT INTO chapterbook (impactCA, titleEvidence, author, homepage, endPage, ISBN, idProject, curp, chapterNumber)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        boolean saveResult = false;
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
            preparedStatement.setString(8, chapterBook.getCurp());  
            preparedStatement.setInt(9, chapterBook.getChapterNumber());            
            preparedStatement.executeUpdate();
            saveResult = true;
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return saveResult;
    }
    
    /**
     * Método que modifica una evidencia de tipo capítulo de libro existente en la base de datos
     * @param chapterBook Define el capítulo de libro modificado
     * @param chapterNumber Define el número del capítulo de libro a modificar
     * @return Booleano con el resultado de modificación, devuelve true si guardó, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    @Override
    public boolean updatedChapterBook (ChapterBook chapterBook, int chapterNumber) throws BusinessConnectionException{
       String sql = "UPDATE chapterBook SET impactCA = ?, titleEvidence = ?, author = ?, homepage =?, endPage = ?, ISBN = ?, idProject = ?, curp = ? "
               + " WHERE chapterNumber = ? ";
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
            preparedStatement.setString(8, chapterBook.getCurp());  
            preparedStatement.setInt(9, chapterNumber);          
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
                String impactCA = resultSet.getString("impactCA");
                String titleEvidence = resultSet.getString("titleEvidence");
                String author = resultSet.getString("author");                 
                int homePage = resultSet.getInt("homepage");
                int endPage = resultSet.getInt("endPage");
                int chapterNumber = resultSet.getInt("chapterNumber");                                  
                evidence = new Evidence(impactCA, titleEvidence, author);
                chapterBook = new ChapterBook(evidence, chapterNumber, homePage, endPage);
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
    
}
