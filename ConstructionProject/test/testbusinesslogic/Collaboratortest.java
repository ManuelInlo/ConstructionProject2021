package testbusinesslogic;

import mx.fei.ca.businesslogic.CollaboratorDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.businesslogic.exceptions.BusinessDataException;
import mx.fei.ca.domain.Collaborator;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author inigu
 */
public class Collaboratortest {
    @Test
    public void testSaveCollaborator() throws BusinessConnectionException, BusinessDataException{
        CollaboratorDAO collaboratorDAO = new CollaboratorDAO();
        Collaborator collaborator = new Collaborator(4, "Armando Esteban Kito", "Student");
        int saveResult = collaboratorDAO.saveCollaborator(collaborator);
        assertEquals("Test save Collaborator", saveResult, 1);
    }
    
    @Test
    public void testDeleteCollaboratorById() throws BusinessConnectionException, BusinessDataException{
        CollaboratorDAO collaboratorDAO = new CollaboratorDAO();
        int deleteResult = collaboratorDAO.deleteCollaboratorById("1");
        assertEquals("Test delete LGAC", deleteResult, 1);
    }
    
}
