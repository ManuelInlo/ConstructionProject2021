
package mx.fei.ca.businesslogic;

import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Book;

public interface IBookDAO {
    public boolean saveBook (Book book) throws BusinessConnectionException;
    public boolean updateBook (Book book, String ISBN) throws BusinessConnectionException;        
}
