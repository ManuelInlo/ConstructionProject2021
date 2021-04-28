package testbusinesslogic;

import mx.fei.ca.businesslogic.LGACDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.businesslogic.exceptions.BusinessDataException;
import mx.fei.ca.domain.LGAC;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author inigu
 */
public class LGACtest {
    
    @Test
    public void testSaveLGAC() throws BusinessConnectionException, BusinessDataException{
        LGACDAO lgacDAO = new LGACDAO();
        LGAC lgac = new LGAC("L4", "Dise;o WEB avanzado");
        int saveResult = lgacDAO.saveLGAC(lgac);
        assertEquals("Test save LGAC",saveResult ,1 );
    }
    
    @Test
    public void testDeleteLGAC() throws BusinessConnectionException, BusinessDataException{
        LGACDAO lgacDAO = new LGACDAO();
        int deleteResult = lgacDAO.deleteAgreementById("L5");
        assertEquals("Test delete LGAC", deleteResult, 1);
    }
}
