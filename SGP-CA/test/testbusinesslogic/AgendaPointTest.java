
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


public class AgendaPointTest {
    public AgendaPointTest(){
        
    }
    
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
    
    @Test
    public void testDeleteAgendaPointById() throws BusinessConnectionException{
        AgendaPointDAO agendaPointDAO = new AgendaPointDAO();
        boolean deleteResult = agendaPointDAO.deletedAgendaPointById(3);
        assertTrue("Prueba eliminar punto agenda", deleteResult);
    }
    
    @Test 
    public void testFindAgendaPointsByIdMeeting() throws BusinessConnectionException{
        AgendaPointDAO agendaPointDAO = new AgendaPointDAO();
        ArrayList<AgendaPoint> agendaPoints = agendaPointDAO.findAgendaPointsByIdMeeting(5);
        assertEquals("Prueba correcta", agendaPoints.size(), 1);
    }
    
    @Test 
    public void testExistsAgendaPointTopic() throws BusinessConnectionException{
        AgendaPointDAO agendaPointDAO = new AgendaPointDAO();
        boolean exists = agendaPointDAO.existsAgendaPointTopic("Introducción reunión", 5);
        assertTrue("Prueba mandar a validar un tema que ya existe en un punto de agenda", exists);
    }
    
    @Test
    public void testExistsAvailableHoursForAgendaPoint() throws BusinessConnectionException{
        AgendaPointDAO agendaPointDAO = new AgendaPointDAO();
        SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("hh:mm");
        java.sql.Time startTime = null;
        java.sql.Time endTime = null;
        try {
            startTime = new java.sql.Time(simpleDateFormatTime.parse("13:30").getTime());
        } catch (ParseException ex) {
            Logger.getLogger(AgendaPointTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            endTime = new java.sql.Time(simpleDateFormatTime.parse("13:45").getTime());
        }catch (ParseException ex){
            Logger.getLogger(AgendaPointTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean hoursAvailable = agendaPointDAO.existsAvailableHoursForAgendaPoint(startTime, endTime, 5);
        assertFalse("Prueba mandar a validar horas que ya están en un punto de agenda", hoursAvailable);
    }
    
    @Test 
    public void testExistsAgendaPointTopicForUpdate() throws BusinessConnectionException{
        AgendaPointDAO agendaPointDAO = new AgendaPointDAO();
        boolean exists = agendaPointDAO.existsAgendaPointTopicForUpdate("Introducción plan de trabajo", 2 ,5);
        assertFalse("Prueba mandar a validar un tema modificado que no existe en un punto de agenda", exists);
    }
    
    @Test
    public void testExistsAvailableHoursForAgendaPointForUpdate() throws BusinessConnectionException{
        AgendaPointDAO agendaPointDAO = new AgendaPointDAO();
        SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("hh:mm");
        java.sql.Time startTime = null;
        java.sql.Time endTime = null;
        try {
            startTime = new java.sql.Time(simpleDateFormatTime.parse("15:30").getTime());
        } catch (ParseException ex) {
            Logger.getLogger(AgendaPointTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            endTime = new java.sql.Time(simpleDateFormatTime.parse("15:45").getTime());
        }catch (ParseException ex){
            Logger.getLogger(AgendaPointTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        AgendaPoint agendaPoint = new AgendaPoint(startTime, endTime);
        boolean hoursAvailable = agendaPointDAO.existsAvailableHoursForAgendaPointForUpdate(agendaPoint, 2, 5);
        assertTrue("Prueba mandar a validar horas modificadas que no están en un punto de agenda", hoursAvailable);
    }
    
}
