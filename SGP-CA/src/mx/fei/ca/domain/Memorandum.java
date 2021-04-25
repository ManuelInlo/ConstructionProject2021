
package mx.fei.ca.domain;

import java.util.ArrayList;


public class Memorandum {
    private int idMemorandum;
    private String pending;
    private String note;
    private ArrayList<Agreement> agreements;
    private ArrayList<MemorandumApprover> approvers;

    public Memorandum(String pending, String note) {
        this.pending = pending;
        this.note = note;
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

    public ArrayList<Agreement> getAgreement() {
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

    public void setAgreement(ArrayList<Agreement> agreements) {
        this.agreements = agreements;
    }

    public void setApprovers(ArrayList<MemorandumApprover> approvers) {
        this.approvers = approvers;
    }
    
}