
package testbusinesslogic;

import java.util.ArrayList;
import mx.fei.ca.businesslogic.BookDAO;
import mx.fei.ca.businesslogic.ChapterBookDAO;
import mx.fei.ca.businesslogic.IntegrantDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Book;
import mx.fei.ca.domain.ChapterBook;
import mx.fei.ca.domain.Evidence;
import mx.fei.ca.domain.Integrant;
import mx.fei.ca.domain.InvestigationProject;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Clase para representar las pruebas unitarias de los métodos de la clase ChapterBookDAO
 * @author Gloria Mendoza González
 * @version 17-06-2021
 */

public class ChapterBookTest {
    
    /**
     * Constructor vacío de la clase
     */
    
    public ChapterBookTest(){
    
    }
    
    /**
     * Test de agregación de  capítulo de libro
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testInsertChapterBook() throws BusinessConnectionException{
        ChapterBookDAO chapterBookDAO = new ChapterBookDAO();
        InvestigationProject investigationProject = new InvestigationProject();
        investigationProject.setIdProject(1);
        
        IntegrantDAO integrantDAO = new IntegrantDAO();
        Integrant integrant = integrantDAO.findIntegrantByCurp("JCPA940514RDTREOP1");  
        
        BookDAO bookDAO = new BookDAO();
        Book book = bookDAO.findBookByIsbn("908-3-16-148410-4");
        
        Evidence evidence = new Evidence("SI", "Procesos de la ingeniería de software", "Mauricio Piñeda");
        ChapterBook chapterBook = new ChapterBook(evidence, 4 , 1, 95);
        
        chapterBook.setCurp(integrant.getCurp());
        chapterBook.setInvestigationProject(investigationProject);
        chapterBook.setBook(book);
        
        boolean saveResult = chapterBookDAO.savedChapterBook(chapterBook);
        assertEquals("Prueba correcta, si guardó", saveResult, true);
    }
    
    /**
     * Test de modificación de un capítulo libro previamente registrado
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testUpdateChapterBook() throws BusinessConnectionException{
        ChapterBookDAO chapterBookDAO = new ChapterBookDAO();
        InvestigationProject investigationProject = new InvestigationProject();
        investigationProject.setIdProject(1);
        
        IntegrantDAO integrantDAO = new IntegrantDAO();
        Integrant integrant = integrantDAO.findIntegrantByCurp("JCPA940514RDTREOP1");  
        
        BookDAO bookDAO = new BookDAO();
        Book book = bookDAO.findBookByIsbn("908-3-16-148410-4");
        
        Evidence evidence = new Evidence("SI", "Ingeniería de software y la IA", "Mauricio Piñeda");
        ChapterBook chapterBook = new ChapterBook(evidence, 1 , 62, 95);
        
        chapterBook.setCurp(integrant.getCurp());
        chapterBook.setInvestigationProject(investigationProject);
        chapterBook.setBook(book);
        
        boolean updateResult = chapterBookDAO.updatedChapterBook(chapterBook, 1);
        assertEquals("Prueba correcta, si actualizó", updateResult, true);
    }   
    
    /**
     * Test de búsqueda de capítulos de libros por su impacto positivo al CA
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testFindChapterBooksByPositiveImpactCA() throws BusinessConnectionException{
        ChapterBookDAO chapterBookDAO = new ChapterBookDAO();
        ArrayList<ChapterBook> chapterBooks = chapterBookDAO.findChapterBooksByPositiveImpactCA();
        assertEquals("Prueba busqueda de capítulos de libro por su impacto al CA", chapterBooks.size(), 3);
    }
    
    /**
     * Test de búsqueda de capítulos de libros de un integrante
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testFindChapterBooksByCurpIntegrant() throws BusinessConnectionException{
        ChapterBookDAO chapterBookDAO = new ChapterBookDAO();
        ArrayList<ChapterBook> chapterBooks = chapterBookDAO.findLastTwoChapterBooksByCurpIntegrant("JCPA940514RDTREOP1");
        assertEquals("Prueba busqueda de capítulos de libros de un integrante por curp", chapterBooks.size(), 2);
    }
    
}
