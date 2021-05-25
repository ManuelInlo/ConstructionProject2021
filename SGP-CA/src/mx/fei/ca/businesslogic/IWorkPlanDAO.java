package mx.fei.ca.businesslogic;

import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.WorkPlan;

public interface IWorkPlanDAO {
    
    public boolean savedWorkPlan(WorkPlan workPlan, String curp) throws BusinessConnectionException;
    public boolean updatedWorkPlan(WorkPlan workPlan, String curp, int keyCodePlan) throws BusinessConnectionException;
    public ArrayList<WorkPlan> findAllWorkPlans() throws BusinessConnectionException;
}
