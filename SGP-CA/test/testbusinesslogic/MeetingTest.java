
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
import org.junit.Test;

public class MeetingTest {
    public MeetingTest(){
        
    }
    
    @Test
    public void testInsertMeeting() throws BusinessConnectionException{
        MeetingDAO meetingDAO = new MeetingDAO();
        String date = "15-04-2021";
        String time = "12:30";
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
        Meeting meeting = new Meeting(meetingDate, meetingTime, "FEI", "Revisar anteproyecto", "Reunión Anteproyecto");
        int saveResult = meetingDAO.saveMeeting(meeting, "JCPA940514RDTREOP1");
        assertEquals("Prueba insertar nueva reunión", saveResult, 1);
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
        Meeting meeting = new Meeting(meetingDate, meetingTime, "FEI", "Revisar anteproyecto", "Reunión Anteproyecto");
        int updateResult = meetingDAO.updateMeeting(meeting, 5);
        assertEquals("Prueba modificar reunión", updateResult, 1);
    }
    
    @Test
    public void testGetCurpOfResponsibleMeeting() throws BusinessConnectionException{
        MeetingDAO meetingDAO = new MeetingDAO();
        String curpExpected = "JCPA940514RDTREOP1";
        String resultingCurp = meetingDAO.getCurpOfResponsibleMeeting(5);
        assertEquals("Prueba retornar curp del responsable de la reunión", curpExpected, resultingCurp);
    }
}
