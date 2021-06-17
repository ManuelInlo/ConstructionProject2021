
package testbusinesslogic;

import mx.fei.ca.businesslogic.MemorandumDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Memorandum;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Clase para representar los test de la clase MemorandumDAO
 * @author David Alexander Mijangos Paredes
 * @version 17-06-2021
 */

public class MemorandumTest {
    
    /**
     * Constructor para la creación de un MemorandumTest
     */
    
    public MemorandumTest(){
        
    }
    
    /**
     * Método que realiza test de inserción de una nueva minuta de reunión en la base de datos 
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testInsertMemorandum() throws BusinessConnectionException{
        MemorandumDAO memorandumDAO = new MemorandumDAO();
        Memorandum memorandum = new Memorandum("Revisar ultima parte de metas", "Hasta el momento todo correcto");
        int idMemorandumResult = memorandumDAO.saveAndReturnIdMemorandum(memorandum, 5);
        assertNotSame("Prueba guardar minuta", idMemorandumResult, 0);
    }
    
    /**
     * Método que realiza test para la modificación de una minuta de reunión en la base de datos
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testUpdateMemorandum() throws BusinessConnectionException{
        MemorandumDAO memorandumDAO = new MemorandumDAO();
        Memorandum memorandum = new Memorandum("Revisar ultima parte de las metas del plan", "Corregir fallo");
        boolean updateResult = memorandumDAO.updatedMemorandum(memorandum, 7);
        assertTrue("Prueba modificar minuta", updateResult);
    }
    
    /**
     * Método que realiza test para la obtención de una minuta de reunión de acuerdo al identificador de la reunión
     * @throws BusinessConnectionException 
     */
        
    @Test
    public void testFindMemorandumByIdMeeting() throws BusinessConnectionException{
        MemorandumDAO memorandumDAO = new MemorandumDAO();
        int idMemorandumExpected = 3;
        Memorandum memorandum = memorandumDAO.findMemorandumByIdMeeting(5);
        assertEquals("Prueba busqueda de minuta con id reunión", idMemorandumExpected, memorandum.getIdMemorandum());
    }
    
}
