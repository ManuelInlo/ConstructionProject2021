
package testbusinesslogic;

import java.util.ArrayList;
import mx.fei.ca.businesslogic.PrerequisiteDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Prerequisite;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Clase para representar los test de la clase PrerequisiteDAO
 * @author David Alexander Mijangos Paredes
 * @version 17-06-2021
 */

public class PrerequisiteTest {
    
    /**
     * Constructor para la creación de un PrerequisiteTest
     */
    
    public PrerequisiteTest(){
        
    }
    
    /**
     * Método que realiza test para la inserción de un nuevo prerequisito de reunión en la base de datos
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testInsertPrerequisite() throws BusinessConnectionException{
        PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
        Prerequisite prerequisite = new Prerequisite("Llevar informe de la reunión antepasada", "Juan Carlos Pérez Arriaga");
        boolean saveResult = prerequisiteDAO.savedPrerequisite(prerequisite, 5);
        assertTrue("Prueba insertar prerequisito", saveResult);
    }
    
    /**
     * Método que realiza test para la modificación de un prerequisito de reunión en la base de datos 
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testUpdatePrerequisite() throws BusinessConnectionException{
        PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
        Prerequisite prerequisite = new Prerequisite("Llevar informe de la reunión pasada", "Juan Carlos Pérez Arriaga");
        boolean updateResult = prerequisiteDAO.updatedPrerequisite(prerequisite, 8, 5);
        assertTrue("Prueba modificar prerequisito", updateResult);
    }
    
    /**
     * Método que realiza test para la eliminación de un prerequisito de reunión de la base de datos
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testDeletePrerequisiteById() throws BusinessConnectionException{
        PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
        boolean deleteResult = prerequisiteDAO.deletedPrerequisiteById(8);
        assertTrue("Prueba borrar prerequisito", deleteResult);
    }
    
    /**
     * Método que realiza test para la obtención de los prerequisitos de una reunión de acuerdo a su identificador
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testFindPrerequisitesByIdMeeting() throws BusinessConnectionException{
        PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
        ArrayList<Prerequisite> prerequisites = prerequisiteDAO.findPrerequisitesByIdMeeting(5);
        assertEquals("Prueba corerecta", prerequisites.size(), 1);
    }
    
    /**
     * Método que realiza test para la obtención del identificador de un prerequisito de reunión de acuerdo a su descripción e identificador de la reunión
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testGetIdPrerequisiteByDescription() throws BusinessConnectionException{
        PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
        int idPrerequisiteExpected = 15;
        int idPrerequisiteResult = prerequisiteDAO.getIdPrerequisiteByDescription("Llevar trabajos recepcionales", 24);
        assertEquals("Prueba recuperar id de prerrequisito", idPrerequisiteExpected, idPrerequisiteResult);
    }
    
}
