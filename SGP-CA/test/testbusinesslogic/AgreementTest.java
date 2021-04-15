
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
        int saveResult = agreementDAO.saveAgreement(agreement, 1);
        assertEquals("Prueba correcta, si guardó", saveResult, 1);
    }
    
    @Test
    public void testUpdateAgreement() throws BusinessConnectionException{
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
        int updateResult = agreementDAO.updateAgreement(agreement, 5, 1);
        assertEquals("Prueba correcta, si guardó", updateResult, 1);
    }
    
    @Test
    public void testDeleteAgreementById() throws BusinessConnectionException{
        AgreementDAO agreementDAO = new AgreementDAO();
        int deleteResult = agreementDAO.deleteAgreementById(4);
        assertEquals("Prueba correcta, se eliminó", deleteResult, 1);
    }
    
    @Test
    public void testFindAgreementsByIdMemorandum() throws BusinessConnectionException, BusinessDataException{
        AgreementDAO agreementDAO = new AgreementDAO();
        ArrayList<Agreement> agreements = agreementDAO.findAgreementsByIdMemorandum(1);
        assertEquals("Prueba correcta",agreements.size(), 3);
    }
}
