
package testbusinesslogic;

import mx.fei.ca.businesslogic.MemorandumDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Memorandum;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class MemorandumTest {
    public MemorandumTest(){
        
    }
    
    @Test
    public void testInsertMemorandum() throws BusinessConnectionException{
        MemorandumDAO memorandumDAO = new MemorandumDAO();
        Memorandum memorandum = new Memorandum("Revisar ultima parte de metas", "Hasta el momento todo correcto");
        int saveResult = memorandumDAO.saveMemorandum(memorandum, 5);
        assertEquals("Prueba correcta, si guardó", saveResult, 1);
    }
    
    @Test
    public void testUpdateMemorandum() throws BusinessConnectionException{
        MemorandumDAO memorandumDAO = new MemorandumDAO();
        Memorandum memorandum = new Memorandum("Revisar ultima parte de las metas del plan", "Corregir fallo");
        int updateResult = memorandumDAO.updateMemorandum(memorandum, 1, 2);
        assertEquals("Prueba correcta, si modificó", updateResult, 1);
    }
        
    @Test
    public void testFindMemorandumByIdMeeting() throws BusinessConnectionException{
        MemorandumDAO memorandumDAO = new MemorandumDAO();
        int idMemorandumExpected = 1;
        Memorandum memorandum = memorandumDAO.findMemorandumByIdMeeting(2);
        assertEquals("Prueba busqueda de minuta con id reunión", idMemorandumExpected, memorandum.getIdMemorandum());
    }
    
}
