
package testbusinesslogic;

import mx.fei.ca.businesslogic.CollaboratorDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Collaborator;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class CollaboratorTest {
    
    public CollaboratorTest(){
        
    }
    
    @Test
    public void testInsertCollaborator() throws BusinessConnectionException{
        CollaboratorDAO collaboratorDAO = new CollaboratorDAO();
        Collaborator collaborator = new Collaborator("Roberto Rodríguez Jiménez", "Estudiante");
        int idCollaboratorResult = collaboratorDAO.saveCollaborator(collaborator);
        assertNotSame("Prueba insertar colaborador", idCollaboratorResult, 0);
    }
    
    @Test 
    public void testUpdateCollaboratorByIdCollaborator() throws BusinessConnectionException{
        CollaboratorDAO collaboratorDAO = new CollaboratorDAO();
        Collaborator collaborator = new Collaborator("Rodrigo Rodríguez Jiménez", "Estudiante");
        int updateResult = collaboratorDAO.updateCollaboratorByIdCollaborator(collaborator, 1);
        assertEquals("Prueba insertar colaborador", updateResult, 1);
    }
    
    @Test
    public void testFindCollaboratorByIdCollaborator() throws BusinessConnectionException{
        CollaboratorDAO collaboratorDAO = new CollaboratorDAO();
        Collaborator collaborator = collaboratorDAO.findCollaboratorByIdCollaborator(1);
        String nameCollaboratorExpected = "Rodrigo Rodríguez Jiménez";
        assertEquals("Prueba buscar colaborador por id", nameCollaboratorExpected, collaborator.getName());
    }
    
    @Test 
    public void testValidateExistenceOfCollaboratorName() throws BusinessConnectionException{
        CollaboratorDAO collaboratorDAO = new CollaboratorDAO();
        boolean exists = collaboratorDAO.validateExistenceOfCollaboratorName("Roberto Méndez Mendoza");
        assertTrue("Prueba mandar a validar el nombre que ya existe de un colaborador", exists);
    }
}
