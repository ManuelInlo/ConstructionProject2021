
package mx.fei.ca.businesslogic;

import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Strategy;


public interface IStrategyDAO {
    public boolean saveStrategy(Strategy strategy, int idTarget)throws BusinessConnectionException;
    public boolean updateStrategy(Strategy strategy, int idTarget, int idStrategy)throws BusinessConnectionException;

}
