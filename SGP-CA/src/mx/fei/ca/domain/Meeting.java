package mx.fei.ca.domain;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

/**
 * Clase para representar una reunión del CA
 * Cada reunión se determina de un identificador, una fecha, hora, lugar, asunto, nombre de proyecto, estado y sus respectivos puntos de agenda,
 * prerequisitos y asistentes. Además se determina de una minuta
 * @author David Alexander Mijangos Paredes
 * @version 16-06-2021
 */

public class Meeting {
    private int idMeeting;
    private Date meetingDate;
    private Time meetingTime;
    private String meetingPlace;
    private String affair;
    private String projectName;
    private String state;
    private ArrayList<AgendaPoint> agendaPoints; 
    private Memorandum memorandum;
    private ArrayList<Prerequisite> prerequisites;
    private ArrayList<MeetingAssistant> assistants;
    
    /**
     * Constructor para la creación de una reunión del CA
     * @param meetingDate Define la fecha de la reunión
     * @param meetingTime Define la hora de la reunión
     * @param meetingPlace Define el lugar de la reunión
     * @param affair Define el asunto de la reunión
     * @param projectName Define el nombre de proyecto de la reunión
     * @param state Define el estado de la reunión
     */
    
    public Meeting(Date meetingDate, Time meetingTime, String meetingPlace, String affair, String projectName, String state) {
        this.meetingDate = meetingDate;
        this.meetingTime = meetingTime;
        this.meetingPlace = meetingPlace;
        this.affair = affair;
        this.projectName = projectName;
        this.state = state;
    }
    
    /**
     * 
     * @return El identificador de la reunión 
     */

    public int getIdMeeting() {
        return idMeeting;
    }
    
    /**
     * 
     * @return La fecha de la reunión
     */

    public Date getMeetingDate() {
        return meetingDate;
    }
    
    /**
     * 
     * @return La hora de la reunión
     */
    
    public Time getMeetingTime() {
        return meetingTime;
    }
    
    /**
     * 
     * @return El lugar de la reunión
     */

    public String getMeetingPlace() {
        return meetingPlace;
    }
    
    /**
     * 
     * @return El asunto de la reunión
     */
    
    public String getAffair() {
        return affair;
    }
    
    /**
     * 
     * @return El nombre de proyecto de la reunión
     */
    
    public String getProjectName() {
        return projectName;
    }
    
    /**
     * 
     * @return El estado de la reunión
     */

    public String getState() {
        return state;
    }
    
    /**
     * 
     * @return Los puntos de agenda de la reunión
     */
    
    public ArrayList<AgendaPoint> getAgendaPoints() {
        return agendaPoints;
    }
    
    /**
     * 
     * @return La minuta de la reunión
     */
    
    public Memorandum getMemorandum() {
        return memorandum;
    }
    
    /**
     * 
     * @return Los prerequisitos de la reunión
     */

    public ArrayList<Prerequisite> getPrerequisites() {
        return prerequisites;
    }
    
    /**
     * 
     * @return Los asistentes de la reunión
     */

    public ArrayList<MeetingAssistant> getAssistants() {
        return assistants;
    }
    
    /**
     * 
     * @param idMeeting El identificador a establecer a la reunión
     */
    
    public void setIdMeeting(int idMeeting) {
        this.idMeeting = idMeeting;
    }
    
    /**
     * 
     * @param meetingDate La fehca a establecer a la reunión
     */

    public void setMeetingDate(Date meetingDate) {
        this.meetingDate = meetingDate;
    }
    
    /**
     * 
     * @param meetingTime La hora a establecer a la reunión
     */

    public void setMeetingTime(Time meetingTime) {
        this.meetingTime = meetingTime;
    }
    
    /**
     * 
     * @param meetingPlace El lugar a establecer a la reunión
     */

    public void setMeetingPlace(String meetingPlace) {
        this.meetingPlace = meetingPlace;
    }
    
    /**
     * 
     * @param affair El asunto a establecer a la reunión
     */

    public void setAffair(String affair) {
        this.affair = affair;
    }
    
    /**
     * 
     * @param projectName El nombre del proyecto a establecer a la reunión
     */

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    } 
    
    /**
     * 
     * @param state El estado a establecer a la reunión
     */

    public void setState(String state) {
        this.state = state;
    }
    
    /**
     * 
     * @param agendaPoints Los puntos de agenda a establecer a la reunión
     */

    public void setAgendaPoints(ArrayList<AgendaPoint> agendaPoints) {
        this.agendaPoints = agendaPoints;
    }
    
    /**
     * 
     * @param memorandum La minuta a establecer a la reunión
     */
    
    public void setMemorandum(Memorandum memorandum) {
        this.memorandum = memorandum;
    }
    
    /**
     * 
     * @param prerequisites Los prerequisitos a establecer a la reunión
     */

    public void setPrerequisites(ArrayList<Prerequisite> prerequisites) {
        this.prerequisites = prerequisites;
    }
    
    /**
     * 
     * @param assistants Los asistentes a establecer a la reunión
     */

    public void setAssistants(ArrayList<MeetingAssistant> assistants) {
        this.assistants = assistants;
    }
    
}
