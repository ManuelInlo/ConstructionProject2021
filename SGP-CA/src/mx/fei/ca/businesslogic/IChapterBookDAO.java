
package mx.fei.ca.businesslogic;

import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.ChapterBook;

public interface IChapterBookDAO {
    public boolean savedChapterBook (ChapterBook chapterBook) throws BusinessConnectionException;
    public boolean updatedChapterBook (ChapterBook chapterBook, int chapterNumber) throws BusinessConnectionException;    
}
