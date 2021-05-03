
package mx.fei.ca.businesslogic;

import java.sql.Time;
import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.AgendaPoint;

public interface IAgendaPointDAO {
    public int saveAgendaPoint(AgendaPoint agendaPoint, int idMeeting) throws BusinessConnectionException;
    public int updateAgendaPoint(AgendaPoint agendaPoint, int idAgendaPoint, int idMeeting) throws BusinessConnectionException;
    public int deleteAgendaPointById(int idAgendaPoint) throws BusinessConnectionException;
    public ArrayList<AgendaPoint> findAgendaPointsByIdMeeting(int idMeeting) throws BusinessConnectionException;
    public boolean existsAgendaPointTopic(String topic, int idMeeting) throws BusinessConnectionException;
    public boolean existsAvailableHoursForAgendaPoint(Time startTime, Time endTime, int idMeeting) throws BusinessConnectionException;
    public boolean existsAgendaPointTopicForUpdate(String topic, int idAgendaPoint, int idMeeting) throws BusinessConnectionException;
    public boolean existsAvailableHoursForAgendaPointForUpdate(AgendaPoint agendaPoint, int idAgendaPoint, int idMeeting) throws BusinessConnectionException;
}
