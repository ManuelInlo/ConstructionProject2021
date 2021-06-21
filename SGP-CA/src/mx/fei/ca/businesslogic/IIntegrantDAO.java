
package mx.fei.ca.businesslogic;

import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Integrant;

/**
 * Interface del objeto de acceso a datos de integrante
 * @author Gloria Mendoza González
 * @version 16-06-2021
 */

public interface IIntegrantDAO {
    public boolean savedIntegrant(Integrant integrant) throws BusinessConnectionException;
    public boolean updatedIntegrant(Integrant integrant, String curp) throws BusinessConnectionException; 
    public Integrant findIntegrantByCurp(String curp) throws BusinessConnectionException;
    public Integrant findIntegrantByName(String name) throws BusinessConnectionException;
    public ArrayList<Integrant> findAllIntegrants() throws BusinessConnectionException; 
    public boolean existsIntegrantName(String name) throws BusinessConnectionException;
    public boolean deleteIntegrantByCurp(String curp) throws BusinessConnectionException;   
    public boolean changedPasswordIntegrant(String password, String curp) throws BusinessConnectionException;
    public Integrant getIntegrantByInstitutionalMail(String institutionalMail) throws BusinessConnectionException;
    public String encryptPassword(String password);
    public String decryptPassword(String encryptedPassword);
    public boolean existsIntegrantCurp(String curp) throws BusinessConnectionException;
    public boolean existsIntegrantEmail(String institutionalMail) throws BusinessConnectionException;
    public ArrayList<Integrant> findIntegrantsByInitialesOfTitle(String InitialesNameIntegrant) throws BusinessConnectionException; 
    public boolean existsIntegrantResponsable() throws BusinessConnectionException;
    public Integrant findIntegrantResponsable() throws BusinessConnectionException;   
    public boolean existsIntegrantEmailForUpdate(String institutionalMail, String curp) throws BusinessConnectionException;
    public boolean existsIntegrantNameForUpdate(String name, String curp) throws BusinessConnectionException;
}
