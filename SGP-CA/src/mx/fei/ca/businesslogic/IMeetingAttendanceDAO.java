
package mx.fei.ca.businesslogic;

import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.MeetingAttendance;


public interface IMeetingAttendanceDAO {
    public int saveMeetingAttendance(MeetingAttendance meetingAtendance,int idMeeting, String curp) throws BusinessConnectionException;
    
}
