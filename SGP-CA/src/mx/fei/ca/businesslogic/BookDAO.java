
package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
;import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.dataaccess.DataBaseConnection;
import mx.fei.ca.domain.Book;
import mx.fei.ca.domain.Evidence;
import mx.fei.ca.domain.InvestigationProject;

/**
 * Clase para representar el Objeto de acceso a datos de una evidencia de tipo libro
 * @author Gloria Mendoza González
 * @version 16-06-2021
 */

public class BookDAO implements IBookDAO{
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;   
    
    /**
     * Constructor para la creación de un BookDAO permitiendo también la creación de la conexión a la base de datos
     */
 
    public BookDAO(){
        dataBaseConnection = new DataBaseConnection();       
    }  
    
    /**
     * Método que guarda una nueva evidencia de tipo libro en la base de datos
     * @param book Define el libro a guardar
     * @return Booleano con el resultado de guardado, devuelve true si guardó, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    @Override
    public boolean savedBook (Book book) throws BusinessConnectionException{
       String sql = "INSERT INTO book (impactCA, titleEvidence, ISBN, printing, numPages, participationType, actualState, country, author, publicationDate, editorial, edition, fileRoute, idProject, curp)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        boolean saveResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, book.getImpactCA());
            preparedStatement.setString(2, book.getTitleEvidence()); 
            preparedStatement.setString(3, book.getIsbn());
            preparedStatement.setInt(4, book.getPrinting());
            preparedStatement.setInt(5, book.getNumPages()); 
            preparedStatement.setString(6, book.getParticipationType());
            preparedStatement.setString(7, book.getActualState()); 
            preparedStatement.setString(8, book.getCountry());
            preparedStatement.setString(9, book.getAuthor()); 
            preparedStatement.setDate(10, book.getPublicationDate());
            preparedStatement.setString(11, book.getEditorial()); 
            preparedStatement.setString(12, book.getEdition()); 
            preparedStatement.setString(13, book.getFileRoute());  
            preparedStatement.setInt(14, book.getInvestigationProject().getIdProject()); 
            preparedStatement.setString(15, book.getCurp());             
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
     * Método que modifica una evidencia de tipo libro existente en la base de datos
     * @param book Define el libro modificado
     * @param isbn Define el identificador del libro a modificar
     * @return Booleano con el resultado de modificación, devuelve true si guardó, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    @Override
    public boolean updatedBook (Book book, String isbn) throws BusinessConnectionException{
       String sql = "UPDATE book SET impactCA = ?, titleEvidence = ?, ISBN = ?, printing = ?, numPages = ?, participationType = ?, "
               + " actualState = ?, country = ?, author = ?, publicationDate = ?, editorial = ?, edition = ?, fileRoute = ?, idProject = ?, curp = ? "
               + " WHERE ISBN = ? ";
        boolean updateResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, book.getImpactCA());
            preparedStatement.setString(2, book.getTitleEvidence()); 
            preparedStatement.setString(3, book.getIsbn());
            preparedStatement.setInt(4, book.getPrinting());
            preparedStatement.setInt(5, book.getNumPages()); 
            preparedStatement.setString(6, book.getParticipationType());
            preparedStatement.setString(7, book.getActualState()); 
            preparedStatement.setString(8, book.getCountry());
            preparedStatement.setString(9, book.getAuthor()); 
            preparedStatement.setDate(10, book.getPublicationDate());
            preparedStatement.setString(11, book.getEditorial()); 
            preparedStatement.setString(12, book.getEdition()); 
            preparedStatement.setString(13, book.getFileRoute());  
            preparedStatement.setInt(14, book.getInvestigationProject().getIdProject()); 
            preparedStatement.setString(15, book.getCurp());  
            preparedStatement.setString(16, isbn);           
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
     * Método que recupera de la base de datos las evidencias de tipo libro que impactan al CA
     * @return ArrayList con evidencias de tipo libro que impactan al CA
     * @throws BusinessConnectionException 
     */
    
