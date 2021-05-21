
package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.dataaccess.DataBaseConnection;
import mx.fei.ca.domain.ChapterBook;

public class ChapterBookDAO implements IChapterBookDAO{
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;    
 
    public ChapterBookDAO(){
        dataBaseConnection = new DataBaseConnection();       
    } 
    
    @Override
    public boolean saveChapterBook (ChapterBook chapterBook) throws BusinessConnectionException{
       String sql = "INSERT INTO chapterbook (impactCA, titleEvidence, author, homepage, endPage, ISBN, idProject, curp, chapterNumber)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        boolean saveResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, chapterBook.getImpactCA());
            preparedStatement.setString(2, chapterBook.getTitleEvidence()); 
            preparedStatement.setString(3, chapterBook.getAuthor());           
            preparedStatement.setInt(4, chapterBook.getHomepage());
            preparedStatement.setInt(5, chapterBook.getEndPage()); 
            preparedStatement.setString(6, chapterBook.getISBN()); 
            preparedStatement.setInt(7, chapterBook.getIdProject()); 
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
    
    @Override
    public boolean updateChapterBook (ChapterBook chapterBook, int chapterNumber) throws BusinessConnectionException{
       String sql = "UPDATE chapterBook SET impactCA = ?, titleEvidence = ?, author = ?, homepage =?, endPage = ?, ISBN = ?, idProject = ?, curp = ? "
               + " WHERE chapterNumber = ? ";
        boolean updateResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, chapterBook.getImpactCA());
            preparedStatement.setString(2, chapterBook.getTitleEvidence()); 
            preparedStatement.setString(3, chapterBook.getAuthor());
            preparedStatement.setInt(4, chapterBook.getHomepage());
            preparedStatement.setInt(5, chapterBook.getEndPage()); 
            preparedStatement.setString(6, chapterBook.getISBN());
            preparedStatement.setInt(7, chapterBook.getIdProject()); 
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
}
