
package testbusinesslogic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.fei.ca.businesslogic.BookDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Book;
import mx.fei.ca.domain.Evidence;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class BookTest {
    public BookTest(){
        
    }
    
    @Test
    public void testInsertBook() throws BusinessConnectionException{
        BookDAO bookDAO = new BookDAO();
        String date = "01-10-2004";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        java.sql.Date publicationDate = null;
        try {
            publicationDate = new java.sql.Date(simpleDateFormat.parse(date).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(IntegrantTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Evidence evidence = new Evidence("SI", "Procesos de la ingeniería de software", "Alicia Ruiz");
        Book book = new Book(evidence, "978-3-16-148410-2", 1, 95, "Responsable", "Publicado",
                    "México", publicationDate, "CUO", "Quinta edición", "C:\\usuarios\\alicia\\arc1.fil", 1, "MCUD940585RDTRER10");
        boolean saveResult = bookDAO.saveBook(book);
        assertEquals("Prueba correcta, si guardó", saveResult, true);
    }
    
    @Test
    public void testUpdateBook() throws BusinessConnectionException{
        BookDAO bookDAO = new BookDAO();
        String date = "27-12-2008";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        java.sql.Date publicationDate = null;
        try {
            publicationDate = new java.sql.Date(simpleDateFormat.parse(date).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(IntegrantTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Evidence evidence = new Evidence("SI", "Procesos de la ingeniería de software", "Alicia Ruiz");
        Book book = new Book(evidence, "978-3-16-148410-2", 1, 95, "Responsable", "Publicado",
                    "México", publicationDate, "CUO", "Sexta edición", "C:\\usuarios\\alicia\\arc1.fil", 1, "MCUD940585RDTRER10");
        boolean updateResult = bookDAO.updateBook(book, "978-3-16-148410-2");
        assertEquals("Prueba correcta, si actualizó", updateResult, true);
    }
}
