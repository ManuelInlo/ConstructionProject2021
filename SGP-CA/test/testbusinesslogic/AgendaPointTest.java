
package testbusinesslogic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.fei.ca.businesslogic.AgendaPointDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.AgendaPoint;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Clase para representar los test de la clase AgendaPointDAO
 * @author David Alexander Mijangos Paredes
 * @version 17-06-2021
 */

public class AgendaPointTest {
    
    /**
     * Constructor para la creación de un nuevo AgendaPointTest 
     */
    
    public AgendaPointTest(){
        
    }
    
    /**
     * Método que realiza el test para la inserción de nuevo punto de agenda en la base de datos
     * @throws BusinessConnectionException 
     */
    
    @Test 
    public void testInsertAgendaPoint() throws BusinessConnectionException{
        AgendaPointDAO agendaPointDAO = new AgendaPointDAO();
        SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("hh:mm");
        java.sql.Time startTime = null;
        java.sql.Time endTime = null;
        try {
            startTime = new java.sql.Time(simpleDateFormatTime.parse("13:45").getTime());
        } catch (ParseException ex) {
            Logger.getLogger(AgendaPointTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            endTime = new java.sql.Time(simpleDateFormatTime.parse("14:00").getTime());
        }catch (ParseException ex){
            Logger.getLogger(AgendaPointTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        AgendaPoint agendaPoint = new AgendaPoint(startTime, endTime, "Pase de lista", "María Karen Cortés Verdín");
        boolean saveResult = agendaPointDAO.savedAgendaPoint(agendaPoint, 5);
        assertTrue("Prueba guardar punto de agenda", saveResult);
    }
    
    /**
     * Método que realiza test para la modificación de un punto de agenda en la base de datos
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testUpdateAgendaPoint() throws BusinessConnectionException{
        AgendaPointDAO agendaPointDAO = new AgendaPointDAO();
        SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("hh:mm");
        java.sql.Time startTime = null;
        java.sql.Time endTime = null;
        try {
            startTime = new java.sql.Time(simpleDateFormatTime.parse("13:45").getTime());
        } catch (ParseException ex) {
            Logger.getLogger(AgendaPointTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            endTime = new java.sql.Time(simpleDateFormatTime.parse("13:50").getTime());
        }catch (ParseException ex){
            Logger.getLogger(AgendaPointTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        AgendaPoint agendaPoint = new AgendaPoint(startTime, endTime, "Pase de lista", "María Karen Cortés Verdín");
        boolean updateResult = agendaPointDAO.updatedAgendaPoint(agendaPoint, 3, 5);
        assertTrue("Prueba modificar punto agenda", updateResult);
    }
    
    /**
     * Método que realiza test para la eliminación de un punto de agenda específico de la base de datos
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testDeleteAgendaPointById() throws BusinessConnectionException{
        AgendaPointDAO agendaPointDAO = new AgendaPointDAO();
        boolean deleteResult = agendaPointDAO.deletedAgendaPointById(3);
        assertTrue("Prueba eliminar punto agenda", deleteResult);
    }
    
    /**
     * Método que realiza test para la obtención de los puntos de agenda de una reunión específica
     * @throws BusinessConnectionException 
     */
    
    @Test 
    public void testFindAgendaPointsByIdMeeting() throws BusinessConnectionException{
        AgendaPointDAO agendaPointDAO = new AgendaPointDAO();
        ArrayList<AgendaPoint> agendaPoints = agendaPointDAO.findAgendaPointsByIdMeeting(5);
        assertEquals("Prueba correcta", agendaPoints.size(), 1);
    }
    
    /**
     * Método que realiza test para la obtención del identificador de un punto de agenda de acuerdo a su tema e identificador de la reunión
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testGetIdAgendaPointByTopic() throws BusinessConnectionException{
        AgendaPointDAO agendaPointDAO = new AgendaPointDAO();
        int idAgendaPointExpected = 19;
        int idAgendaPointResult = agendaPointDAO.getIdAgendaPointByTopic("lista", 24);
        assertEquals("Prueba recuperar id de punto de agenda", idAgendaPointExpected, idAgendaPointResult);
    }
   
}
  