package mx.fei.ca.businesslogic;

import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.InvestigationProject;

/**
 * Interface del objeto de acceso a datos de proyecto de investigación
 * @author David Alexander Mijangos Paredes
 * @version 17-06-2021
 */

public interface IInvestigationProjectDAO {
    public InvestigationProject findInvestigationProjectById(int idProject) throws BusinessConnectionException;
    public ArrayList<InvestigationProject> findAllInvestigationProjects() throws BusinessConnectionException;
}
