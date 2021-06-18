
package testbusinesslogic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.fei.ca.businesslogic.BookDAO;
import mx.fei.ca.businesslogic.IntegrantDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Book;
import mx.fei.ca.domain.Evidence;
import mx.fei.ca.domain.Integrant;
import mx.fei.ca.domain.InvestigationProject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Clase para representar las pruebas unitarias de los métodos de la clase BookDAO
 * @author Gloria Mendoza González
 * @version 16-06-2021
 */

public class BookTest {
    
    /**
     * Constructor vacío de la clase
     */
    
    public BookTest(){
        
    }
    
    /**
     * Test de agregación de libro
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testInsertBook() throws BusinessConnectionException{
        BookDAO bookDAO = new BookDAO();
        String date = "01-10-2004";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        java.sql.Date publicationDate = null;
        try {
            publicationDate = new java.sql.Date(simpleDateFormat.parse(date).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(BookTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        InvestigationProject investigationProject = new InvestigationProject();
        investigationProject.setIdProject(1);
        
        IntegrantDAO integrantDAO = new IntegrantDAO();
        Integrant integrant = integrantDAO.findIntegrantByCurp("JCPA940514RDTREOP1");  
        
        Evidence evidence = new Evidence("SI", "Procesos de la ingeniería de software", "Alicia Ruiz");
        Book book = new Book(evidence, "908-3-16-148410-4", 1, 95, "Responsable", "Publicado", "México", 
                publicationDate, "CUO", "Quinta edición", "C:\\usuarios\\alicia\\arc1.fil");
        
        book.setCurp(integrant.getCurp());
        book.setInvestigationProject(investigationProject);
        
        boolean saveResult = bookDAO.savedBook(book);
        assertEquals("Prueba correcta, si guardó", saveResult, true);
    }
   
    /**
     * Test de modificación de un libro previamente registrado
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testUpdateBook() throws BusinessConnectionException{
        BookDAO bookDAO = new BookDAO();
        String date = "27-12-2008";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        java.sql.Date publicationDate = null;
        try {
            publicationDate = new java.sql.Date(simpleDateFormat.parse(date).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(BookTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        InvestigationProject investigationProject = new InvestigationProject();
        investigationProject.setIdProject(1);
        
        IntegrantDAO integrantDAO = new IntegrantDAO();
        Integrant integrant = integrantDAO.findIntegrantByCurp("JCPA940514RDTREOP1"); 
        
        Evidence evidence = new Evidence("SI", "Procesos de la ingeniería de software", "Alicia Ruiz");
        Book book = new Book(evidence, "978-3-16-148410-2", 1, 95, "Responsable", "Publicado", "México", 
                publicationDate, "CUO", "Sexta edición", "C:\\usuarios\\alicia\\arc1.fil");
        
        book.setCurp(integrant.getCurp());
        book.setInvestigationProject(investigationProject);  
        
        boolean updateResult = bookDAO.updatedBook(book, "978-3-16-148410-2");
        assertEquals("Prueba correcta, si actualizó", updateResult, true);
    }
    
    /**
     * Test de búsqueda de libros por su impacto positivo al CA
     * @throws BusinessConnectionException 
     */  
    
    @Test
    public void testFindBooksByPositiveImpactCA() throws BusinessConnectionException{
        BookDAO bookDAO = new BookDAO();
        ArrayList<Book> books = bookDAO.findBooksByPositiveImpactCA();
        assertEquals("Prueba busqueda de libro por su impacto al CA", books.size(), 6);
    }
    
    /**
     * Test de búsqueda de libros de un integrante
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testFindBooksByCurpIntegrant() throws BusinessConnectionException{
        BookDAO bookDAO = new BookDAO();
        ArrayList<Book> books = bookDAO.findLastTwoBooksByCurpIntegrant("MCUD940585RDTRER10");
        assertEquals("Prueba busqueda de libros de un integrante por curp", books.size(), 2);
    }
    
    /**
     * Test de búsqueda de libros por las iniciales del titulo
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testFindBookByInitialesOfTitle() throws BusinessConnectionException{
        BookDAO bookDAO = new BookDAO();
        ArrayList <Book> books = bookDAO.findBookByInitialesOfTitle("software", "MCUD940585RDTRER10");
        assertEquals("Prueba encontrar libro por título", books.size(), 4);
    }
    
    /**
     * Test de verificación de existencia del título de un libro
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testExistsBookTitle() throws BusinessConnectionException{
        BookDAO bookDAO = new BookDAO();
        boolean exists = bookDAO.existsBookTitle("Procesos de la ingeniería de software");
        assertTrue("Prueba mandar a validar un titulo que ya existe de un libro", exists);
    }
    
    /**
     * Test de verificación de existencia de la ruta de archivo de un libro
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testExistsBookFileRoute() throws BusinessConnectionException{
        BookDAO bookDAO = new BookDAO();
        boolean exists = bookDAO.existsBookFileRoute("C:\\usuarios\\alicia\\arc1.fil");
        assertTrue("Prueba mandar a validar una ruta de archivo que ya existe en un libro", exists);
    }  
    
    /**
     * Test de verificación de existencia de título de libro para modificación 
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testExistsBookTitleForUpdate() throws BusinessConnectionException{
        BookDAO bookDAO = new BookDAO();
        boolean exists = bookDAO.existsBookTitleForUpdate("Datos de la ingeniería de software", "908-3-16-148410-4");
        assertFalse("Prueba mandar a validar un titulo modificado que no existe de libro", exists);
    }
     
    /**
     * Test de verificación de existencia de ruta de archivo para modificación de un libro
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testExistsBookFileRouteForUpdate() throws BusinessConnectionException{
        BookDAO bookDAO = new BookDAO();
        boolean exists = bookDAO.existsBookFileRouteForUpdate("C:\\Users\\juan\\Documents\\Datos de AI.pdf", "978-3-16-148410-0");
        assertFalse("Prueba mandar a validar una ruta de archivo modificada que no existe en un libro", exists);
    }    
    
    /**
     * Test de búsqueda por fecha de libros que impactan positivamente al CA
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testFindBooksByDateAndImpactCA() throws BusinessConnectionException{
        BookDAO bookDAO = new BookDAO();
        String date = "01-10-2004";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        java.sql.Date publicationDate = null;
        try {
            publicationDate = new java.sql.Date(simpleDateFormat.parse(date).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(BookTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList <Book> books = bookDAO.findBooksByDateAndImpactCA(publicationDate);
        assertEquals("Prueba encontrar libro por fecha de publicación", books.size(), 2);
    }
    
}
