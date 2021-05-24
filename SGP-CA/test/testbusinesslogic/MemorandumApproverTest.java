
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


public class MemorandumApproverTest {
    public MemorandumApproverTest(){
        
    }
    
   @Test
    public void testInsertMemorandumApprover() throws BusinessConnectionException{
        MemorandumApproverDAO memorandumApproverDAO = new MemorandumApproverDAO();
        IntegrantDAO integrantDAO = new IntegrantDAO();
        Integrant integrant = integrantDAO.findIntegrantByCurp("JCPA940514RDTREOP1");
        MemorandumApprover memorandumApprover = new MemorandumApprover(integrant);
        boolean saveResult = memorandumApproverDAO.savedMemorandumApprover(memorandumApprover, 3);
        assertTrue("Prueba insertar aprovador de minuta", saveResult);
    }
    
    @Test
    public void testFindMemorandumApproversByIdMemorandum() throws BusinessConnectionException{
        MemorandumApproverDAO memorandumApproverDAO = new MemorandumApproverDAO();
        ArrayList<MemorandumApprover> memorandumApprovers = memorandumApproverDAO.findMemorandumApproversByIdMemorandum(3);
        assertEquals("Prueba encontrar aprobadores de una minuta", memorandumApprovers.size(), 3);
    }
    
}
