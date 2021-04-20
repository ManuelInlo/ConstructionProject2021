
package mx.fei.ca.businesslogic;

import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.MeetingAssistant;


public interface IMeetingAttendanceDAO {
    public int saveMeetingAttendance(MeetingAssistant meetingAtendance,int idMeeting, String curp) throws BusinessConnectionException;
    
}
