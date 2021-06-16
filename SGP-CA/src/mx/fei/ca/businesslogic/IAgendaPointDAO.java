
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
    public int getIdAgendaPointByTopic(String topic) throws BusinessConnectionException;
}
