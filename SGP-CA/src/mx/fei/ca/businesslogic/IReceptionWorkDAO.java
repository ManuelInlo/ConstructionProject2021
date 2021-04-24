
package mx.fei.ca.businesslogic;
import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.ReceptionWork;

public interface IReceptionWorkDAO {
    public int saveReceptionWork(ReceptionWork receptionWork) throws BusinessConnectionException;
    public int updateReceptionWorkById(ReceptionWork receptionWork, int id) throws BusinessConnectionException;
    public ArrayList<ReceptionWork> findReceptionWorksByPositiveImpactCA() throws BusinessConnectionException;
    public ArrayList<ReceptionWork> findReceptionWorksByCurpIntegrant(String curp) throws BusinessConnectionException;
    public ReceptionWork findReceptionWorkByCurpAndTitle(String titleReceptionWork, String curp) throws BusinessConnectionException;
}