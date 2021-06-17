
package mx.fei.ca.businesslogic;

import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Book;

public interface IBookDAO {
    public boolean savedBook (Book book) throws BusinessConnectionException;
    public boolean updatedBook (Book book, String ISBN) throws BusinessConnectionException;  
    public ArrayList<Book> findBooksByPositiveImpactCA() throws BusinessConnectionException;
}
