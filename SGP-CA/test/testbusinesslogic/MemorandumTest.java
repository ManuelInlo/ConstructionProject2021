
package testbusinesslogic;

import mx.fei.ca.businesslogic.MemorandumDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Memorandum;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class MemorandumTest {
    public MemorandumTest(){
        
    }
    
    @Test
    public void testInsertMemorandum() throws BusinessConnectionException{
        MemorandumDAO memorandumDAO = new MemorandumDAO();
        Memorandum memorandum = new Memorandum("Revisar ultima parte de metas", "Hasta el momento todo correcto", 
                                               "Por aprobar");
        int idMemorandumResult = memorandumDAO.saveAndReturnIdMemorandum(memorandum, 5);
        assertNotSame("Prueba guardar minuta", idMemorandumResult, 0);
    }
    
    @Test
    public void testUpdateMemorandum() throws BusinessConnectionException{
        MemorandumDAO memorandumDAO = new MemorandumDAO();
        Memorandum memorandum = new Memorandum("Revisar ultima parte de las metas del plan", "Corregir fallo", 
                                                "Por aprobar");
        boolean updateResult = memorandumDAO.updatedMemorandum(memorandum, 3, 5);
        assertTrue("Prueba correcta, si modificó", updateResult);
    }
        
    @Test
    public void testFindMemorandumByIdMeeting() throws BusinessConnectionException{
        MemorandumDAO memorandumDAO = new MemorandumDAO();
        int idMemorandumExpected = 3;
        Memorandum memorandum = memorandumDAO.findMemorandumByIdMeeting(5);
        assertEquals("Prueba busqueda de minuta con id reunión", idMemorandumExpected, memorandum.getIdMemorandum());
    }
    
}
