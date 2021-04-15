
package mx.fei.ca.businesslogic;

import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Memorandum;


public interface IMemorandumDAO {
    public int saveMemorandum(Memorandum memorandum, int idMeeting) throws BusinessConnectionException;
    public int updateMemorandum(Memorandum memorandum, int idMemorandum, int idMeeting) throws BusinessConnectionException;
    public int deleteMemorandumById(int idMemorandum) throws BusinessConnectionException;
    public Memorandum findMemorandumByIdMeeting(int idMeeting) throws BusinessConnectionException;
}
