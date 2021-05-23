package mx.fei.ca.businesslogic;

import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Target;


public interface ITargetDAO {
    public boolean savedTarget(Target target, int keyCodePlan)throws BusinessConnectionException;
    public boolean updatedTarget(Target target, int keyCodePlan, int idTarget)throws BusinessConnectionException;
}
