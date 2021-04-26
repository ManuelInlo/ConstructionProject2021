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
        Collaborator collaborator = new Collaborator(1, "Jose Manuel", "Student");
        int saveResult = collaboratorDAO.saveCollaborator(collaborator);
        assertEquals("Test save Collaborator", saveResult, 1);
    }
    
}
