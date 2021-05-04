
package mx.fei.ca.businesslogic;

import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Memorandum;


public interface IMemorandumDAO {
    public boolean savedMemorandum(Memorandum memorandum, int idMeeting) throws BusinessConnectionException;
    public boolean updatedMemorandum(Memorandum memorandum, int idMemorandum, int idMeeting) throws BusinessConnectionException;
    public Memorandum findMemorandumByIdMeeting(int idMeeting) throws BusinessConnectionException;
}
