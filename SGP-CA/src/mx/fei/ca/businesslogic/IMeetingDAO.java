package mx.fei.ca.businesslogic;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Meeting;

public interface IMeetingDAO {
    public boolean savedMeeting(Meeting meeting, String curp) throws BusinessConnectionException;
    public ArrayList<Meeting> findMeetingsByProjectName(String projectName) throws BusinessConnectionException;
    public ArrayList<Meeting> findMeetingsByProjectNameAndDate(String projectName, Date meetingDate) throws BusinessConnectionException;
    public ArrayList<Meeting> findLastFiveMeetings() throws BusinessConnectionException;
    public boolean updatedMeeting(Meeting meeting, int idMeeting)throws BusinessConnectionException;
    public String getCurpOfResponsibleMeeting(int idMeeting) throws BusinessConnectionException;
    public boolean existsMeetingAffair(String meetingAffair) throws BusinessConnectionException;
    public boolean existsDateAndTimeAvailable(Date meetingDate, Time meetingTime) throws BusinessConnectionException;
    public boolean existsMeetingAffairForUpdate(String meetingAffair, int idMeeting) throws BusinessConnectionException;
    public boolean existsDateAndTimeAvailableForUpdate(Date meetingDate, Time meetingTime, int idMeeting) throws BusinessConnectionException;
}