    @Override
    public ArrayList<Book> findBooksByPositiveImpactCA() throws BusinessConnectionException {
        String sql = "SELECT * FROM book WHERE impactCA = 'SI'";
        ArrayList<Book> books = new ArrayList<>();
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Evidence evidence;
                Book book;
                String impactCA = resultSet.getString("impactCA");
                String titleEvidence = resultSet.getString("titleEvidence");
                String isbn = resultSet.getString("ISBN");
                int printing = resultSet.getInt("printing");
                int numPages = resultSet.getInt("numPages");
                String participationType = resultSet.getString("participationType");
                String actualState = resultSet.getString("actualState");
                String country = resultSet.getString("country");      
                String author = resultSet.getString("author");               
                Date publicationDate = resultSet.getDate("publicationDate");
                String editorial = resultSet.getString("editorial");
                String edition = resultSet.getString("edition");      
                String fileRoute = resultSet.getString("fileRoute");                
                evidence = new Evidence(impactCA, titleEvidence, author);
                if(publicationDate != null){
                    book = new Book(evidence, isbn, printing, numPages, participationType, actualState, country, publicationDate, editorial, edition, fileRoute);
                }else{
                    book = new Book(evidence, isbn, printing, numPages, participationType, actualState, country, editorial, edition, fileRoute);
                }
                books.add(book);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return books;
    }
    
    /**
     * Método que recupera de la base de datos todas las evidencias de tipo libro 
     * @return ArrayList con todas las evidencias de tipo libro 
     * @throws BusinessConnectionException 
     */
    
    @Override
    public ArrayList<Book> findAllBooks() throws BusinessConnectionException {
        String sql = "SELECT * FROM book";
        ArrayList<Book> books = new ArrayList<>();
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Evidence evidence;
                Book book;
                String impactCA = resultSet.getString("impactCA");
                String titleEvidence = resultSet.getString("titleEvidence");
                String isbn = resultSet.getString("ISBN");
                int printing = resultSet.getInt("printing");
                int numPages = resultSet.getInt("numPages");
                String participationType = resultSet.getString("participationType");
                String actualState = resultSet.getString("actualState");
                String country = resultSet.getString("country");      
                String author = resultSet.getString("author");               
                Date publicationDate = resultSet.getDate("publicationDate");
                String editorial = resultSet.getString("editorial");
                String edition = resultSet.getString("edition");      
                String fileRoute = resultSet.getString("fileRoute");                
                evidence = new Evidence(impactCA, titleEvidence, author);
                if(publicationDate != null){
                    book = new Book(evidence, isbn, printing, numPages, participationType, actualState, country, publicationDate, editorial, edition, fileRoute);
                }else{
                    book = new Book(evidence, isbn, printing, numPages, participationType, actualState, country, editorial, edition, fileRoute);
                }
                books.add(book);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return books;
    }   
    
    /**
     * Método que recupera de la base de datos las evidencias de tipo libro por fecha que impactan al CA
     * @param date Define la fecha de la cual se quiere recuperar los libros
     * @return ArrayList con máximo dos libros
     * @throws BusinessConnectionException 
     */
    
