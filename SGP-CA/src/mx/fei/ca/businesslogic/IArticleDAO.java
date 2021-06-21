
package mx.fei.ca.businesslogic;

import java.sql.Date;
import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Article;

/**
 * Interface del objeto de acceso a datos de artículo
 * @author Gloria Mendoza González
 * @version 16-06-2021
 */

public interface IArticleDAO {
    public boolean savedArticle (Article article) throws BusinessConnectionException;    
    public boolean updatedArticle (Article article, String issn) throws BusinessConnectionException;   
    public ArrayList<Article> findArticlesByPositiveImpactCA() throws BusinessConnectionException; 
    public ArrayList<Article> findLastTwoArticlesByCurpIntegrant(String curp) throws BusinessConnectionException;
    public ArrayList<Article> findArticleByInitialesOfTitle(String InitialesTitleArticle, String curp) throws BusinessConnectionException; 
    public boolean existsArticleTitle(String titleArticle) throws BusinessConnectionException;
    public boolean existsArticleFileRoute(String fileRoute) throws BusinessConnectionException;
    public boolean existsArticleTitleForUpdate(String titleArticle, String issn) throws BusinessConnectionException;
    public boolean existsArticleFileRouteForUpdate(String fileRoute, String issn) throws BusinessConnectionException;
    public ArrayList<Article> findArticlesByDateAndImpactCA(Date date) throws BusinessConnectionException;
    public boolean existsArticleIssn(String issn) throws BusinessConnectionException;   
    public int findArticlesByCurpIntegrant(String curp) throws BusinessConnectionException;
}