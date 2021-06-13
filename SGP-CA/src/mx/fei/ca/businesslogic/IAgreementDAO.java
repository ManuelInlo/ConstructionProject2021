
package mx.fei.ca.businesslogic;

import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Agreement;


public interface IAgreementDAO {
    public boolean savedAgreement(Agreement agreement, int idMemorandum) throws BusinessConnectionException;
    public ArrayList<Agreement> findAgreementsByIdMemorandum (int idMemorandum) throws BusinessConnectionException;
    public boolean updatedAgreement(Agreement agreement, int idAgreement, int idMemorandum) throws BusinessConnectionException;
    public boolean deletedAgreementById(int idAgreement) throws BusinessConnectionException;
    public boolean existsAgreementDescription(String description, int idMemorandum) throws BusinessConnectionException;
    public boolean existsAgreementDescriptionForUpdate(String description, int idMemorandum, int idAgreement) throws BusinessConnectionException;
}
