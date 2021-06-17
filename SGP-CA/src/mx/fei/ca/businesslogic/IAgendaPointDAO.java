
package mx.fei.ca.businesslogic;

import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.AgendaPoint;

/**
 * Interface del Objecto de acceso a datos de punto de agenda
 * @author David Alexander Mijangos Paredes
 * @version 16-06-2021
 */

public interface IAgendaPointDAO {
    public boolean savedAgendaPoint(AgendaPoint agendaPoint, int idMeeting) throws BusinessConnectionException;
    public boolean updatedAgendaPoint(AgendaPoint agendaPoint, int idAgendaPoint, int idMeeting) throws BusinessConnectionException;
    public boolean deletedAgendaPointById(int idAgendaPoint) throws BusinessConnectionException;
    public ArrayList<AgendaPoint> findAgendaPointsByIdMeeting(int idMeeting) throws BusinessConnectionException;
    public int getIdAgendaPointByTopic(String topic, int idMeeting) throws BusinessConnectionException;
}
