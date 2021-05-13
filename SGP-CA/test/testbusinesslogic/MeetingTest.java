
package testbusinesslogic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.fei.ca.businesslogic.MeetingDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Meeting;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class MeetingTest {
    public MeetingTest(){
        
    }
    
    @Test
    public void testInsertMeeting() throws BusinessConnectionException{
        MeetingDAO meetingDAO = new MeetingDAO();
        String date = "18-05-2021";
        String time = "10:30";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        java.sql.Date meetingDate = null;
        try {
            meetingDate = new java.sql.Date(simpleDateFormat.parse(date).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(MeetingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("hh:mm");
        java.sql.Time meetingTime = null;
        try {
            meetingTime = new java.sql.Time(simpleDateFormat2.parse(time).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(MeetingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Meeting meeting = new Meeting(meetingDate, meetingTime, "FEI", "Revisar pendientes del plan", "Reunión CA", "Registrada");
        boolean saveResult = meetingDAO.savedMeeting(meeting, "JCPA940514RDTREOP1");
        assertTrue("Prueba insertar nueva reunión", saveResult);
    }
    
    @Test
    public void testFindMeetingsByProjectName() throws BusinessConnectionException{
        MeetingDAO meetingDAO = new MeetingDAO();
        ArrayList<Meeting> meetings = meetingDAO.findMeetingsByProjectName("Reunión Anteproyecto");
        assertEquals("Prueba encontrar reuniones por nombre proyecto",meetings.size(), 1);
    }
    
    @Test
    public void testFindMeetingsByProjectNameAndDate() throws BusinessConnectionException{
        String date = "15-04-2021";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        java.sql.Date meetingDate = null;
        try {
            meetingDate = new java.sql.Date(simpleDateFormat.parse(date).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(MeetingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        MeetingDAO meetingDAO = new MeetingDAO();
        ArrayList<Meeting> meetings = meetingDAO.findMeetingsByProjectNameAndDate("Reunión Anteproyecto", meetingDate);
        assertEquals("Prueba encontrar reuniones por nombre proyecto y fecha",meetings.size(), 1);
    }
    
    @Test
    public void testUpdateMeeting() throws BusinessConnectionException{
        MeetingDAO meetingDAO = new MeetingDAO();
        String date = "12-04-2021";
        String time = "13:30";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        java.sql.Date meetingDate = null;
        try {
            meetingDate = new java.sql.Date(simpleDateFormat.parse(date).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(MeetingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("hh:mm");
        java.sql.Time meetingTime = null;
        try {
            meetingTime = new java.sql.Time(simpleDateFormat2.parse(time).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(MeetingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Meeting meeting = new Meeting(meetingDate, meetingTime, "FEI", "Revisar anteproyecto", "Reunión Anteproyecto", "Registrada");
        boolean updateResult = meetingDAO.updatedMeeting(meeting, 5);
        assertTrue("Prueba modificar reunión", updateResult);
    }
    
    @Test
    public void testGetCurpOfResponsibleMeeting() throws BusinessConnectionException{
        MeetingDAO meetingDAO = new MeetingDAO();
        String curpExpected = "JCPA940514RDTREOP1";
        String resultingCurp = meetingDAO.getCurpOfResponsibleMeeting(5);
        assertEquals("Prueba retornar curp del responsable de la reunión", curpExpected, resultingCurp);
    }
    
    @Test 
    public void testFindLastFiveMeetings() throws BusinessConnectionException{
        MeetingDAO meetingDAO = new MeetingDAO();
        ArrayList<Meeting> meetings = meetingDAO.findLastFiveMeetings();
        assertEquals("Prueba encontrar últimas 5 reuniones", meetings.size(), 5);
    }
    
    @Test
    public void testExistsMeetingAffair() throws BusinessConnectionException{
        MeetingDAO meetingDAO = new MeetingDAO();
        boolean exists = meetingDAO.existsMeetingAffair("Revisar Anteproyecto");
        assertTrue("Prueba mandar un asunto que si existe", exists);
    }
    
    @Test 
    public void testExistsDateAndTimeAvailable() throws BusinessConnectionException{
        MeetingDAO meetingDAO = new MeetingDAO();
        String date = "12-04-2021";
        String time = "13:30";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        java.sql.Date meetingDate = null;
        try {
            meetingDate = new java.sql.Date(simpleDateFormat.parse(date).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(MeetingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("hh:mm");
        java.sql.Time meetingTime = null;
        try {
            meetingTime = new java.sql.Time(simpleDateFormat2.parse(time).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(MeetingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean available = meetingDAO.existsDateAndTimeAvailable(meetingDate, meetingTime);
        assertFalse("Prueba mandar fecha y hora ya registradas", available);
    }
    
    @Test
    public void testExistsMeetingAffairForUpdate() throws BusinessConnectionException{
        MeetingDAO meetingDAO = new MeetingDAO();
        boolean exists = meetingDAO.existsMeetingAffairForUpdate("Revisar tesis de José", 5);
        assertTrue("Prueba mandar un asunto modificado que ya existe", exists);
    }
    
    @Test 
    public void testExistsDateAndTimeAvailableForUpdate() throws BusinessConnectionException{
        MeetingDAO meetingDAO = new MeetingDAO();
        String date = "18-04-2021";
        String time = "13:30";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        java.sql.Date meetingDate = null;
        try {
            meetingDate = new java.sql.Date(simpleDateFormat.parse(date).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(MeetingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("hh:mm");
        java.sql.Time meetingTime = null;
        try {
            meetingTime = new java.sql.Time(simpleDateFormat2.parse(time).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(MeetingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean available = meetingDAO.existsDateAndTimeAvailableForUpdate(meetingDate, meetingTime, 5);
        assertFalse("Prueba mandar fecha y hora modificadas que ya están registradas", available);
    }
}
