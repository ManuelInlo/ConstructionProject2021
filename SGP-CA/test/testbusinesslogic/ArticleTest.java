
package testbusinesslogic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.fei.ca.businesslogic.ArticleDAO;
import mx.fei.ca.businesslogic.IntegrantDAO;
import mx.fei.ca.businesslogic.ReceptionWorkDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Article;
import mx.fei.ca.domain.Evidence;
import mx.fei.ca.domain.Integrant;
import mx.fei.ca.domain.InvestigationProject;
import mx.fei.ca.domain.ReceptionWork;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ArticleTest {
    public ArticleTest(){
        
    }
    
    @Test
    public void testInsertArticle() throws BusinessConnectionException{
        ArticleDAO articleDAO = new ArticleDAO();
        String date = "20-05-2000";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        java.sql.Date publicationDate = null;
        try {
            publicationDate = new java.sql.Date(simpleDateFormat.parse(date).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(ArticleTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        InvestigationProject investigationProject = new InvestigationProject();
        investigationProject.setIdProject(1);
        
        IntegrantDAO integrantDAO = new IntegrantDAO();
        Integrant integrant = integrantDAO.findIntegrantByCurp("JCPA940514RDTREOP1");
        
        Evidence evidence = new Evidence("SI", "Importancia de la Ingeniería de Software", "Alicia Ruiz");
        Article article = new Article(evidence, "7488-0985", "C:\\usuarios\\alicia\\arc1.fil", 95, 110,
                    "Publicado", "UVserva", "México", publicationDate, 300, "CUO", "El desarrollo de la educación "
                    + "y la ingeniería de software");
        
        article.setCurp(integrant.getCurp());
        article.setInvestigationProject(investigationProject);
        
        boolean saveResult = articleDAO.savedArticle(article);
        assertEquals("Prueba correcta, si guardó", saveResult, true);
    }
    
    @Test
    public void testUpdateArticle() throws BusinessConnectionException{
        ArticleDAO articleDAO = new ArticleDAO();
        String date = "20-12-2019";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        java.sql.Date publicationDate = null;
        try {
            publicationDate = new java.sql.Date(simpleDateFormat.parse(date).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(ArticleTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        InvestigationProject investigationProject = new InvestigationProject();
        investigationProject.setIdProject(1);
        
        IntegrantDAO integrantDAO = new IntegrantDAO();
        Integrant integrant = integrantDAO.findIntegrantByCurp("JCPA940514RDTREOP1");        
        
        Evidence evidence = new Evidence("NO", "Importancia de la Ingeniería de Software", "Alicia Ruiz");
        Article article = new Article(evidence, "7000-0982", "C:\\usuarios\\alicia\\arc1.fil", 80, 110,
                    "Publicado", "UVserva", "México", publicationDate, 8, "CUO", "El desarrollo de la educación y "
                    + "la ingeniería de software");
        
        article.setCurp(integrant.getCurp());
        article.setInvestigationProject(investigationProject);       
        
        boolean updateResult = articleDAO.updatedArticle(article,"7485-0952");
        assertEquals("Prueba correcta, si actualizo", updateResult, true);
    }    
    
    @Test
    public void testFindArticlesByPositiveImpactCA() throws BusinessConnectionException{
        ArticleDAO articleDAO = new ArticleDAO();
        ArrayList<Article> articles = articleDAO.findArticlesByPositiveImpactCA();
        assertEquals("Prueba busqueda de artículo por su impacto al CA", articles.size(), 5);
    }
    
    @Test
    public void testFindArticlesByCurpIntegrant() throws BusinessConnectionException{
        ArticleDAO articleDAO = new ArticleDAO();
        ArrayList<Article> articles = articleDAO.findLastTwoArticlesByCurpIntegrant("MCUD940585RDTRER10");
        assertEquals("Prueba busqueda de artículo de un integrante por curp", articles.size(), 2);
    }
    
    @Test
    public void testFindArticleByInitialesOfTitle() throws BusinessConnectionException{
        ArticleDAO articleDAO = new ArticleDAO();
        ArrayList <Article> articles = articleDAO.findArticleByInitialesOfTitle("software", "MCUD940585RDTRER10");
        assertEquals("Prueba encontrar artículo por título", articles.size(), 5);
    }
    
    @Test
    public void testExistsArticleTitle() throws BusinessConnectionException{
        ArticleDAO articleDAO = new ArticleDAO();
        boolean exists = articleDAO.existsArticleTitle("Importancia de la Ingeniería de Software");
        assertTrue("Prueba mandar a validar un titulo que ya existe de un artículo", exists);
    }
    
    @Test
    public void testExistsArticleFileRoute() throws BusinessConnectionException{
        ArticleDAO articleDAO = new ArticleDAO();
        boolean exists = articleDAO.existsArticleFileRoute("C:\\usuarios\\alicia\\arc1.fil");
        assertTrue("Prueba mandar a validar una ruta de archivo que ya existe en un articulo", exists);
    }  
    
    @Test
    public void testExistsArticleTitleForUpdate() throws BusinessConnectionException{
        ArticleDAO articleDAO = new ArticleDAO();
        boolean exists = articleDAO.existsArticleTitleForUpdate("Datos de la ingeniería de software", "7487-0983");
        assertFalse("Prueba mandar a validar un titulo modificado que no existe de artículo", exists);
    }
     
    @Test
    public void testExistsArticleFileRouteForUpdate() throws BusinessConnectionException{
        ArticleDAO articleDAO = new ArticleDAO();
        boolean exists = articleDAO.existsArticleFileRouteForUpdate("C:\\Users\\juan\\Documents\\IA.pdf", "7487-0983");
        assertFalse("Prueba mandar a validar una ruta de archivo modificada que no existe en un artículo", exists);
    }    
    
}
