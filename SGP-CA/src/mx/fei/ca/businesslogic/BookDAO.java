
package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.dataaccess.DataBaseConnection;
import mx.fei.ca.domain.Book;

public class BookDAO implements IBookDAO{
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;    
 
    public BookDAO(){
        dataBaseConnection = new DataBaseConnection();       
    }  
    
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
}
