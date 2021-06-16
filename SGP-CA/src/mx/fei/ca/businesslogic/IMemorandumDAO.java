
package mx.fei.ca.businesslogic;

import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Memorandum;


public interface IMemorandumDAO {
    public int saveAndReturnIdMemorandum(Memorandum memorandum, int idMeeting) throws BusinessConnectionException;
    public boolean updatedMemorandum(Memorandum memorandum, int idMemorandum) throws BusinessConnectionException;
    public Memorandum findMemorandumByIdMeeting(int idMeeting) throws BusinessConnectionException;
}
