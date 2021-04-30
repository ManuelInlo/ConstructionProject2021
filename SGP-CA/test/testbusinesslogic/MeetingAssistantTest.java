
package testbusinesslogic;

import java.util.ArrayList;
import mx.fei.ca.businesslogic.MeetingAssistantDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Integrant;
import mx.fei.ca.domain.MeetingAssistant;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class MeetingAssistantTest {
    public MeetingAssistantTest(){
        
    }
    
    @Test
    public void testInsertMeetingAssistant() throws BusinessConnectionException{
        MeetingAssistantDAO meetingAssistantDAO = new MeetingAssistantDAO();
        Integrant integrant = new Integrant("JCPA940514RDTREOP1");
        MeetingAssistant meetingAssistant = new MeetingAssistant(integrant, "Líder");
        int saveResult = meetingAssistantDAO.saveMeetingAssistant(meetingAssistant, 5);
        assertEquals("Prueba insertar asistente de reunión", saveResult, 1);
    }
    
    @Test
    public void testUpdateRoleOfMeetingAssistant() throws BusinessConnectionException{
        MeetingAssistantDAO meetingAssistantDAO = new MeetingAssistantDAO();
        Integrant integrant = new Integrant("JCPA940514RDTREOP1");
        MeetingAssistant meetingAssistant = new MeetingAssistant(integrant, "Secretario");
        int updateResult = meetingAssistantDAO.updateRoleOfMeetingAssistant(meetingAssistant, 5);
        assertEquals("Prueba modificar rol de asistente de reunión", updateResult, 1);
    }
    
    @Test
    public void testFindMeetingAssistantsByIdMeeting() throws BusinessConnectionException{
        MeetingAssistantDAO meetingAssistantDAO = new MeetingAssistantDAO();
        ArrayList<MeetingAssistant> meetingAssistants = meetingAssistantDAO.findMeetingAssistantsByIdMeeting(5);
        assertEquals("Prueba encontrar asistentes de una reunión", meetingAssistants.size(), 1);
    }
    
    @Test
    public void testValidateExistenceOfMeetingAssistantRole() throws BusinessConnectionException{
        MeetingAssistantDAO meetingAssistantDAO = new MeetingAssistantDAO();
        boolean exists = meetingAssistantDAO.validateExistenceOfMeetingAssistantRole("Secretario", 5);
        assertTrue("Prueba mandar un rol que ya existe de un asistente de reunión", exists);
    }
}
