
package mx.fei.ca.businesslogic;

import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Prerequisite;


public interface IPrerequisiteDAO {
    public int savePrerequisite(Prerequisite prerequisite, int idMeeting) throws BusinessConnectionException;
    public int updatePrerequisite(Prerequisite prerequisite, int idPrerequisite, int idMeeting) throws BusinessConnectionException;
    public int deletePrerequisiteById(int idPrerequisite)throws BusinessConnectionException;
    public ArrayList<Prerequisite> findPrerequisitesByIdMeeting(int idMeeting) throws BusinessConnectionException;
}
