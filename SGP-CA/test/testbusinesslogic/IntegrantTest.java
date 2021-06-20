
package testbusinesslogic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.fei.ca.businesslogic.IntegrantDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Integrant;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
        boolean saveResult = integrantDAO.savedIntegrant(integrant);
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
        Integrant integrant = new Integrant("MCUD940585RDTRER28", "Responsable", "Alicia Ruiz", "Doctorado", "Ingeniería de software",
                    "Inactivo", "PTC", "Universidad Veracruzana", "hasgret@uv.mx", "2251214658", birthdayDate, "Activo");
        boolean updateResult = integrantDAO.updatedIntegrant(integrant, "MCUD940585RDTRER28");
        assertEquals("Prueba correcta, si actualizo", updateResult, true);
    }    
    
    @Test    
    public void testDeleteIntegrantByCurp() throws BusinessConnectionException{
        IntegrantDAO integrantDAO = new IntegrantDAO();
        boolean deleteResult = integrantDAO.deleteIntegrantByCurp("JOPA820701HASBDFO9");
        assertEquals("Prueba correcta, se elimino integrante", deleteResult, true);       
    }    
    
    @Test 
    public void testChangedPasswordIntegrant() throws BusinessConnectionException{
        IntegrantDAO integrantDAO = new IntegrantDAO();
        String newPassword = "MCUD940585RDTRER23";
        boolean changedResult = integrantDAO.changedPasswordIntegrant(newPassword, "MCUD940585RDTRER23");
        assertEquals("Prueba cambio contraseña", changedResult, true);
    }
    
    @Test
    public void testGetIntegrantByInstitutionalMail() throws BusinessConnectionException{
        IntegrantDAO integrantDAO = new IntegrantDAO();
        String passwordIntegrantExpected = "password123";
        Integrant integrant = integrantDAO.getIntegrantByInstitutionalMail("kcortes@uv.mx");
        assertEquals("Prueba encontrar integrante por email", passwordIntegrantExpected, integrant.getPassword());
    }
    
    @Test
    public void testFindIntegrantByCurp() throws BusinessConnectionException{
        IntegrantDAO integrantDAO = new IntegrantDAO();
        Integrant integrant = integrantDAO.findIntegrantByCurp("JCPA940514RDTREOP1");
        String nameIntegrantExpected = "Juan Carlos Pérez Arriaga";
        assertEquals("Prueba encontrar integrante por curp", nameIntegrantExpected, integrant.getNameIntegrant());
    }
    
    @Test
    public void testExistsIntegrantName() throws BusinessConnectionException{
        IntegrantDAO integrantDAO = new IntegrantDAO();
        boolean exists = integrantDAO.existsIntegrantName("Juan Carlos Pérez Arriaga");
        assertTrue("Prueba mandar a validar un nombre que ya existe de un integrante", exists);
    }
    
    @Test
    public void testExistsIntegrantCurp() throws BusinessConnectionException{
        IntegrantDAO integrantDAO = new IntegrantDAO();
        boolean exists = integrantDAO.existsIntegrantCurp("MCUD940585RDTRER23");
        assertTrue("Prueba mandar a validar una curp que ya existe de un integrante", exists);
    }
    
    @Test
    public void testExistsIntegrantEmail() throws BusinessConnectionException{
        IntegrantDAO integrantDAO = new IntegrantDAO();
        boolean exists = integrantDAO.existsIntegrantEmail("kcortes@uv.mx");
        assertTrue("Prueba mandar a validar una curp que ya existe de un integrante", exists);
    }
    
    @Test
    public void testFindAllIntegrants() throws BusinessConnectionException{
        IntegrantDAO integrantDAO = new IntegrantDAO();
        ArrayList<Integrant> integrants = integrantDAO.findAllIntegrants();
        assertEquals("Prueba busqueda de todos los integrantes", integrants.size(), 4);
    }
          
}
