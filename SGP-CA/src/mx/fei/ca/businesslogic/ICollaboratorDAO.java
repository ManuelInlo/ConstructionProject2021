
package mx.fei.ca.businesslogic;

import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Collaborator;

public interface ICollaboratorDAO {
   public int saveCollaboratorAndReturnId(Collaborator collaborator) throws BusinessConnectionException;
   public boolean updatedCollaboratorByIdCollaborator(Collaborator collaborator, int idCollaborator) throws BusinessConnectionException;
   public Collaborator findCollaboratorByIdCollaborator(int idCollaborator) throws BusinessConnectionException;
   public boolean existsCollaboratorName(String nameCollaborator) throws BusinessConnectionException;
   public boolean existsCollaboratorNameForUpdate(String nameCollaborator, int idCollaborator) throws BusinessConnectionException;
}
