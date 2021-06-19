
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
    public boolean savedChapterBook (ChapterBook chapterBook) throws BusinessConnectionException;
    public boolean updatedChapterBook (ChapterBook chapterBook, int chapterNumber) throws BusinessConnectionException;   
    public ArrayList<ChapterBook> findChapterBooksByPositiveImpactCA() throws BusinessConnectionException;
    public ArrayList<ChapterBook> findLastTwoChapterBooksByCurpIntegrant(String curp) throws BusinessConnectionException; 
}