    @Override
    public ArrayList<Book> findBooksByDateAndImpactCA(Date date) throws BusinessConnectionException {
        String sql = "SELECT * FROM book WHERE publicationDate = ? AND impactCA = 'SI' ORDER by ISBN DESC LIMIT 2";
        ArrayList<Book> books = new ArrayList<>();
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, date);            
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Evidence evidence;
                Book book;
                String impactCA = resultSet.getString("impactCA");
                String titleEvidence = resultSet.getString("titleEvidence");
                String isbn = resultSet.getString("ISBN");
                int printing = resultSet.getInt("printing");
                int numPages = resultSet.getInt("numPages");
                String participationType = resultSet.getString("participationType");
                String actualState = resultSet.getString("actualState");
                String country = resultSet.getString("country");      
                String author = resultSet.getString("author");               
                Date publicationDate = resultSet.getDate("publicationDate");
                String editorial = resultSet.getString("editorial");
                String edition = resultSet.getString("edition");      
                String fileRoute = resultSet.getString("fileRoute");                
                evidence = new Evidence(impactCA, titleEvidence, author);
                if(publicationDate != null){
                    book = new Book(evidence, isbn, printing, numPages, participationType, actualState, country, publicationDate, editorial, edition, fileRoute);
                }else{
                    book = new Book(evidence, isbn, printing, numPages, participationType, actualState, country, editorial, edition, fileRoute);
                }
                books.add(book);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return books;
    }   
    
    /**
     * Método que recupera de la base de datos las últimas dos evidencias de tipo libro de un integrante
     * @param curp Define la curp del integrante del cual se quiere recuperar los libros
     * @return ArrayList con máximo dos libros
     * @throws BusinessConnectionException 
     */
    
    @Override
    public ArrayList<Book> findLastTwoBooksByCurpIntegrant(String curp) throws BusinessConnectionException {
        String sql = "SELECT * FROM book WHERE curp = ? ORDER by ISBN DESC LIMIT 2";
        ArrayList<Book> books = new ArrayList<>();
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, curp);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Evidence evidence;
                Book book;
                String impactCA = resultSet.getString("impactCA");
                String titleEvidence = resultSet.getString("titleEvidence");
                String isbn = resultSet.getString("ISBN");
                int printing = resultSet.getInt("printing");
                int numPages = resultSet.getInt("numPages");
                String participationType = resultSet.getString("participationType");
                String actualState = resultSet.getString("actualState");
                String country = resultSet.getString("country");      
                String author = resultSet.getString("author");               
                Date publicationDate = resultSet.getDate("publicationDate");
                String editorial = resultSet.getString("editorial");
                String edition = resultSet.getString("edition");      
                String fileRoute = resultSet.getString("fileRoute");
                int idProject = resultSet.getInt("idProject");                
                evidence = new Evidence(impactCA, titleEvidence, author);
                if(publicationDate != null){
                    book = new Book(evidence, isbn, printing, numPages, participationType, actualState, country, publicationDate, editorial, edition, fileRoute);
                }else{
                    book = new Book(evidence, isbn, printing, numPages, participationType, actualState, country, editorial, edition, fileRoute);
                }
                InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
                InvestigationProject investigationProject = investigationProjectDAO.findInvestigationProjectById(idProject); 
                book.setInvestigationProject(investigationProject);
                books.add(book);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return books;
    }
    
    /**
     * Método que recupera de la base de datos el número de evidencias de tipo libro de un integrante
     * @param curp Define la curp del integrante del cual se quiere recuperar los libros
     * @return int con el número total de evidencias de tipo libro de un integrante
     * @throws BusinessConnectionException 
     */
    
    @Override
    public int findBooksByCurpIntegrant(String curp) throws BusinessConnectionException {
        String sql = "SELECT * FROM book WHERE curp = ?";
        ArrayList<Book> books = new ArrayList<>();
        int numberBooks = 0;        
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, curp);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Evidence evidence;
                Book book;
                String impactCA = resultSet.getString("impactCA");
                String titleEvidence = resultSet.getString("titleEvidence");
                String isbn = resultSet.getString("ISBN");
                int printing = resultSet.getInt("printing");
                int numPages = resultSet.getInt("numPages");
                String participationType = resultSet.getString("participationType");
                String actualState = resultSet.getString("actualState");
                String country = resultSet.getString("country");      
                String author = resultSet.getString("author");               
                Date publicationDate = resultSet.getDate("publicationDate");
                String editorial = resultSet.getString("editorial");
                String edition = resultSet.getString("edition");      
                String fileRoute = resultSet.getString("fileRoute");
                int idProject = resultSet.getInt("idProject");                
                evidence = new Evidence(impactCA, titleEvidence, author);
                if(publicationDate != null){
                    book = new Book(evidence, isbn, printing, numPages, participationType, actualState, country, publicationDate, editorial, edition, fileRoute);
                }else{
                    book = new Book(evidence, isbn, printing, numPages, participationType, actualState, country, editorial, edition, fileRoute);
                }
                InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
                InvestigationProject investigationProject = investigationProjectDAO.findInvestigationProjectById(idProject); 
                book.setInvestigationProject(investigationProject);
                books.add(book);             
            }
            numberBooks = books.size();               
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return numberBooks;
    }   
    
    /**
     * Método que recupera los libros de acuerdo a las iniciales del título
     * @param InitialesTitleBook Define las iniciales del título del libro a recuperar
     * @param curp Define la curp del integrante del cual se quiere recuperar los artículos
     * @return ArrayList con artículos que coincidieron con el título
     * @throws BusinessConnectionException 
     */
    
    @Override
    public ArrayList<Book> findBookByInitialesOfTitle(String InitialesTitleBook, String curp) throws BusinessConnectionException {
        String sql = "SELECT * FROM book WHERE titleEvidence LIKE CONCAT('%',?,'%') AND curp = ?";
        ArrayList<Book> books = new ArrayList<>();
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, InitialesTitleBook);
            preparedStatement.setString(2, curp);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Evidence evidence;
                Book book;
                String impactCA = resultSet.getString("impactCA");
                String titleEvidence = resultSet.getString("titleEvidence");
                String isbn = resultSet.getString("ISBN");
                int printing = resultSet.getInt("printing");
                int numPages = resultSet.getInt("numPages");
                String participationType = resultSet.getString("participationType");
                String actualState = resultSet.getString("actualState");
                String country = resultSet.getString("country");      
                String author = resultSet.getString("author");               
                Date publicationDate = resultSet.getDate("publicationDate");
                String editorial = resultSet.getString("editorial");
                String edition = resultSet.getString("edition");      
                String fileRoute = resultSet.getString("fileRoute");
                int idProject = resultSet.getInt("idProject");                
                evidence = new Evidence(impactCA, titleEvidence, author);
                if(publicationDate != null){
                    book = new Book(evidence, isbn, printing, numPages, participationType, actualState, country, publicationDate, editorial, edition, fileRoute);
                }else{
                    book = new Book(evidence, isbn, printing, numPages, participationType, actualState, country, editorial, edition, fileRoute);
                }
                InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
                InvestigationProject investigationProject = investigationProjectDAO.findInvestigationProjectById(idProject); 
                book.setInvestigationProject(investigationProject);
                books.add(book);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return books;
    }    
    
    /**
     * Método que verifica si existe el título de un libro en la base de datos
     * @param titleBook Define el título a verificar existencia
     * @return Booleano con el resultado de verificación, devuelve true si existe, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    @Override
    public boolean existsBookTitle(String titleBook) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM book WHERE titleEvidence = ?";
        boolean exists = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, titleBook);
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
     * Método que verifica si existe la ruta de archivo de un libro en la base de datos
     * @param fileRoute Define la ruta del archivo a verificar existencia
     * @return Booleano con el resultado de verificación, devuelve true si existe, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    @Override
    public boolean existsBookFileRoute(String fileRoute) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM book WHERE fileRoute = ?";
        boolean exists = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, fileRoute);
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
     * Método que verifica si existe el ISBN de un libro en la base de datos
     * @param isbn Define el ISBN a verificar existencia
     * @return Booleano con el resultado de verificación, devuelve true si existe, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    @Override
    public boolean existsBookIsbn(String isbn) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM book WHERE isbn = ?";
        boolean exists = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, isbn);
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
     * Método que verifica si existe el título de un libro en la base de datos para modificación
     * @param titleBook Define el título del libro a verificar existencia
     * @param isbn Define el identificador único del libro a modificar
     * @return Booleano con el resultado de verificación, devuelve true si existe, de lo contrario devuelve false
     * @throws BusinessConnectionException 
     */
    
    @Override
    public boolean existsBookTitleForUpdate(String titleBook, String isbn) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM book WHERE titleEvidence = ? AND isbn = ?";
        boolean exists = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, titleBook);
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
    
    /**
     * Método que verifica si existe la ruta de archivo de un libro en la base de datos
     * @param fileRoute Define la ruta del archivo a verificar existencia
     * @param isbn Define el identificador único del libro a modificar
     * @return Booleano con el resultado de verificación, devuelve true si existe, de lo contrario devuelve false
     * @throws BusinessConnectionException 
     */
    
    @Override
    public boolean existsBookFileRouteForUpdate(String fileRoute, String isbn) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM book WHERE fileRoute = ? AND isbn = ?";
        boolean exists = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, fileRoute);
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
    
    /**
     * Método que recupera el libro de acuerdo a el ISBN proporcionado
     * @param isbn Define el identificador único del libro a buscar
     * @return Una evidencia de tipo libro
     * @throws BusinessConnectionException 
     */
    
    @Override
    public Book findBookByIsbn(String isbn) throws BusinessConnectionException {
        String sql = "SELECT * FROM book WHERE ISBN = ?";
        Book book = null;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, isbn);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Evidence evidence;
                String impactCA = resultSet.getString("impactCA");
                String titleEvidence = resultSet.getString("titleEvidence");
                int printing = resultSet.getInt("printing");
                int numPages = resultSet.getInt("numPages");
                String participationType = resultSet.getString("participationType");
                String actualState = resultSet.getString("actualState");
                String country = resultSet.getString("country");      
                String author = resultSet.getString("author");               
                Date publicationDate = resultSet.getDate("publicationDate");
                String editorial = resultSet.getString("editorial");
                String edition = resultSet.getString("edition");      
                String fileRoute = resultSet.getString("fileRoute");
                int idProject = resultSet.getInt("idProject");                
                evidence = new Evidence(impactCA, titleEvidence, author);
                if(publicationDate != null){
                    book = new Book(evidence, isbn, printing, numPages, participationType, actualState, country, publicationDate, editorial, edition, fileRoute);
                }else{
                    book = new Book(evidence, isbn, printing, numPages, participationType, actualState, country, editorial, edition, fileRoute);
                }
                InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
                InvestigationProject investigationProject = investigationProjectDAO.findInvestigationProjectById(idProject); 
                book.setInvestigationProject(investigationProject);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return book;
    }
    
}
