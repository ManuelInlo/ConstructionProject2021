
package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.dataaccess.DataBaseConnection;
import mx.fei.ca.domain.Article;

public class ArticleDAO implements IArticleDAO{
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;    
 
    public ArticleDAO(){
        dataBaseConnection = new DataBaseConnection();       
    }  
    
    @Override
    public int saveArticle (Article article) throws BusinessConnectionException{
       String sql = "INSERT INTO article (impactCA, titleEvidence, ISSN, fileRoute, homepage, endPage, actualState, magazineName, country, publicationDate, volume, editorial, author, description, idProject, curp)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int saveResult = 0;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, article.getImpactCA());
            preparedStatement.setString(2, article.getTitleEvidence()); 
            preparedStatement.setString(3, article.getISSN());
            preparedStatement.setString(4, article.getFileRoute());
            preparedStatement.setInt(5, article.getHomepage()); 
            preparedStatement.setInt(6, article.getEndPage());
            preparedStatement.setString(7, article.getActualState()); 
            preparedStatement.setString(8, article.getMagazineName());
            preparedStatement.setString(9, article.getCountry()); 
            preparedStatement.setDate(10, article.getPublicationDate());
            preparedStatement.setInt(11, article.getVolume()); 
            preparedStatement.setString(12, article.getEditorial()); 
            preparedStatement.setString(13, article.getAuthor()); 
            preparedStatement.setString(14, article.getDescription());  
            preparedStatement.setInt(15, article.getIdProject()); 
            preparedStatement.setString(16, article.getCurp());             
            saveResult = preparedStatement.executeUpdate();
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return saveResult;
    }
    
    @Override
    public int updateArticle (Article article, String ISSN) throws BusinessConnectionException{
       String sql = "UPDATE article SET impactCA = ?, titleEvidence = ?, ISSN = ?, fileRoute = ?, homepage = ?, endPage = ?, actualState = ?, magazineName = ?, country = ?,"
               + " publicationDate = ?, volume = ?, editorial = ?, author = ?, description = ?, idProject = ?, curp = ?"
               + " WHERE ISSN = ? ";
        int updateResult = 0;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, article.getImpactCA());
            preparedStatement.setString(2, article.getTitleEvidence()); 
            preparedStatement.setString(3, article.getISSN());
            preparedStatement.setString(4, article.getFileRoute());
            preparedStatement.setInt(5, article.getHomepage()); 
            preparedStatement.setInt(6, article.getEndPage());
            preparedStatement.setString(7, article.getActualState()); 
            preparedStatement.setString(8, article.getMagazineName());
            preparedStatement.setString(9, article.getCountry()); 
            preparedStatement.setDate(10, article.getPublicationDate());
            preparedStatement.setInt(11, article.getVolume()); 
            preparedStatement.setString(12, article.getEditorial()); 
            preparedStatement.setString(13, article.getAuthor()); 
            preparedStatement.setString(14, article.getDescription());  
            preparedStatement.setInt(15, article.getIdProject()); 
            preparedStatement.setString(16, article.getCurp()); 
            preparedStatement.setString(17, ISSN);
            updateResult = preparedStatement.executeUpdate();
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return updateResult;
    }    
}
