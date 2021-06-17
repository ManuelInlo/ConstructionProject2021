
package testbusinesslogic;

import java.util.ArrayList;
import mx.fei.ca.businesslogic.IntegrantDAO;
import mx.fei.ca.businesslogic.MemorandumApproverDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Integrant;
import mx.fei.ca.domain.MemorandumApprover;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Clase para representar los test de la clase MemorandumApproverDAO
 * @author David Alexander Mijangos Paredes
 * @version 17-06-2021
 */

public class MemorandumApproverTest {
    
    /**
     * Constructor para la creación de un MemorandumApproverTest
     */
    
    public MemorandumApproverTest(){
        
    }
    
    /**
     * Método que realiza test para la inserción de un nuevo aprobador de minuta en la base de datos
     * @throws BusinessConnectionException 
     */
    
   @Test
    public void testInsertMemorandumApprover() throws BusinessConnectionException{
        MemorandumApproverDAO memorandumApproverDAO = new MemorandumApproverDAO();
        IntegrantDAO integrantDAO = new IntegrantDAO();
        Integrant integrant = integrantDAO.findIntegrantByCurp("JCPA940514RDTREOP1");
        MemorandumApprover memorandumApprover = new MemorandumApprover(integrant);
        boolean saveResult = memorandumApproverDAO.savedMemorandumApprover(memorandumApprover, 3);
        assertTrue("Prueba insertar aprovador de minuta", saveResult);
    }
    
    /**
     * Método que realiza test para la obtención de los aprobadores de una minuta de acuerdo al identificador de la minuta
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testFindMemorandumApproversByIdMemorandum() throws BusinessConnectionException{
        MemorandumApproverDAO memorandumApproverDAO = new MemorandumApproverDAO();
        ArrayList<MemorandumApprover> memorandumApprovers = memorandumApproverDAO.findMemorandumApproversByIdMemorandum(3);
        assertEquals("Prueba encontrar aprobadores de una minuta", memorandumApprovers.size(), 3);
    }
    
    /**
     * Método que realiza test para verificar la existencia de un aprobador de minuta de acuerdo a la curp del integrante
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testExistsMemorandumApproverByCurp() throws BusinessConnectionException{
        MemorandumApproverDAO memorandumApproverDAO = new MemorandumApproverDAO();
        boolean existsMemorandumApprover = memorandumApproverDAO.existsMemorandumApproverByCurp("JCPA940514RDTREOP1", 3);
        assertTrue("Prueba validar si el integrante ya aprobó la minuta", existsMemorandumApprover);
    }
    
}
