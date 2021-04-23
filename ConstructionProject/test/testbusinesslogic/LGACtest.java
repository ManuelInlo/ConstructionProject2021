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
        LGAC lgac = new LGAC("L1", "Gestión, modelado, y desarrollo de software");
        int saveResult = lgacDAO.saveLGAC(lgac);
        assertEquals("Test save LGAC",saveResult ,1 );
    }
}
