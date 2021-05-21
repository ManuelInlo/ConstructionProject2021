
package testbusinesslogic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.fei.ca.businesslogic.ArticleDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Article;
import mx.fei.ca.domain.Evidence;
import static org.junit.Assert.assertEquals;
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
        Evidence evidence = new Evidence("SI", "Importancia de la Ingeniería de Software", "Alicia Ruiz");
        Article article = new Article(evidence, "7487-0984", "C:\\usuarios\\alicia\\arc1.fil", 95, 110,
                    "Publicado", "UVserva", "México", publicationDate, 300, "CUO", "El desarrollo de la educación y la ingeniería de software", 1, "MCUD940585RDTRER10");
        boolean saveResult = articleDAO.saveArticle(article);
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
        Evidence evidence = new Evidence("NO", "Importancia de la Ingeniería de Software", "Alicia Ruiz");
        Article article = new Article(evidence, "7000-0982", "C:\\usuarios\\alicia\\arc1.fil", 80, 110,
                    "Publicado", "UVserva", "México", publicationDate, 8, "CUO", "El desarrollo de la educación y la ingeniería de software", 1, "MCUD940585RDTRER10");
        boolean updateResult = articleDAO.updateArticle(article,"7485-0952");
        assertEquals("Prueba correcta, si actualizo", updateResult, true);
    }    
}
