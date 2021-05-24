
package mx.fei.ca.domain;

import java.util.ArrayList;


public class Memorandum {
    private int idMemorandum;
    private String pending;
    private String note;
    private String state;
    private ArrayList<Agreement> agreements;
    private ArrayList<MemorandumApprover> approvers;

    public Memorandum(String pending, String note, String state) {
        this.pending = pending;
        this.note = note;
        this.state = state;
    }

    public int getIdMemorandum() {
        return idMemorandum;
    }

    public String getPending() {
        return pending;
    }

    public String getNote() {
        return note;
    }

    public String getState() {
        return state;
    }

    public ArrayList<Agreement> getAgreements() {
        return agreements;
    }
    
    public ArrayList<MemorandumApprover> getApprovers() {
        return approvers;
    }
    
    public void setIdMemorandum(int idMemorandum) {
        this.idMemorandum = idMemorandum;
    }

    public void setPending(String pending) {
        this.pending = pending;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setApprovers(ArrayList<MemorandumApprover> approvers) {
        this.approvers = approvers;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setAgreements(ArrayList<Agreement> agreements) {
        this.agreements = agreements;
    }
    
    
}