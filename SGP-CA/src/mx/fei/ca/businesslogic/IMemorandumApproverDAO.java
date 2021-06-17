
package mx.fei.ca.businesslogic;

import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.MemorandumApprover;

/**
 * Interface del objeto de acceso a datos de aprobador de minuta
 * @author David Alexander Mijangos Paredes
 * @version 16-06-2021
 */

public interface IMemorandumApproverDAO {
    public boolean savedMemorandumApprover(MemorandumApprover memorandumApprover, int idMemorandum) throws BusinessConnectionException;
    public ArrayList<MemorandumApprover> findMemorandumApproversByIdMemorandum(int idMemorandum) throws BusinessConnectionException;
    public boolean existsMemorandumApproverByCurp(String curp, int idMemorandum) throws BusinessConnectionException;
}
