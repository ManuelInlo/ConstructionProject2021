
package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.dataaccess.DataBaseConnection;
import mx.fei.ca.domain.Article;
import mx.fei.ca.domain.Evidence;
import mx.fei.ca.domain.InvestigationProject;

/**
 * Clase para representar el Objeto de acceso a datos de una evidencia de tipo artículo 
 * @author Gloria Mendoza González
 * @version 16-06-2021
 */

public class ArticleDAO implements IArticleDAO{
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;    
 
    /**
     * Constructor para la creación de un ArticleDAO permitiendo también la creación de la conexión a la base de datos
     */
    
    public ArticleDAO(){
        dataBaseConnection = new DataBaseConnection();       
    }  
    
    /**
     * Método que guarda una nueva evidencia de tipo artículo en la base de datos
     * @param article Define el artículo a guardar
     * @return Booleano con el resultado de guardado, devuelve true si guardó, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    @Override
    public boolean savedArticle (Article article) throws BusinessConnectionException{
       String sql = "INSERT INTO article (impactCA, titleEvidence, ISSN, fileRoute, homepage, endPage, actualState, magazineName, country, publicationDate, volume, editorial, author, description, idProject, curp)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        boolean saveResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, article.getImpactCA());
            preparedStatement.setString(2, article.getTitleEvidence()); 
            preparedStatement.setString(3, article.getIssn());
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
            preparedStatement.setInt(15, article.getInvestigationProject().getIdProject()); 
            preparedStatement.setString(16, article.getCurp());             
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
     * Método que modifica una evidencia de tipo artículo existente en la base de datos
     * @param article Define el artículo modificado
     * @param ISSN Define el identificador del artículo a modificar
     * @return Booleano con el resultado de modificación, devuelve true si guardó, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    @Override
    public boolean updatedArticle (Article article, String ISSN) throws BusinessConnectionException{
       String sql = "UPDATE article SET impactCA = ?, titleEvidence = ?, ISSN = ?, fileRoute = ?, homepage = ?, endPage = ?, actualState = ?, magazineName = ?, country = ?,"
               + " publicationDate = ?, volume = ?, editorial = ?, author = ?, description = ?, idProject = ?, curp = ?"
               + " WHERE ISSN = ? ";
        boolean updateResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, article.getImpactCA());
            preparedStatement.setString(2, article.getTitleEvidence()); 
            preparedStatement.setString(3, article.getIssn());
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
            preparedStatement.setInt(15, article.getInvestigationProject().getIdProject()); 
            preparedStatement.setString(16, article.getCurp()); 
            preparedStatement.setString(17, ISSN);
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
     * Método que recupera de la base de datos las evidencias de tipo artículo que impactan al CA
     * @return ArrayList con evidencias de tipo artículo que impactan al CA
     * @throws BusinessConnectionException 
     */
    
    @Override
    public ArrayList<Article> findArticlesByPositiveImpactCA() throws BusinessConnectionException {
        String sql = "SELECT * FROM article WHERE impactCA = 'SI'";
        ArrayList<Article> articles = new ArrayList<>();
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Evidence evidence;
                Article article;
                String impactCA = resultSet.getString("impactCA");
                String titleEvidence = resultSet.getString("titleEvidence");
                String issn = resultSet.getString("ISSN");
                String fileRoute = resultSet.getString("fileRoute");
                int homePage = resultSet.getInt("homePage");
                int endPage = resultSet.getInt("endPage");    
                String actualState = resultSet.getString("actualState");
                String magazineName = resultSet.getString("magazineName");
                String country = resultSet.getString("country");                
                Date publicationDate = resultSet.getDate("publicationDate");
                int volume = resultSet.getInt("volume"); 
                String editorial = resultSet.getString("editorial");
                String author = resultSet.getString("author");               
                String description = resultSet.getString("description");
                evidence = new Evidence(impactCA, titleEvidence, author);
                if(publicationDate != null){
                    article = new Article(evidence, issn, fileRoute, homePage, endPage, actualState, magazineName, country, publicationDate, volume, editorial, description);
                }else{
                     article = new Article(evidence, issn, fileRoute, homePage, endPage, actualState, magazineName, country, volume, editorial, description);
                }               
                articles.add(article);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return articles;
    }
    
    /**
     * Método que recupera de la base de datos las últimas dos evidencias de tipo artículo de un integrante
     * @param curp Define la curp del integrante del cual se quiere recuperar los artículos
     * @return ArrayList con máximo dos artículos
     * @throws BusinessConnectionException 
     */

