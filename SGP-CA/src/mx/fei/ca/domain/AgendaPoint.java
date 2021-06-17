package mx.fei.ca.domain;

import java.sql.Time;

/**
 * Clase para representar a un punto de agenda de una reunión del CA
 * @author David Alexander Mijangos Paredes
 * @version 16-06-2021
 */

public class AgendaPoint {
    private int idAgendaPoint;
    private Time startTime;
    private Time endTime;
    private String topic;
    private String leader;

    /**
     * Constructor para la creación de un punto de agenda de una reunión
     *
     * @param startTime Define la hora de inicio del punto de agenda
     * @param endTime Define la hora de fin del punto de agenda
     * @param topic Define el tema a tratar del punto de agenda
     * @param leader Define el líder de discusión del punto de agenda
     */
    
    public AgendaPoint(Time startTime, Time endTime, String topic, String leader) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.topic = topic;
        this.leader = leader;
    }
    
    /**
     * 
     * @return El identificador del punto de agenda
     */

    public int getIdAgendaPoint() {
        return idAgendaPoint;
    }
    
    /**
     * 
     * @return La hora de inicio del punto de agenda
     */

    public Time getStartTime() {
        return startTime;
    }
    
    /**
     * 
     * @return La hora de fin del punto de agenda
     */

    public Time getEndTime() {
        return endTime;
    }
    
    /**
     * 
     * @return El tema del punto de agenda
     */

    public String getTopic() {
        return topic;
    }
    
    /**
     * 
     * @return El líder de discusión del punto de agenda
     */

    public String getLeader() {
        return leader;
    }
    
    /**
     * 
     * @param idAgendaPoint Identificador a establecer al punto de agenda
     */

    public void setIdAgendaPoint(int idAgendaPoint) {
        this.idAgendaPoint = idAgendaPoint;
    }

    /**
     * 
     * @param startTime Hora de inicio a establecer al punto de agenda
     */
    
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }
    
    /**
     * 
     * @param endTime Hora de fin a establecer al punto de agenda
     */

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
    
    /**
     * 
     * @param topic Tema a establecer al punto de agenda
     */
    
    public void setTopic(String topic) {
        this.topic = topic;
    }
    
    /**
     * 
     * @param leader Líder de discusión a establecer al punto de agenda
     */

    public void setLeader(String leader) {
        this.leader = leader;
    }
      
}
