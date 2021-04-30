package testbusinesslogic;

import mx.fei.ca.businesslogic.PreliminaryProjectDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.businesslogic.exceptions.BusinessDataException;
import mx.fei.ca.domain.PreliminaryProject;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author inigu
 */
public class PreliminaryProjecttest {
    @Test
    public void testSavePreliminaryProject() throws BusinessConnectionException, BusinessDataException{
        PreliminaryProjectDAO preliminaryProjectDAO = new PreliminaryProjectDAO();
        PreliminaryProject preliminaryProject = new PreliminaryProject(1, 1, 3, "Investigation about JAVA", 
        "Active", "1 semester", "Complete", "This gonna be a simply project for be better in the java's language");
        int saveResult = preliminaryProjectDAO.savePreliminaryProject(preliminaryProject);
        assertEquals("Test save preliminaryProject", saveResult, 1);
    }
}
