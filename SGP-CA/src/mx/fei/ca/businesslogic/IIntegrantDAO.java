
package mx.fei.ca.businesslogic;

import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.businesslogic.exceptions.BusinessDataException;
import mx.fei.ca.domain.Integrant;

public interface IIntegrantDAO {
    public int saveIntegrant(Integrant integrant) throws BusinessConnectionException;
    public int updateIntegrant(Integrant integrant, String curp) throws BusinessConnectionException; 
    public int deleteIntegrantByCurp(String curp) throws BusinessConnectionException;
}
