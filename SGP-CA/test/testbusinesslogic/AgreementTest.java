
package testbusinesslogic;

import java.util.ArrayList;
import mx.fei.ca.businesslogic.AgreementDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Agreement;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Clase para representar los test de la clase AgreementDAO
 * @author David Alexander Mijagnos Paredes
 * @version 17-06-2021
 */

public class AgreementTest {
    
    /**
     * Constructor para la creación de un nuevo AgreementTest
     */
    
    public AgreementTest(){
        
    }
    
    /**
     * Método que realiza test la inserción de un nuevo acuerdo de minuta a la base de datos
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testInsertAgreement() throws BusinessConnectionException{
        AgreementDAO agreementDAO = new AgreementDAO();
        String date = "Mayo-2021";
        Agreement agreement = new Agreement("Revisar pendientes del CA", date, "Juan Carlos Pérez Arriaga");
        boolean saveResult = agreementDAO.savedAgreement(agreement, 3);
        assertTrue("Prueba guardar acuerdo", saveResult);
    }
    
    /**
     * Método que realiza test para la modificación de un acuerdo de minuta en la base de datos
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testUpdatedAgreement() throws BusinessConnectionException{
        AgreementDAO agreementDAO = new AgreementDAO();
        String date = "Junio-2021";  
        Agreement agreement = new Agreement("Revisar pendientes de metas del CA", date, "Juan Carlos Pérez Arriaga");
        boolean updateResult = agreementDAO.updatedAgreement(agreement, 12, 3);
        assertTrue("Prueba modificar acuerdo", updateResult);
    }
    
    /**
     * Método que realiza test para la eliminación de un acuerdo de minuta en la base de datos
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testDeletedAgreementById() throws BusinessConnectionException{
        AgreementDAO agreementDAO = new AgreementDAO();
        boolean deleteResult = agreementDAO.deletedAgreementById(11);
        assertTrue("Prueba borrar acuerdo", deleteResult);
    }
    
    /**
     * Método que realiza test para la obtención de los acuerdos de una minuta específica
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testFindAgreementsByIdMemorandum() throws BusinessConnectionException{
        AgreementDAO agreementDAO = new AgreementDAO();
        ArrayList<Agreement> agreements = agreementDAO.findAgreementsByIdMemorandum(3);
        assertEquals("Prueba correcta",agreements.size(), 1);
    }
    
    /**
     * Método que realiza test para la obtención del identificador de un acuerdo de minuta de acuerdo a su descripción e identificador de la minuta
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testGetIdAgreementByDescription() throws BusinessConnectionException{
        AgreementDAO agreementDAO = new AgreementDAO();
        int idAgreementExpected = 16;
        int idAgreementResult = agreementDAO.getIdAgreementByDescription("Agendar reunión para revisar el trabajo de Juan", 7);
        assertEquals("Prueba obtener id de acuerdo por medio de su descripción",idAgreementExpected,idAgreementResult);
    }

}
