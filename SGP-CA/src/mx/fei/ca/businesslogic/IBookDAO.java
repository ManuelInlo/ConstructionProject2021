
package mx.fei.ca.businesslogic;

import java.sql.Date;
import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Book;

public interface IBookDAO {
    public boolean savedBook (Book book) throws BusinessConnectionException;
    public boolean updatedBook (Book book, String ISBN) throws BusinessConnectionException;  
    public ArrayList<Book> findBooksByPositiveImpactCA() throws BusinessConnectionException;
    public ArrayList<Book> findBooksByDateAndImpactCA(Date date) throws BusinessConnectionException;
    public ArrayList<Book> findLastTwoBooksByCurpIntegrant(String curp) throws BusinessConnectionException;
    public ArrayList<Book> findBookByInitialesOfTitle(String InitialesTitleBook, String curp) throws BusinessConnectionException;
    public boolean existsBookTitle(String titleBook) throws BusinessConnectionException;
    public boolean existsBookFileRoute(String fileRoute) throws BusinessConnectionException;
    public boolean existsBookTitleForUpdate(String titleBook, String issn) throws BusinessConnectionException;
    public boolean existsBookFileRouteForUpdate(String fileRoute, String isbn) throws BusinessConnectionException;
    public Book findBookByIsbn(String isbn) throws BusinessConnectionException;
}
