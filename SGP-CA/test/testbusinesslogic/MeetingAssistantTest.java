
package testbusinesslogic;

import java.util.ArrayList;
import mx.fei.ca.businesslogic.MeetingAssistantDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Integrant;
import mx.fei.ca.domain.MeetingAssistant;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
        boolean saveResult = meetingAssistantDAO.savedMeetingAssistant(meetingAssistant, 5);
        assertTrue("Prueba insertar asistente de reunión", saveResult);
    }
    
    @Test
    public void testUpdateRoleOfMeetingAssistant() throws BusinessConnectionException{
        MeetingAssistantDAO meetingAssistantDAO = new MeetingAssistantDAO();
        Integrant integrant = new Integrant("JCPA940514RDTREOP1");
        MeetingAssistant meetingAssistant = new MeetingAssistant(integrant, "Secretario");
        boolean updateResult = meetingAssistantDAO.updatedRoleOfMeetingAssistant(meetingAssistant, 5);
        assertTrue("Prueba modificar rol de asistente de reunión", updateResult);
    }
    
    @Test
    public void testFindMeetingAssistantsByIdMeeting() throws BusinessConnectionException{
        MeetingAssistantDAO meetingAssistantDAO = new MeetingAssistantDAO();
        ArrayList<MeetingAssistant> meetingAssistants = meetingAssistantDAO.findMeetingAssistantsByIdMeeting(5);
        assertEquals("Prueba encontrar asistentes de una reunión", meetingAssistants.size(), 1);
    }
    
    @Test
    public void testExistsMeetingAssistantRole() throws BusinessConnectionException{
        MeetingAssistantDAO meetingAssistantDAO = new MeetingAssistantDAO();
        boolean exists = meetingAssistantDAO.existsMeetingAssistantRole("Líder", 5);
        assertTrue("Prueba mandar un rol que ya existe de un asistente de reunión", exists);
    }
    
    @Test
    public void testExistsMeetingAssistantRoleForUpdate() throws BusinessConnectionException{
        MeetingAssistantDAO meetingAssistantDAO = new MeetingAssistantDAO();
        boolean exists = meetingAssistantDAO.existsMeetingAssistantRoleForUpdate("Secretario", 5, "JCPA940514RDTREOP1");
        assertFalse("Prueba mandar un rol modificado que no existe de un asistente de reunión", exists);
    }
}
