package mx.fei.ca.businesslogic;

import java.sql.Date;
import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Meeting;

public interface IMeetingDAO {
    public int saveMeeting(Meeting meeting, String curp) throws BusinessConnectionException;
    public ArrayList<Meeting> findMeetingsByProjectName(String projectName) throws BusinessConnectionException;
    public ArrayList<Meeting> findMeetingsByProjectNameAndDate(String projectName, Date meetingDate) throws BusinessConnectionException;
    public int updateMeeting(Meeting meeting, int idMeeting)throws BusinessConnectionException;
    public String getCurpOfResponsibleMeeting(int idMeeting) throws BusinessConnectionException;
}
