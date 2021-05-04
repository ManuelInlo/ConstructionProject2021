
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
        Prerequisite prerequisite = new Prerequisite("Contemplar las evidencias a tratar", "Juan Carlos Pérez Arriaga");
        boolean saveResult = prerequisiteDAO.savedPrerequisite(prerequisite, 5);
        assertTrue("Prueba insertar prerequisito", saveResult);
    }
    
    @Test
    public void testUpdatePrerequisite() throws BusinessConnectionException{
        PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
        Prerequisite prerequisite = new Prerequisite("Contemplar los artículos a tratar", "Juan Carlos Pérez Arriaga");
        boolean updateResult = prerequisiteDAO.updatedPrerequisite(prerequisite, 3, 2);
        assertTrue("Prueba modificar prerequisito", updateResult);
    }
    
    @Test
    public void testDeletePrerequisiteById() throws BusinessConnectionException{
        PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
        boolean deleteResult = prerequisiteDAO.deletedPrerequisiteById(3);
        assertTrue("Prueba borrar prerequisito", deleteResult);
    }
    
    @Test
    public void testFindPrerequisitesByIdMeeting() throws BusinessConnectionException{
        PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
        ArrayList<Prerequisite> prerequisites = prerequisiteDAO.findPrerequisitesByIdMeeting(2);
        assertEquals("Prueba corerecta", prerequisites.size(), 1);
    }
    
    @Test
    public void testExistsPrerequisiteDescription() throws BusinessConnectionException{
        PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
        boolean exists = prerequisiteDAO.existsPrerequisiteDescription("Contemplar las evidencias a tratar", 5);
        assertTrue("Prueba mandar a validar la descripción ya existente de un prerequisito", exists);
    }
    
    @Test
    public void testExistsPrerequisiteDescriptionForUpdate() throws BusinessConnectionException{
        PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
        boolean exists = prerequisiteDAO.existsPrerequisiteDescriptionForUpdate("Contemplar anteproyectos",5,4);
        assertFalse("Prueba mandar a validar la descripción modificada que no existente de un prerequisito", exists);
    }
    
}
