
package testbusinesslogic;

import java.util.ArrayList;
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
     * Método que realiza test que guarda una nueva relación entre el integrante del CA y el LGAC al que pertenece en la base de datos
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testApplyLgacByIntegrant() throws BusinessConnectionException{
        LgacDAO lgacDAO = new LgacDAO();       
        boolean saveResult = lgacDAO.applyLgacByIntegrant("JCPA940514RDTREOP1", "L1");
        assertEquals("Prueba correcta, si guardó", saveResult, true);
    }
    
    /**
     * Método que realiza test de eliminación de relación entre el integrante del CA y su LGAC 
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testDeletedLgacOfIntegrant() throws BusinessConnectionException{
        LgacDAO lgacDAO = new LgacDAO();       
        boolean saveResult = lgacDAO.deletedLgacOfIntegrant("GOZT010107MASDFZA0", "L2");
        assertEquals("Prueba correcta, si elimino", saveResult, true);
    }
    
    /**
     * Método que recupera de la base de datos los LGAC 1 a los que pertenece un integrante 
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testFindFirstLgacOfIntegrant() throws BusinessConnectionException{
        LgacDAO lgacDAO = new LgacDAO();       
        boolean lgacs = lgacDAO.findFirstLgacOfIntegrant("GOZT010107MASDFZA0");
        assertEquals("Prueba correcta en búsqueda de LGAC", lgacs, true);
    }    

    /**
     * Método que recupera de la base de datos los LGAC 2 a los que pertenece un integrante 
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testFinSecondLgacOfIntegrant() throws BusinessConnectionException{
        LgacDAO lgacDAO = new LgacDAO();       
        boolean lgacs = lgacDAO.findSecondLgacOfIntegrant("GOZT010107MASDFZA0");
        assertEquals("Prueba correcta en búsqueda de LGAC 2", lgacs, false);
    }   
}
