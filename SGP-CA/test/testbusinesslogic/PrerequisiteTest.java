
package testbusinesslogic;

import java.util.ArrayList;
import mx.fei.ca.businesslogic.PrerequisiteDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Prerequisite;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class PrerequisiteTest {
    
    public PrerequisiteTest(){
        
    }
    
    @Test
    public void testInsertPrerequisite() throws BusinessConnectionException{
        PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
        Prerequisite prerequisite = new Prerequisite("Llevar informe de la reunión antepasada", "Juan Carlos Pérez Arriaga");
        boolean saveResult = prerequisiteDAO.savedPrerequisite(prerequisite, 5);
        assertTrue("Prueba insertar prerequisito", saveResult);
    }
    
    @Test
    public void testUpdatePrerequisite() throws BusinessConnectionException{
        PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
        Prerequisite prerequisite = new Prerequisite("Llevar informe de la reunión pasada", "Juan Carlos Pérez Arriaga");
        boolean updateResult = prerequisiteDAO.updatedPrerequisite(prerequisite, 8, 5);
        assertTrue("Prueba modificar prerequisito", updateResult);
    }
    
    @Test
    public void testDeletePrerequisiteById() throws BusinessConnectionException{
        PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
        boolean deleteResult = prerequisiteDAO.deletedPrerequisiteById(8);
        assertTrue("Prueba borrar prerequisito", deleteResult);
    }
    
    @Test
    public void testFindPrerequisitesByIdMeeting() throws BusinessConnectionException{
        PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
        ArrayList<Prerequisite> prerequisites = prerequisiteDAO.findPrerequisitesByIdMeeting(5);
        assertEquals("Prueba corerecta", prerequisites.size(), 1);
    }
    
}
