
package testbusinesslogic;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.fei.ca.businesslogic.AgreementDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.businesslogic.exceptions.BusinessDataException;
import mx.fei.ca.domain.Agreement;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


public class AgreementTest {
    
    public AgreementTest(){
        
    }
    
    @Test
    public void testInsertAgreement() throws BusinessConnectionException{
        AgreementDAO agreementDAO = new AgreementDAO();
        String date = "01-07-2021";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        java.sql.Date dateAgreement = null;
        try {
            dateAgreement = new java.sql.Date(simpleDateFormat.parse(date).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(AgreementTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Agreement agreement = new Agreement(2, "Revisar pendientes del CA", dateAgreement, "Juan Carlos Pérez"
                                            + "Arriaga");
        boolean saveResult = agreementDAO.savedAgreement(agreement, 3);
        assertTrue("Prueba guardar acuerdo", saveResult);
    }
    
    @Test
    public void testUpdatedAgreement() throws BusinessConnectionException{
        AgreementDAO agreementDAO = new AgreementDAO();
        String date = "01-08-2021";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        java.sql.Date dateAgreement = null;
        try {
            dateAgreement = new java.sql.Date(simpleDateFormat.parse(date).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(MeetingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Agreement agreement = new Agreement(1, "Revisar pendientes de metas del CA", dateAgreement, "Juan Carlos Pérez Arriaga");
        boolean updateResult = agreementDAO.updatedAgreement(agreement, 5, 1);
        assertTrue("Prueba modificar acuerdo", updateResult);
    }
    
    @Test
    public void testDeletedAgreementById() throws BusinessConnectionException{
        AgreementDAO agreementDAO = new AgreementDAO();
        boolean deleteResult = agreementDAO.deletedAgreementById(4);
        assertTrue("Prueba borrar acuerdo", deleteResult);
    }
    
    @Test
    public void testFindAgreementsByIdMemorandum() throws BusinessConnectionException, BusinessDataException{
        AgreementDAO agreementDAO = new AgreementDAO();
        ArrayList<Agreement> agreements = agreementDAO.findAgreementsByIdMemorandum(1);
        assertEquals("Prueba correcta",agreements.size(), 3);
    }
    
    @Test 
    public void testExistsAgreementDescription() throws BusinessConnectionException{
        AgreementDAO agreementDAO = new AgreementDAO();
       boolean exists = agreementDAO.existsAgreementDescription("Revisar pendientes del CA", 3);
       assertTrue("Prueba mandar a validar una descripción que ya existe en la minuta", exists);
    }
    
    @Test 
    public void testExistsAgreementDescriptionForUpdate() throws BusinessConnectionException{
        AgreementDAO agreementDAO = new AgreementDAO();
       boolean exists = agreementDAO.existsAgreementDescriptionForUpdate("Revisar acuerdos de la reunión pasada del CA", 11, 3);
       assertFalse("Prueba mandar a validar una descripción modificada que no existe en la minuta", exists);
    }
}
