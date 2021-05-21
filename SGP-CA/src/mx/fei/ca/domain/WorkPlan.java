package mx.fei.ca.domain;

import java.sql.Date;

public class WorkPlan {
    private int keyCodePlan;
    private String curp;
    private Date startDate;
    private Date endDate;

    public WorkPlan() {
        //Default constructor
    }

    public WorkPlan(int keyCodePlan, String curp, Date startDate, Date endDate) {
        this.keyCodePlan = keyCodePlan;
        this.curp = curp;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getKeyCodePlan() {
        return keyCodePlan;
    }

    public void setKeyCodePlan(int keyCodePlan) {
        this.keyCodePlan = keyCodePlan;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "WorkPlan{" + "keyCodePlan=" + keyCodePlan + ", curp=" + curp + ", startDate=" + startDate + ", endDate=" + endDate + '}';
    }
    
}
