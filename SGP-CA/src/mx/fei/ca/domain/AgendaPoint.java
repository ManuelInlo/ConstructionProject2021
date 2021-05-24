
package mx.fei.ca.domain;

import java.sql.Time;

public class AgendaPoint {
    private int idAgendaPoint;
    private Time startTime;
    private Time endTime;
    private String topic;
    private String leader;

    public AgendaPoint(Time startTime, Time endTime, String topic, String leader) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.topic = topic;
        this.leader = leader;
    }

    public AgendaPoint(Time startTime, Time endTime) {
       this.startTime = startTime;
       this.endTime = endTime;
    }

    public int getIdAgendaPoint() {
        return idAgendaPoint;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public String getTopic() {
        return topic;
    }

    public String getLeader() {
        return leader;
    }

    public void setIdAgendaPoint(int idAgendaPoint) {
        this.idAgendaPoint = idAgendaPoint;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
    
    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }
    
    
}
