
package mx.fei.ca.businesslogic;

import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Collaborator;

public interface ICollaboratorDAO {
   public int saveCollaborator(Collaborator collaborator) throws BusinessConnectionException;
   public int updateCollaboratorByIdCollaborator(Collaborator collaborator, int idCollaborator) throws BusinessConnectionException;
   public Collaborator findCollaboratorByIdCollaborator(int idCollaborator) throws BusinessConnectionException;
}
