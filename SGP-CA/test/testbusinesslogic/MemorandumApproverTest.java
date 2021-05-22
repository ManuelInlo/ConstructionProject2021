
package testbusinesslogic;

import java.util.ArrayList;
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
    
   /* @Test
    public void testInsertMemorandumApprover() throws BusinessConnectionException{
        MemorandumApproverDAO memorandumApproverDAO = new MemorandumApproverDAO();
        Integrant integrant = new Integrant("JCPA940514RDTREOP1");
        MemorandumApprover memorandumApprover = new MemorandumApprover(integrant);
        boolean saveResult = memorandumApproverDAO.savedMemorandumApprover(memorandumApprover, 3);
        assertTrue("Prueba insertar aprovador de minuta", saveResult);
    }*/
    
    public void testFindMemorandumApproversByIdMemorandum() throws BusinessConnectionException{
        MemorandumApproverDAO memorandumApproverDAO = new MemorandumApproverDAO();
        ArrayList<MemorandumApprover> memorandumApprovers = memorandumApproverDAO.findMemorandumApproversByIdMemorandum(3);
        assertEquals("Prueba encontrar aprovadores de una minuta", memorandumApprovers.size(), 1);
    }
}
