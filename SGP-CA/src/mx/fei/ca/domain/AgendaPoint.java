
package mx.fei.ca.domain;

import java.sql.Time;

public class AgendaPoint {
    private int idAgendaPoint;
    private Time startTime;
    private Time endTime;
    private int number;
    private String topic;
    private String leader;

    public AgendaPoint(Time startTime, Time endTime, int number, String topic, String leader) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.number = number;
        this.topic = topic;
        this.leader = leader;
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

    public int getNumber() {
        return number;
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

    public void setNumber(int number) {
        this.number = number;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }
    
    
}
