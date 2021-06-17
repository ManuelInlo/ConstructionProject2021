
package mx.fei.ca.businesslogic;

import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.MeetingAssistant;

/**
 * Interface del objeto de acceso a datos de asistente de reunión
 * @author David Alexander Mijangos Paredes
 * @version 16-06-2021
 */

public interface IMeetingAssistantDAO {
    public boolean savedMeetingAssistant(MeetingAssistant meetingAssistant,int idMeeting) throws BusinessConnectionException;
    public boolean updatedRoleOfMeetingAssistant(MeetingAssistant meetingAssistant, int idMeeting) throws BusinessConnectionException;
    public ArrayList<MeetingAssistant> findMeetingAssistantsByIdMeeting(int idMeeting) throws BusinessConnectionException;
}
