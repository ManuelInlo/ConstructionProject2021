
package mx.fei.ca.businesslogic;

import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Prerequisite;


public interface IPrerequisiteDAO {
    public boolean savedPrerequisite(Prerequisite prerequisite, int idMeeting) throws BusinessConnectionException;
    public boolean updatedPrerequisite(Prerequisite prerequisite, int idPrerequisite, int idMeeting) throws BusinessConnectionException;
    public boolean deletedPrerequisiteById(int idPrerequisite)throws BusinessConnectionException;
    public ArrayList<Prerequisite> findPrerequisitesByIdMeeting(int idMeeting) throws BusinessConnectionException;
    public boolean existsPrerequisiteDescription(String description, int idMeeting) throws BusinessConnectionException;
    public boolean existsPrerequisiteDescriptionForUpdate(String description, int idMeeting, int idPrerequiste) throws BusinessConnectionException;
}
