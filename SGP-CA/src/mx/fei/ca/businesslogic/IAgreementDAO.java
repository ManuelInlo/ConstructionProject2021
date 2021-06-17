
package mx.fei.ca.businesslogic;

import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Agreement;

/**
 * Interface del objeto de acceso a datos de acuerdo
 * @author David Alexander Mijangos Paredes
 * @version 16-06-2021
 */

public interface IAgreementDAO {
    public boolean savedAgreement(Agreement agreement, int idMemorandum) throws BusinessConnectionException;
    public ArrayList<Agreement> findAgreementsByIdMemorandum (int idMemorandum) throws BusinessConnectionException;
    public boolean updatedAgreement(Agreement agreement, int idAgreement, int idMemorandum) throws BusinessConnectionException;
    public boolean deletedAgreementById(int idAgreement) throws BusinessConnectionException;
    public int getIdAgreementByDescription(String description) throws BusinessConnectionException;
}
