
package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.dataaccess.DataBaseConnection;
import mx.fei.ca.domain.Book;
import mx.fei.ca.domain.Evidence;

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
            preparedStatement.setString(3, book.getISBN());
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
            preparedStatement.setInt(14, book.getIdProject()); 
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
     * @param ISBN Define el identificador del libro a modificar
     * @return Booleano con el resultado de modificación, devuelve true si guardó, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    @Override
    public boolean updatedBook (Book book, String ISBN) throws BusinessConnectionException{
       String sql = "UPDATE book SET impactCA = ?, titleEvidence = ?, ISBN = ?, printing = ?, numPages = ?, participationType = ?, "
               + " actualState = ?, country = ?, author = ?, publicationDate = ?, editorial = ?, edition = ?, fileRoute = ?, idProject = ?, curp = ? "
               + " WHERE ISBN = ? ";
        boolean updateResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, book.getImpactCA());
            preparedStatement.setString(2, book.getTitleEvidence()); 
            preparedStatement.setString(3, book.getISBN());
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
            preparedStatement.setInt(14, book.getIdProject()); 
            preparedStatement.setString(15, book.getCurp());  
            preparedStatement.setString(16, ISBN);           
            preparedStatement.executeUpdate();
            updateResult = true;
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return updateResult;
    }
    
    @Override
    public ArrayList<Book> findBooksByPositiveImpactCA() throws BusinessConnectionException {
        String sql = "SELECT * FROM book WHERE impactCA = 'SI'";
        ArrayList<Book> books = new ArrayList<>();
        /*try{
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
                String participationType = resultSet.getString("paticipationType");
                String actualState = resultSet.getString("actualState");
                String country = resultSet.getString("country");      
                String author = resultSet.getString("author");               
                Date publicationDate = resultSet.getDate("publicationDate");
                String editorial = resultSet.getString("editorial");
                String edition = resultSet.getString("edition");      
                String fileRoute = resultSet.getString("fileRoute");                
                evidence = new Evidence(impactCA, titleEvidence, author);
                book = new Book(evidence, issn, fileRoute, homePage,  endPage, actualState, magazineName,
                                      country, publicationDate, volume, editorial, description);
                books.add(book);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }*/
        return books;
    }
}
