
package mx.fei.ca.businesslogic;

import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.ChapterBook;

/**
 * Interface del objeto de acceso a datos de capítulo de libro
 * @author Gloria Mendoza González
 * @version 16-06-2021
 */

public interface IChapterBookDAO {
    public int saveAndReturnIdNewChapterBook (ChapterBook chapterBook) throws BusinessConnectionException;
    public boolean updatedChapterBook (ChapterBook chapterBook, int chapterNumber) throws BusinessConnectionException;   
    public ArrayList<ChapterBook> findChapterBooksByPositiveImpactCA() throws BusinessConnectionException;
    public ArrayList<ChapterBook> findLastTwoChapterBooksByCurpIntegrant(String curp) throws BusinessConnectionException;
    public ArrayList<ChapterBook> findChapterBookByCurpIntegrantInitialesOfTitle(String initialesTitleChapter, String curp) throws BusinessConnectionException;
    public boolean existsChapterBookTitle(String titleChapterBook) throws BusinessConnectionException;  
    public boolean existsNumberChapterByBook(int numberChapter, String isbn) throws BusinessConnectionException; 
    public int findChapterBooksByCurpIntegrant(String curp) throws BusinessConnectionException;     
}
