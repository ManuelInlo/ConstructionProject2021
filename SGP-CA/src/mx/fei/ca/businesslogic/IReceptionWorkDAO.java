
package mx.fei.ca.businesslogic;
import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.ReceptionWork;

/**
 * Interface del objeto de acceso a datos de trabajo recepcional
 * @author David Alexander Mijangos Paredes
 * @version 16-06-2021
 */

public interface IReceptionWorkDAO {
    public boolean savedReceptionWork(ReceptionWork receptionWork) throws BusinessConnectionException;
    public boolean updatedReceptionWorkById(ReceptionWork receptionWork, int id) throws BusinessConnectionException;
    public ArrayList<ReceptionWork> findReceptionWorksByPositiveImpactCA() throws BusinessConnectionException;
    public ArrayList<ReceptionWork> findLastTwoReceptionWorksByCurpIntegrant(String curp) throws BusinessConnectionException;
    public ArrayList<ReceptionWork> findReceptionWorkByInitialesOfTitle(String InitialesTitleReceptionWork, String curp) throws BusinessConnectionException;
    public boolean existsReceptionWorkTitle(String titleReceptionWork) throws BusinessConnectionException;
    public boolean existsReceptionWorkFileRoute(String fileRoute) throws BusinessConnectionException;
    public boolean existsReceptionWorkTitleForUpdate(String titleReceptionWork, int id) throws BusinessConnectionException;
    public boolean existsReceptionWorkFileRouteForUpdate(String fileRoute, int id) throws BusinessConnectionException;
}
