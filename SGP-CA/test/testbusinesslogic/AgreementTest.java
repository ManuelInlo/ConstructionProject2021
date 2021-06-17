
package testbusinesslogic;

import java.util.ArrayList;
import mx.fei.ca.businesslogic.AgreementDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.businesslogic.exceptions.BusinessDataException;
import mx.fei.ca.domain.Agreement;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


public class AgreementTest {
    
    public AgreementTest(){
        
    }
    
    @Test
    public void testInsertAgreement() throws BusinessConnectionException{
        AgreementDAO agreementDAO = new AgreementDAO();
        String date = "Mayo-2021";
        Agreement agreement = new Agreement("Revisar pendientes del CA", date, "Juan Carlos Pérez"
                                            + "Arriaga");
        boolean saveResult = agreementDAO.savedAgreement(agreement, 3);
        assertTrue("Prueba guardar acuerdo", saveResult);
    }
    
    @Test
    public void testUpdatedAgreement() throws BusinessConnectionException{
        AgreementDAO agreementDAO = new AgreementDAO();
        String date = "Junio-2021";  
        Agreement agreement = new Agreement("Revisar pendientes de metas del CA", date, "Juan Carlos Pérez Arriaga");
        boolean updateResult = agreementDAO.updatedAgreement(agreement, 12, 3);
        assertTrue("Prueba modificar acuerdo", updateResult);
    }
    
    @Test
    public void testDeletedAgreementById() throws BusinessConnectionException{
        AgreementDAO agreementDAO = new AgreementDAO();
        boolean deleteResult = agreementDAO.deletedAgreementById(11);
        assertTrue("Prueba borrar acuerdo", deleteResult);
    }
    
    @Test
    public void testFindAgreementsByIdMemorandum() throws BusinessConnectionException{
        AgreementDAO agreementDAO = new AgreementDAO();
        ArrayList<Agreement> agreements = agreementDAO.findAgreementsByIdMemorandum(3);
        assertEquals("Prueba correcta",agreements.size(), 1);
    }
    
    @Test
    public void testGetIdAgreementByDescription() throws BusinessConnectionException{
        AgreementDAO agreementDAO = new AgreementDAO();
        int idAgreementExpected = 16;
        int idAgreementResult = agreementDAO.getIdAgreementByDescription("Agendar reunión para revisar el trabajo de Juan");
        assertEquals("Prueba obtener id de acuerdo por medio de su descripción",idAgreementExpected,idAgreementResult);
    }

}
