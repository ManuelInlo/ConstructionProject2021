package mx.fei.ca.businesslogic.interfaces;

import java.sql.Date;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.businesslogic.exceptions.BusinessDataException;
import mx.fei.ca.domain.Integrant;

/**
 *
 * @author inigu
 */
public interface Integrantinterface {
    public int saveIntegrant(Integrant integrant) throws BusinessConnectionException, BusinessDataException;
    public int updateIntegrant(Integrant integrant, String curp, String role, 
            String nameIntegrant, String studyDegree, 
            String studyDicipline, String prodepParticipation, 
            String typeTeaching, String eisStudyDegree, String institucionalMail, 
            String numberPhone, Date dateBirthday) throws BusinessConnectionException;
    public int deleteIntegrantByCurp(String curp) throws BusinessConnectionException, BusinessDataException;
    /*
    public int saveLGAC(LGAC lgac) throws BusinessConnectionException, BusinessDataException;
   //public ArrayList<LGAC> findLGACByClave(String clabe) throws BusinessConnectionException, BusinessDataException;
    public int updateLGAC(LGAC lgac, String keyCode, String name) throws BusinessConnectionException;
    public int deleteAgreementById(String keyCode) throws BusinessConnectionException, BusinessDataException; 
    */
}