    @Override
    public ArrayList<Article> findLastTwoArticlesByCurpIntegrant(String curp) throws BusinessConnectionException {
        String sql = "SELECT * FROM article WHERE curp = ? ORDER by ISSN DESC LIMIT 2";
        ArrayList<Article> articles = new ArrayList<>();
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, curp);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Evidence evidence;
                Article article;
                String impactCA = resultSet.getString("impactCA");
                String titleEvidence = resultSet.getString("titleEvidence");
                String issn = resultSet.getString("ISSN");
                String fileRoute = resultSet.getString("fileRoute");
                int homePage = resultSet.getInt("homePage");
                int endPage = resultSet.getInt("endPage");    
                String actualState = resultSet.getString("actualState");
                String magazineName = resultSet.getString("magazineName");
                String country = resultSet.getString("country");                
                Date publicationDate = resultSet.getDate("publicationDate");
                int volume = resultSet.getInt("volume"); 
                String editorial = resultSet.getString("editorial");
                String author = resultSet.getString("author");               
                String description = resultSet.getString("description");
                int idProject = resultSet.getInt("idProject");               
                evidence = new Evidence(impactCA, titleEvidence, author);
                if(publicationDate != null){
                    article = new Article(evidence, issn, fileRoute, homePage, endPage, actualState, magazineName, country, publicationDate, volume, editorial, description);
                }else{
                     article = new Article(evidence, issn, fileRoute, homePage, endPage, actualState, magazineName, country, volume, editorial, description);
                }  
                InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
                InvestigationProject investigationProject = investigationProjectDAO.findInvestigationProjectById(idProject); 
                article.setInvestigationProject(investigationProject);
                articles.add(article);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return articles;
    }
    
    /**
     * Método que recupera de la base de datos el número de evidencias de tipo artículo de un integrante
     * @param curp Define la curp del integrante del cual se quiere recuperar los artículos
     * @return int con el número total de evidencias de tipo artículo de un integrante
     * @throws BusinessConnectionException 
     */

    @Override
    public int findArticlesByCurpIntegrant(String curp) throws BusinessConnectionException {
        String sql = "SELECT * FROM article WHERE curp = ?";
        ArrayList<Article> articles = new ArrayList<>();
        int numberArticles = 0;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, curp);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Evidence evidence;
                Article article;
                String impactCA = resultSet.getString("impactCA");
                String titleEvidence = resultSet.getString("titleEvidence");
                String issn = resultSet.getString("ISSN");
                String fileRoute = resultSet.getString("fileRoute");
                int homePage = resultSet.getInt("homePage");
                int endPage = resultSet.getInt("endPage");    
                String actualState = resultSet.getString("actualState");
                String magazineName = resultSet.getString("magazineName");
                String country = resultSet.getString("country");                
                Date publicationDate = resultSet.getDate("publicationDate");
                int volume = resultSet.getInt("volume"); 
                String editorial = resultSet.getString("editorial");
                String author = resultSet.getString("author");               
                String description = resultSet.getString("description");
                int idProject = resultSet.getInt("idProject");               
                evidence = new Evidence(impactCA, titleEvidence, author);
                if(publicationDate != null){
                    article = new Article(evidence, issn, fileRoute, homePage, endPage, actualState, magazineName, country, publicationDate, volume, editorial, description);
                }else{
                     article = new Article(evidence, issn, fileRoute, homePage, endPage, actualState, magazineName, country, volume, editorial, description);
                }  
                InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
                InvestigationProject investigationProject = investigationProjectDAO.findInvestigationProjectById(idProject); 
                article.setInvestigationProject(investigationProject);
                articles.add(article);
            }
            numberArticles = articles.size();
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return numberArticles;
    }   
    
    /**
     * Método que recupera de la base de datos las evidencias de tipo artículo por fecha que impactan al CA
     * @param date Define la fecha de la cual se quiere recuperar los artículos
     * @return ArrayList con máximo dos artículos
     * @throws BusinessConnectionException 
     */
    
    @Override
    public ArrayList<Article> findArticlesByDateAndImpactCA(Date date) throws BusinessConnectionException {
        String sql = "SELECT * FROM article WHERE publicationDate = ? AND impactCA = 'SI' ORDER by ISSN DESC LIMIT 2";
        ArrayList<Article> articles = new ArrayList<>();
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, date);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Evidence evidence;
                Article article;
                String impactCA = resultSet.getString("impactCA");
                String titleEvidence = resultSet.getString("titleEvidence");
                String issn = resultSet.getString("ISSN");
                String fileRoute = resultSet.getString("fileRoute");
                int homePage = resultSet.getInt("homePage");
                int endPage = resultSet.getInt("endPage");    
                String actualState = resultSet.getString("actualState");
                String magazineName = resultSet.getString("magazineName");
                String country = resultSet.getString("country");                
                Date publicationDate = resultSet.getDate("publicationDate");
                int volume = resultSet.getInt("volume"); 
                String editorial = resultSet.getString("editorial");
                String author = resultSet.getString("author");               
                String description = resultSet.getString("description");
                int idProject = resultSet.getInt("idProject");               
                evidence = new Evidence(impactCA, titleEvidence, author);
                if(publicationDate != null){
                    article = new Article(evidence, issn, fileRoute, homePage, endPage, actualState, magazineName, country, publicationDate, volume, editorial, description);
                }else{
                     article = new Article(evidence, issn, fileRoute, homePage, endPage, actualState, magazineName, country, volume, editorial, description);
                }  
                InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
                InvestigationProject investigationProject = investigationProjectDAO.findInvestigationProjectById(idProject); 
                article.setInvestigationProject(investigationProject);
                articles.add(article);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return articles;
    }
    
    /**
     * Método que recupera los artículos de acuerdo a las iniciales del título
     * @param InitialesTitleArticle Define las iniciales del título del artículo a recuperar
     * @param curp Define la curp del integrante del cual se quiere recuperar los artículos
     * @return ArrayList con artículos que coincidieron con el título
     * @throws BusinessConnectionException 
     */
    
    @Override
    public ArrayList<Article> findArticleByInitialesOfTitle(String InitialesTitleArticle, String curp) throws BusinessConnectionException {
        String sql = "SELECT * FROM article WHERE titleEvidence LIKE CONCAT('%',?,'%') AND curp = ?";
        ArrayList<Article> articles = new ArrayList<>();
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, InitialesTitleArticle);
            preparedStatement.setString(2, curp);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Evidence evidence;
                Article article;
                String impactCA = resultSet.getString("impactCA");
                String titleEvidence = resultSet.getString("titleEvidence");
                String issn = resultSet.getString("ISSN");
                String fileRoute = resultSet.getString("fileRoute");
                int homePage = resultSet.getInt("homePage");
                int endPage = resultSet.getInt("endPage");    
                String actualState = resultSet.getString("actualState");
                String magazineName = resultSet.getString("magazineName");
                String country = resultSet.getString("country");                
                Date publicationDate = resultSet.getDate("publicationDate");
                int volume = resultSet.getInt("volume"); 
                String editorial = resultSet.getString("editorial");
                String author = resultSet.getString("author");               
                String description = resultSet.getString("description");
                int idProject = resultSet.getInt("idProject");               
                evidence = new Evidence(impactCA, titleEvidence, author);
                if(publicationDate != null){
                    article = new Article(evidence, issn, fileRoute, homePage, endPage, actualState, magazineName, country, publicationDate, volume, editorial, description);
                }else{
                     article = new Article(evidence, issn, fileRoute, homePage, endPage, actualState, magazineName, country, volume, editorial, description);
                }  
                InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
                InvestigationProject investigationProject = investigationProjectDAO.findInvestigationProjectById(idProject); 
                article.setInvestigationProject(investigationProject);
                articles.add(article);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return articles;
    }
    
    /**
     * Método que verifica si existe el título de un artículo en la base de datos
     * @param titleArticle Define el título a verificar existencia
     * @return Booleano con el resultado de verificación, devuelve true si existe, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    @Override
    public boolean existsArticleTitle(String titleArticle) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM article WHERE titleEvidence = ?";
        boolean exists = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, titleArticle);
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
     * Método que verifica si existe la ruta de archivo de un artículo en la base de datos
     * @param fileRoute Define la ruta del archivo a verificar existencia
     * @return Booleano con el resultado de verificación, devuelve true si existe, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    @Override
    public boolean existsArticleFileRoute(String fileRoute) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM article WHERE fileRoute = ?";
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
     * Método que verifica si existe el ISSN de un artículo en la base de datos
     * @param issn Define el ISSN a verificar existencia
     * @return Booleano con el resultado de verificación, devuelve true si existe, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    @Override
    public boolean existsArticleIssn(String issn) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM article WHERE issn = ?";
        boolean exists = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, issn);
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
     * Método que verifica si existe el título de un artículo en la base de datos para modificación
     * @param titleArticle Define el título del artículo a verificar existencia
     * @param issn Define el identificador único del artículo a modificar
     * @return Booleano con el resultado de verificación, devuelve true si existe, de lo contrario devuelve false
     * @throws BusinessConnectionException 
     */
    
    @Override
    public boolean existsArticleTitleForUpdate(String titleArticle, String issn) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM article WHERE titleEvidence = ? AND issn = ?";
        boolean exists = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, titleArticle);
            preparedStatement.setString(2, issn);
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
     * Método que verifica si existe la ruta de archivo de un artículo en la base de datos
     * @param fileRoute Define la ruta del archivo a verificar existencia
     * @param issn Define el identificador único del artículo a modificar
     * @return Booleano con el resultado de verificación, devuelve true si existe, de lo contrario devuelve false
     * @throws BusinessConnectionException 
     */
    
    @Override
    public boolean existsArticleFileRouteForUpdate(String fileRoute, String issn) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM article WHERE fileRoute = ? AND issn = ?";
        boolean exists = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, fileRoute);
            preparedStatement.setString(2, issn);
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
