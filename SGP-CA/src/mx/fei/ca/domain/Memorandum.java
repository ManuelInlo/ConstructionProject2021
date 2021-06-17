package mx.fei.ca.domain;

import java.util.ArrayList;

/**
 * Clase para representar a una minuta de reunión del CA
 * Cada minuta queda determinada de un identificador, pendientes, notas y sus respectivos acuerdos y aprobadores 
 * @author David Alexander Mijangos Paredes
 * @version 16-06-2021
 */

public class Memorandum {
    private int idMemorandum;
    private String pending;
    private String note;
    private ArrayList<Agreement> agreements;
    private ArrayList<MemorandumApprover> approvers;
    
    /**
     * Constructor para la creación de una minuta de reunión
     * @param pending Define los pendientes de la minuta
     * @param note Define las notas de la minuta
     */

    public Memorandum(String pending, String note) {
        this.pending = pending;
        this.note = note;
    }
    
    /**
     * 
     * @return El identificador de la minuta
     */

    public int getIdMemorandum() {
        return idMemorandum;
    }
    
    /**
     * 
     * @return Los pendientes de la minuta
     */

    public String getPending() {
        return pending;
    }
    
    /**
     * 
     * @return Las notas de la minuta
     */

    public String getNote() {
        return note;
    }
    
    /**
     * 
     * @return Los acuerdos de la minuta
     */

    public ArrayList<Agreement> getAgreements() {
        return agreements;
    }
    
    /**
     * 
     * @return Los aprobadores de la minuta
     */
    
    public ArrayList<MemorandumApprover> getApprovers() {
        return approvers;
    }
    
    /**
     * 
     * @param idMemorandum El identificador a establecer a la minuta
     */
    
    public void setIdMemorandum(int idMemorandum) {
        this.idMemorandum = idMemorandum;
    }
    
    /**
     * 
     * @param pending Los pendientes a establecer a la minuta
     */

    public void setPending(String pending) {
        this.pending = pending;
    }
    
    /**
     * 
     * @param note Las notas a establecer a la minuta
     */

    public void setNote(String note) {
        this.note = note;
    }
    
    /**
     * 
     * @param approvers Los aprobadores a establecer a la minuta
     */

    public void setApprovers(ArrayList<MemorandumApprover> approvers) {
        this.approvers = approvers;
    }
    
    /**
     * 
     * @param agreements Los acuerdos a establecer a la minuta
     */

    public void setAgreements(ArrayList<Agreement> agreements) {
        this.agreements = agreements;
    }
     
}