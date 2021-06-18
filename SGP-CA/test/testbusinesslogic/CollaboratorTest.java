
package testbusinesslogic;

import mx.fei.ca.businesslogic.CollaboratorDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Collaborator;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Clase para representar los test de la clase CollaboratorDAO
 * @author David Alexander Mijangos Paredes
 * @version 17-06-2021
 */

public class CollaboratorTest {
    
    /**
     * Constructor para la creación de un CollaboratorTest
     */
    
    public CollaboratorTest(){
        
    }
    
    /**
     * Método que realiza test para la inserción de un nuevo colaborador a la base de datos
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testInsertCollaborator() throws BusinessConnectionException{
        CollaboratorDAO collaboratorDAO = new CollaboratorDAO();
        Collaborator collaborator = new Collaborator("Roberto Rodríguez Jiménez", "Estudiante");
        int idCollaboratorResult = collaboratorDAO.saveCollaboratorAndReturnId(collaborator);
        assertNotSame("Prueba insertar colaborador", idCollaboratorResult, 0);
    }
    
    /**
     * Método que realiza test para la modificación de un colaborador de acuerdo a su identificador
     * @throws BusinessConnectionException 
     */
    
    @Test 
    public void testUpdateCollaboratorByIdCollaborator() throws BusinessConnectionException{
        CollaboratorDAO collaboratorDAO = new CollaboratorDAO();
        Collaborator collaborator = new Collaborator("Rodrigo Rodríguez Jiménez", "Estudiante");
        boolean updateResult = collaboratorDAO.updatedCollaboratorByIdCollaborator(collaborator, 13);
        assertTrue("Prueba modificar colaborador", updateResult);
    }
    
    /**
     * Método que realiza test para la obtención de un colaborador de acuerdo a su identificador
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testFindCollaboratorByIdCollaborator() throws BusinessConnectionException{
        CollaboratorDAO collaboratorDAO = new CollaboratorDAO();
        Collaborator collaborator = collaboratorDAO.findCollaboratorByIdCollaborator(13);
        String nameCollaboratorExpected = "Rodrigo Rodríguez Jiménez";
        assertEquals("Prueba buscar colaborador por id", nameCollaboratorExpected, collaborator.getName());
    }
    
    /**
     * Método que realiza test para verificar la existencia de un nombre de colaborador en la base de datos
     * @throws BusinessConnectionException 
     */
    
    @Test 
    public void testExistsCollaboratorName() throws BusinessConnectionException{
        CollaboratorDAO collaboratorDAO = new CollaboratorDAO();
        boolean exists = collaboratorDAO.existsCollaboratorName("Rodrigo Rodríguez Jiménez");
        assertTrue("Prueba mandar a validar el nombre que ya existe de un colaborador", exists);
    }
    
    /**
     * Método que realiza test para verificar la existencia de un nombre de colaborador en la base de datos para modificación
     * @throws BusinessConnectionException 
     */
    
    @Test 
    public void testExistsCollaboratorNameForUpdate() throws BusinessConnectionException{
        CollaboratorDAO collaboratorDAO = new CollaboratorDAO();
        boolean exists = collaboratorDAO.existsCollaboratorNameForUpdate("Roberto José Mendoza", 13);
        assertFalse("Prueba mandar a validar el nombre modificado que no existe de un colaborador", exists);
    }
}
