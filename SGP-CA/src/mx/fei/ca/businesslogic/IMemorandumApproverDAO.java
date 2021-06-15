
package mx.fei.ca.businesslogic;

import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.MemorandumApprover;


public interface IMemorandumApproverDAO {
    public boolean savedMemorandumApprover(MemorandumApprover memorandumApprover, int idMemorandum) throws BusinessConnectionException;
    public ArrayList<MemorandumApprover> findMemorandumApproversByIdMemorandum(int idMemorandum) throws BusinessConnectionException;
    public boolean existsMemorandumApproverByCurp(String curp, int idMemorandum) throws BusinessConnectionException;
}
