
package testbusinesslogic;

import java.util.ArrayList;
import mx.fei.ca.businesslogic.InvestigationProjectDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.InvestigationProject;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Clase para representar los test de la clase InvestigationProjectDAO
 * @author David Alexander Mijangos Paredes
 * @version 17-06-2021
 */

public class InvestigationProjectTest {
    
    /**
     * Constructor para la creación de un InvestigationProjectTest
     */
    
    public InvestigationProjectTest(){
        
    }
    
    /**
     * Método que realiza test para la obtención de un proyecto de investigación de acuerdo a su identificador
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testFindInvestigationProjectById() throws BusinessConnectionException{
        InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
        String titleExpected = "Inteligencia artificial";
        InvestigationProject investigationProject = investigationProjectDAO.findInvestigationProjectById(1);
        String titleResult = investigationProject.getTittleProject();
        assertEquals("Prueba obtener proyecto de investigación por id",titleExpected ,titleResult );
    }
    
    /**
     * Método que realiza test para la obtención de todos los proyectos de investigación
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testFindAllInvestigationProjects() throws BusinessConnectionException{
        InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
        ArrayList<InvestigationProject> investigationProjects = investigationProjectDAO.findAllInvestigationProjects();
        assertEquals("Prubea recuperar todos los proyectos de investigación", investigationProjects.size(), 1);
    }
    
    
}
