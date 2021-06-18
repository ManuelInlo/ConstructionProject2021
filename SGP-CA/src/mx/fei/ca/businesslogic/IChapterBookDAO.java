
package mx.fei.ca.businesslogic;

import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.ChapterBook;

public interface IChapterBookDAO {
    public boolean savedChapterBook (ChapterBook chapterBook) throws BusinessConnectionException;
    public boolean updatedChapterBook (ChapterBook chapterBook, int chapterNumber) throws BusinessConnectionException;   
    public ArrayList<ChapterBook> findChapterBooksByPositiveImpactCA() throws BusinessConnectionException;
    public ArrayList<ChapterBook> findLastTwoChapterBooksByCurpIntegrant(String curp) throws BusinessConnectionException; 
}
