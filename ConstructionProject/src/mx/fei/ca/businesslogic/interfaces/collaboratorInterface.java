package mx.fei.ca.businesslogic.interfaces;

import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.businesslogic.exceptions.BusinessDataException;
import mx.fei.ca.domain.Collaborator;

/**
 *
 * @author inigu
 */
public interface collaboratorInterface {
    public int saveCollaborator(Collaborator collaborator) throws BusinessConnectionException, BusinessDataException;
    public int updateCollaborator(Collaborator collaborator, int idCollaborator, String nameCollaborator, String position) throws BusinessConnectionException;
    public int deleteAgreementById(String idCollaborator) throws BusinessConnectionException, BusinessDataException; 
}

