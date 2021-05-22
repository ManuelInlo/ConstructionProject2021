
package testbusinesslogic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.fei.ca.businesslogic.IntegrantDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Integrant;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class IntegrantTest {
    public IntegrantTest(){
        
    }
    
    @Test
    public void testInsertIntegrant() throws BusinessConnectionException{
        IntegrantDAO integrantDAO = new IntegrantDAO();
        String date = "20-05-1980";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        java.sql.Date birthdayDate = null;
        try {
            birthdayDate = new java.sql.Date(simpleDateFormat.parse(date).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(IntegrantTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Integrant integrant = new Integrant("MCUD940585RDTRER23", "Responsable", "María Karen Cortés Verdín", "Doctorado", "Ingeniería de software",
                    "Activo", "PTC", "Universidad Veracruzana", "kcortes@uv.mx", "2251214658", birthdayDate, "Activo");
        boolean saveResult = integrantDAO.saveIntegrant(integrant);
        assertEquals("Prueba correcta, si guardó", saveResult, true);
    }

    @Test    
    public void testUpdateIntegrant() throws BusinessConnectionException{
        IntegrantDAO integrantDAO = new IntegrantDAO();
        String date = "10-01-1962";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        java.sql.Date birthdayDate = null;
        try {
            birthdayDate = new java.sql.Date(simpleDateFormat.parse(date).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(IntegrantTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Integrant integrant = new Integrant("MCUD940585RDTRER22", "Responsable", "Alicia Ruiz", "Doctorado", "Ingeniería de software",
                    "Inactivo", "PTC", "Universidad Veracruzana", "hasgret@uv.mx", "2251214658", birthdayDate, "Activo");
        boolean updateResult = integrantDAO.updateIntegrant(integrant, "MCUD940585RDTRER22");
        assertEquals("Prueba correcta, si actualizo", updateResult, true);
    }    
    
    @Test    
    public void testDeleteIntegrantByCurp() throws BusinessConnectionException{
        IntegrantDAO integrantDAO = new IntegrantDAO();
        boolean deleteResult = integrantDAO.deleteIntegrantByCurp("MCUD940585RDTRER23");
        assertEquals("Prueba correcta, se elimino integrante", deleteResult, true);       
    }    
    
    @Test 
    public void testChangedPasswordIntegrant() throws BusinessConnectionException{
        
    }
}
