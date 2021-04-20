
package mx.fei.ca.domain;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;


public class Meeting {
    private int idMeeting;
    private Date meetingDate;
    private Time meetingTime;
    private String meetingPlace;
    private String affair;
    private String projectName;
    private ArrayList<AgendaPoint> agendaPoints; 
    private Memorandum memorandum;
    private ArrayList<Prerequisite> prerequisites;
    private ArrayList<MeetingAssistant> assistants;
    

    public Meeting(Date meetingDate, Time meetingTime, String meetingPlace, String affair, String projectName) {
        this.meetingDate = meetingDate;
        this.meetingTime = meetingTime;
        this.meetingPlace = meetingPlace;
        this.affair = affair;
        this.projectName = projectName;
    }

    public int getIdMeeting() {
        return idMeeting;
    }

    public Date getMeetingDate() {
        return meetingDate;
    }

    public Time getMeetingTime() {
        return meetingTime;
    }

    public String getMeetingPlace() {
        return meetingPlace;
    }

    public String getAffair() {
        return affair;
    }

    public String getProjectName() {
        return projectName;
    }

    public ArrayList<AgendaPoint> getAgendaPoints() {
        return agendaPoints;
    }
    
    public Memorandum getMemorandum() {
        return memorandum;
    }

    public ArrayList<Prerequisite> getPrerequisites() {
        return prerequisites;
    }

    public ArrayList<MeetingAssistant> getAssistants() {
        return assistants;
    }
    
    public void setIdMeeting(int idMeeting) {
        this.idMeeting = idMeeting;
    }

    public void setMeetingDate(Date meetingDate) {
        this.meetingDate = meetingDate;
    }

    public void setMeetingTime(Time meetingTime) {
        this.meetingTime = meetingTime;
    }

    public void setMeetingPlace(String meetingPlace) {
        this.meetingPlace = meetingPlace;
    }

    public void setAffair(String affair) {
        this.affair = affair;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    } 

    public void setAgendaPoints(ArrayList<AgendaPoint> agendaPoints) {
        this.agendaPoints = agendaPoints;
    }
    
    public void setMemorandum(Memorandum memorandum) {
        this.memorandum = memorandum;
    }

    public void setPrerequisites(ArrayList<Prerequisite> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public void setAssistants(ArrayList<MeetingAssistant> assistants) {
        this.assistants = assistants;
    }
    
}
