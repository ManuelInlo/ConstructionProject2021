
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
            startTime = new java.sql.Time(simpleDateFormatTime.parse("13:30").getTime());
        } catch (ParseException ex) {
            Logger.getLogger(AgendaPointTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            endTime = new java.sql.Time(simpleDateFormatTime.parse("13:45").getTime());
        }catch (ParseException ex){
            Logger.getLogger(AgendaPointTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        AgendaPoint agendaPoint = new AgendaPoint(startTime, endTime, 1, "Introducción reunión", "María Karen Cortés Verdín");
        int saveResult = agendaPointDAO.saveAgendaPoint(agendaPoint, 5);
        assertEquals("Prueba correcta, si guardó", saveResult, 1);
    }
    
    @Test
    public void testUpdateAgendaPoint() throws BusinessConnectionException{
        AgendaPointDAO agendaPointDAO = new AgendaPointDAO();
        SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("hh:mm");
        java.sql.Time startTime = null;
        java.sql.Time endTime = null;
        try {
            startTime = new java.sql.Time(simpleDateFormatTime.parse("16:30").getTime());
        } catch (ParseException ex) {
            Logger.getLogger(AgendaPointTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            endTime = new java.sql.Time(simpleDateFormatTime.parse("16:45").getTime());
        }catch (ParseException ex){
            Logger.getLogger(AgendaPointTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        AgendaPoint agendaPoint = new AgendaPoint(startTime, endTime, 1, "Introducción a reunión", "María Karen Cortés Verdín");
        int updateResult = agendaPointDAO.updateAgendaPoint(agendaPoint, 1, 2);
        assertEquals("Prueba correcta, si modificó", updateResult, 1);
    }
    
    @Test
    public void testDeleteAgendaPointById() throws BusinessConnectionException{
        AgendaPointDAO agendaPointDAO = new AgendaPointDAO();
        int deleteResult = agendaPointDAO.deleteAgendaPointById(1);
        assertEquals("Prueba correcta, si eliminó", deleteResult, 1);
    }
    
    @Test 
    public void testFindAgendaPointsByIdMeeting() throws BusinessConnectionException{
        AgendaPointDAO agendaPointDAO = new AgendaPointDAO();
        ArrayList<AgendaPoint> agendaPoints = agendaPointDAO.findAgendaPointsByIdMeeting(2);
        assertEquals("Prueba correcta", agendaPoints.size(), 1);
    }
    
    @Test 
    public void testValidateExistenceOfAgendaPointTopic() throws BusinessConnectionException{
        AgendaPointDAO agendaPointDAO = new AgendaPointDAO();
        boolean exists = agendaPointDAO.existsAgendaPointTopic("Introducción reunión", 5);
        assertTrue("Prueba mandar a validar un tema que ya existe en un punto de agenda", exists);
    }
    
    @Test
    public void testValidateAvailableHoursForAgendaPoint() throws BusinessConnectionException{
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
}
