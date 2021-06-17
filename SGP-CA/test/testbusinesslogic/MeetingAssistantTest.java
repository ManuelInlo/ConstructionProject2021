
package testbusinesslogic;

import java.util.ArrayList;
import mx.fei.ca.businesslogic.IntegrantDAO;
import mx.fei.ca.businesslogic.MeetingAssistantDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Integrant;
import mx.fei.ca.domain.MeetingAssistant;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Clase para representar los test de la clase MeetingAssistantDAO 
 * @author David Alexander Mijangos Paredes
 * @version 17-06-2021
 */

public class MeetingAssistantTest {
    
    /**
     * Constructor para la creación de un MeetingAssistantTest
     */
    
    public MeetingAssistantTest(){
        
    }
    
    /**
     * Método que realiza test para la inserción de un nuevo asistente de reunión en la base de datos
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testInsertMeetingAssistant() throws BusinessConnectionException{
        MeetingAssistantDAO meetingAssistantDAO = new MeetingAssistantDAO();
        IntegrantDAO integrantDAO = new IntegrantDAO();
        Integrant integrant = integrantDAO.findIntegrantByCurp("JCPA940514RDTREOP1");
        MeetingAssistant meetingAssistant = new MeetingAssistant(integrant);
        meetingAssistant.setRole("Lider");
        boolean saveResult = meetingAssistantDAO.savedMeetingAssistant(meetingAssistant, 5);
        assertTrue("Prueba insertar asistente de reunión", saveResult);
    }
    
    /**
     * Método que realiza test para la modificación del rol de un asistente de reunión específico
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testUpdateRoleOfMeetingAssistant() throws BusinessConnectionException{
        MeetingAssistantDAO meetingAssistantDAO = new MeetingAssistantDAO();
        IntegrantDAO integrantDAO = new IntegrantDAO();
        Integrant integrant = integrantDAO.findIntegrantByCurp("JCPA940514RDTREOP1");
        MeetingAssistant meetingAssistant = new MeetingAssistant(integrant);
        meetingAssistant.setRole("Secretario");
        boolean updateResult = meetingAssistantDAO.updatedRoleOfMeetingAssistant(meetingAssistant, 5);
        assertTrue("Prueba modificar rol de asistente de reunión", updateResult);
    }
    
    /**
     * Método para la obtención de los asistentes de una reunión de acuerdo al identificador de la reunión
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testFindMeetingAssistantsByIdMeeting() throws BusinessConnectionException{
        MeetingAssistantDAO meetingAssistantDAO = new MeetingAssistantDAO();
        ArrayList<MeetingAssistant> meetingAssistants = meetingAssistantDAO.findMeetingAssistantsByIdMeeting(5);
        assertEquals("Prueba encontrar asistentes de una reunión", meetingAssistants.size(), 1);
    }
   
}
