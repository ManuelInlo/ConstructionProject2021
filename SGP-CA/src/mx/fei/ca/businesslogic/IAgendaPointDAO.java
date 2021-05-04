
package mx.fei.ca.businesslogic;

import java.sql.Time;
import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.AgendaPoint;

public interface IAgendaPointDAO {
    public boolean savedAgendaPoint(AgendaPoint agendaPoint, int idMeeting) throws BusinessConnectionException;
    public boolean updatedAgendaPoint(AgendaPoint agendaPoint, int idAgendaPoint, int idMeeting) throws BusinessConnectionException;
    public boolean deletedAgendaPointById(int idAgendaPoint) throws BusinessConnectionException;
    public ArrayList<AgendaPoint> findAgendaPointsByIdMeeting(int idMeeting) throws BusinessConnectionException;
    public boolean existsAgendaPointTopic(String topic, int idMeeting) throws BusinessConnectionException;
    public boolean existsAvailableHoursForAgendaPoint(Time startTime, Time endTime, int idMeeting) throws BusinessConnectionException;
    public boolean existsAgendaPointTopicForUpdate(String topic, int idAgendaPoint, int idMeeting) throws BusinessConnectionException;
    public boolean existsAvailableHoursForAgendaPointForUpdate(AgendaPoint agendaPoint, int idAgendaPoint, int idMeeting) throws BusinessConnectionException;
}
