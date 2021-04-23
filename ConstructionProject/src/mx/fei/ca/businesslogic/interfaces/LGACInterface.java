package mx.fei.ca.businesslogic.interfaces;

import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.businesslogic.exceptions.BusinessDataException;
import mx.fei.ca.domain.LGAC;

/**
 *
 * @author inigu
 */
public interface LGACInterface {
   public int saveLGAC(LGAC lgac) throws BusinessConnectionException, BusinessDataException;
   //public ArrayList<LGAC> findLGACByClave(String clabe) throws BusinessConnectionException, BusinessDataException;
    public int updateLGAC(LGAC lgac, String keyCode, String name) throws BusinessConnectionException;
    public int deleteAgreementById(String keyCode) throws BusinessConnectionException, BusinessDataException;  
    
}
