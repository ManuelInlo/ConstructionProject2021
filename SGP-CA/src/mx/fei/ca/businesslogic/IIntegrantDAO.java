
package mx.fei.ca.businesslogic;

import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Integrant;

public interface IIntegrantDAO {
    public boolean savedIntegrant(Integrant integrant) throws BusinessConnectionException;
    public boolean updatedIntegrant(Integrant integrant, String curp) throws BusinessConnectionException; 
    public Integrant findIntegrantByCurp(String curp) throws BusinessConnectionException;
    public boolean deleteIntegrantByCurp(String curp) throws BusinessConnectionException;
    
    public boolean changedPasswordIntegrant(String password, String curp) throws BusinessConnectionException;
    public Integrant getIntegrantByInstitutionalMail(String institutionalMail) throws BusinessConnectionException;
    public String encryptPassword(String password);
    public String decryptPassword(String encryptedPassword);
}
