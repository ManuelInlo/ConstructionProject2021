
package mx.fei.ca.businesslogic;

import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Prerequisite;

/**
 * Interface del objeto de acceso a datos de prerequisito
 * @author David Alexander Mijangos Paredes
 * @version 16-06-2021
 */

public interface IPrerequisiteDAO {
    public boolean savedPrerequisite(Prerequisite prerequisite, int idMeeting) throws BusinessConnectionException;
    public boolean updatedPrerequisite(Prerequisite prerequisite, int idPrerequisite, int idMeeting) throws BusinessConnectionException;
    public boolean deletedPrerequisiteById(int idPrerequisite)throws BusinessConnectionException;
    public ArrayList<Prerequisite> findPrerequisitesByIdMeeting(int idMeeting) throws BusinessConnectionException;
    public int getIdPrerequisiteByDescription(String description) throws BusinessConnectionException;
}
