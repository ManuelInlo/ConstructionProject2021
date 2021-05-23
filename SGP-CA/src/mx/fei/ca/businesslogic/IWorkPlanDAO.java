package mx.fei.ca.businesslogic;

import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.WorkPlan;

public interface IWorkPlanDAO {
    
    public boolean savedWorkPlan(WorkPlan workPlan, String curp) throws BusinessConnectionException;
    public boolean updatedWorkPlan(WorkPlan workPlan, int keyCodePlan) throws BusinessConnectionException;
    /*
    public boolean savedPreliminaryProject(PreliminaryProject preliminaryproject, int idProject, int idCollaborator) throws BusinessConnectionException, BusinessDataException;
    public boolean updatePreliminaryProject(PreliminaryProject preliminaryproject, int idProject, int idCollaborator)throws BusinessConnectionException, BusinessDataException;
    public PreliminaryProject findpreliminaryProjectById(int id) throws BusinessConnectionException;
    */
}
