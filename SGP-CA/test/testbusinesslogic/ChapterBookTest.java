
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
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Clase para representar las pruebas unitarias de los métodos de la clase ChapterBookDAO
 * @author Gloria Mendoza González
 * @version 17-06-2021
 */

public class ChapterBookTest {
    
    /**
     * Constructor para la creación de un ChapterBookTest
     */
    
    public ChapterBookTest(){
    
    }
    
    /**
     * Método que realiza test para la inserción de un nuevo capítulo de libro en la base de datos
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
        
        Evidence evidence = new Evidence("SI", "La ingeniería de software", "Mauricio Piñeda");
        ChapterBook chapterBook = new ChapterBook(evidence, 4 , 1, 95);
        
        chapterBook.setCurp(integrant.getCurp());
        chapterBook.setInvestigationProject(investigationProject);
        chapterBook.setBook(book);
        
        int saveResult = chapterBookDAO.saveAndReturnIdNewChapterBook(chapterBook);
        assertEquals("Prueba correcta, si guardó", saveResult, 4);
    }
    
    /**
     * Método que realiza test para la modificación de un capítulo libro específico
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
     * Método que realiza test de búsqueda de capítulos de libros por su impacto positivo al CA
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testFindChapterBooksByPositiveImpactCA() throws BusinessConnectionException{
        ChapterBookDAO chapterBookDAO = new ChapterBookDAO();
        ArrayList<ChapterBook> chapterBooks = chapterBookDAO.findChapterBooksByPositiveImpactCA();
        assertEquals("Prueba busqueda de capítulos de libro por su impacto al CA", chapterBooks.size(), 4);
    }
    
    /**
     * Método que realiza test de búsqueda de capítulos de libros de un integrante
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testFindChapterBooksByCurpIntegrant() throws BusinessConnectionException{
        ChapterBookDAO chapterBookDAO = new ChapterBookDAO();
        ArrayList<ChapterBook> chapterBooks = chapterBookDAO.findLastTwoChapterBooksByCurpIntegrant("JCPA940514RDTREOP1");
        assertEquals("Prueba busqueda de capítulos de libros de un integrante por curp", chapterBooks.size(), 2);
    }
    
    /**
     * Método que realiza test de búsqueda de total de capítulos de libros de un integrante
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testFindNumberChapterBooksByCurpIntegrant() throws BusinessConnectionException{
        ChapterBookDAO chapterBookDAO = new ChapterBookDAO();
        int chapterBooks = chapterBookDAO.findChapterBooksByCurpIntegrant("JCPA940514RDTREOP1");
        assertEquals("Prueba busqueda de total de capítulos de libros de un integrante por curp", chapterBooks, 3);
    }    
    
    /**
     * Método que realiza test para la obtención de capítulo de libro de acuerdo a las iniciales del título
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testFindChapterBooksByByInitialesOfTitle() throws BusinessConnectionException{
        ChapterBookDAO chapterBookDAO = new ChapterBookDAO();
        ArrayList<ChapterBook> chapterBooks = chapterBookDAO.findChapterBookByCurpIntegrantInitialesOfTitle("software", "JCPA940514RDTREOP1");
        assertEquals("Prueba encontrar capítulos de libros por título", chapterBooks.size(), 3);
    }    
    
    /**
     * Método que realiza test para la verificación de existencia de título de capítulo de libro
     * @throws BusinessConnectionException 
     */   
    
    @Test
    public void testExistsChapterBookTitle() throws BusinessConnectionException{
        ChapterBookDAO chapterBookDAO = new ChapterBookDAO();
        boolean exists = chapterBookDAO.existsChapterBookTitle("Ingeniería de software y la IA");
        assertTrue("Prueba mandar a validar un titulo que ya existe de capítulo de libro", exists);
    }
    
    /**
     * Método que realiza test para la verificación de existencia de un número de capítulo de libro de un libro específico
     * @throws BusinessConnectionException 
     */   
    
    @Test
    public void testExistsNumberChapterByBook() throws BusinessConnectionException{
        ChapterBookDAO chapterBookDAO = new ChapterBookDAO();
        boolean exists = chapterBookDAO.existsNumberChapterByBook(4, "908-3-16-148410-4");
        assertTrue("Prueba mandar a validar un número de capítulo de libro de un libro específico existente", exists);
    }    
}
