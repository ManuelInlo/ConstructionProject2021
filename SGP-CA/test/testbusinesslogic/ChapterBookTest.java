
package testbusinesslogic;

import mx.fei.ca.businesslogic.ChapterBookDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.ChapterBook;
import mx.fei.ca.domain.Evidence;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ChapterBookTest {
   public ChapterBookTest(){
        
    }
    
    @Test
    public void testInsertChapterBook() throws BusinessConnectionException{
        ChapterBookDAO chapterBookDAO = new ChapterBookDAO();
        Evidence evidence = new Evidence("NO", "Procesos de la ingeniería de software", "Alicia Ruiz");
        ChapterBook chapterBook = new ChapterBook(evidence, 5, 23, 50, "978-3-16-148410-0", 1, "MCUD940585RDTRER10");
        boolean saveResult = chapterBookDAO.saveChapterBook(chapterBook);
        assertEquals("Prueba correcta, si guardó", saveResult, true);
    }
    
    @Test
    public void testUpdateBook() throws BusinessConnectionException{
        ChapterBookDAO chapterBookDAO = new ChapterBookDAO();
        Evidence evidence = new Evidence("SI", "Procesos de la ingeniería de software", "Alicia Ruiz");
        ChapterBook chapterBook = new ChapterBook(evidence, 5, 1, 95, "978-3-16-148410-0", 1, "MCUD940585RDTRER10");
        boolean updateResult = chapterBookDAO.updateChapterBook(chapterBook, 5);
        assertEquals("Prueba correcta, si actualizó", updateResult, true);
    }   
}
