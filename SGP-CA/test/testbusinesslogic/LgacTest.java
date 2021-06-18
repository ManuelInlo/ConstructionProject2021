
package testbusinesslogic;

import mx.fei.ca.businesslogic.LgacDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Clase para representar las pruebas unitarias de los métodos de la clase LgacDAO
 * @author Gloria Mendoza González
 * @version 17-06-2021
 */

public class LgacTest {
    
    /**
     * Constructor vacío de la clase
     */
    
    public LgacTest(){
        
    }
    
    /**
     * Test de relación entre el integrante del CA y el LGAC al que pertenece
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testApplyLgacByIntegrant() throws BusinessConnectionException{
        LgacDAO lgacDAO = new LgacDAO();       
        boolean saveResult = lgacDAO.applyLgacByIntegrant("JCPA940514RDTREOP1", "L1");
        assertEquals("Prueba correcta, si guardó", saveResult, true);
    }
    
    /**
     * Test de eliminación de relación entre el integrante del CA y su LGAC 
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testDeletedLgacOfIntegrant() throws BusinessConnectionException{
        LgacDAO lgacDAO = new LgacDAO();       
        boolean saveResult = lgacDAO.deletedLgacOfIntegrant("JCPA940514RDTREOP1", "L1");
        assertEquals("Prueba correcta, si elimino", saveResult, true);
    }
   
}
