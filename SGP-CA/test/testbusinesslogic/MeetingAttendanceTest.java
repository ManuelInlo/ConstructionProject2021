
package testbusinesslogic;

import mx.fei.ca.businesslogic.MeetingAttendanceDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.MeetingAssistant;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class MeetingAttendanceTest {
    public MeetingAttendanceTest(){
        
    }
    
    @Test
    public void testInsertMeetingAttendance() throws BusinessConnectionException{
        MeetingAttendanceDAO meetingAttendanceDAO = new MeetingAttendanceDAO();
        MeetingAssistant meetingAttendance = new MeetingAssistant("Secretario");
        int saveResult = meetingAttendanceDAO.saveMeetingAttendance(meetingAttendance, 2, "JCPA940514RDTREOP1");
        assertEquals("Prueba insertar asistencia a reunión", saveResult, 1);
    }
    
    
}
