
package testbusinesslogic;

import java.util.ArrayList;
import mx.fei.ca.businesslogic.PrerequisiteDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Prerequisite;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class PrerequisiteTest {
    
    public PrerequisiteTest(){
        
    }
    
    @Test
    public void testInsertPrerequisite() throws BusinessConnectionException{
        PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
        Prerequisite prerequisite = new Prerequisite("Contemplar las evidencias a tratar", "Juan Carlos Pérez Arriaga");
        int saveResult = prerequisiteDAO.savePrerequisite(prerequisite, 2);
        assertEquals("Prueba insertar prerequisito", saveResult, 1);
    }
    
    @Test
    public void testUpdatePrerequisite() throws BusinessConnectionException{
        PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
        Prerequisite prerequisite = new Prerequisite("Contemplar los artículos a tratar", "Juan Carlos Pérez Arriaga");
        int updateResult = prerequisiteDAO.updatePrerequisite(prerequisite, 3, 2);
        assertEquals("Prueba correcta, si modificó", updateResult, 1);
    }
    
    @Test
    public void testDeletePrerequisiteById() throws BusinessConnectionException{
        PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
        int deleteResult = prerequisiteDAO.deletePrerequisiteById(3);
        assertEquals("Prueba correcta, si eliminó", deleteResult, 1);
    }
    
    @Test
    public void testFindPrerequisitesByIdMeeting() throws BusinessConnectionException{
        PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
        ArrayList<Prerequisite> prerequisites = prerequisiteDAO.findPrerequisitesByIdMeeting(2);
        assertEquals("Prueba corerecta", prerequisites.size(), 1);
    }
    
}
