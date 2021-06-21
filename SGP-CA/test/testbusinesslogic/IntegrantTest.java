
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

/**
 * Clase para representar las pruebas unitarias de los métodos de la clase IntegrantDAO
 * @author Gloria Mendoza González
 * @version 17-06-2021
 */

public class IntegrantTest {
    
    /**
     * Constructor para la creación de IntegrantTest
     */
    
    public IntegrantTest(){
        
    }
    
    /**
     * Método que realiza test para la inserción de un nuevo integrante a la base de datos
     * @throws BusinessConnectionException 
     */
    
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

    /**
     * Método que realiza test para la modificación de un integrante de acuerdo a su curp
     * @throws BusinessConnectionException 
     */
    
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
    
    /**
     * Método que realiza test para la eliminación de un integrante de acuerdo a su curp
     * @throws BusinessConnectionException 
     */
    
    @Test    
    public void testDeleteIntegrantByCurp() throws BusinessConnectionException{
        IntegrantDAO integrantDAO = new IntegrantDAO();
        boolean deleteResult = integrantDAO.deleteIntegrantByCurp("GOZT010107MASDFZA0");
        assertEquals("Prueba correcta, se elimino integrante", deleteResult, true);       
    }    
    
    /**
     * Método que realiza test para la modificación de la contraseña de inicio de sesión de un integrante 
     * @throws BusinessConnectionException 
     */
    
    @Test 
    public void testChangedPasswordIntegrant() throws BusinessConnectionException{
        IntegrantDAO integrantDAO = new IntegrantDAO();
        String newPassword = "password123";
        boolean changedResult = integrantDAO.changedPasswordIntegrant(newPassword, "OCHJ710514RDTREOP1");
        assertEquals("Prueba cambio contraseña", changedResult, true);
    }
    
    /**
     * Método que realiza test para la obtención de un integrante de acuerdo a su correo institucional y su contraseña
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testGetIntegrantByInstitutionalMail() throws BusinessConnectionException{
        IntegrantDAO integrantDAO = new IntegrantDAO();
        String passwordIntegrantExpected = "password123";
        Integrant integrant = integrantDAO.getIntegrantByInstitutionalMail("kcortes@uv.mx");
        assertEquals("Prueba encontrar integrante por email", passwordIntegrantExpected, integrant.getPassword());
    }
    
    /**
     * Método que realiza test para la obtención de un integrante de acuerdo a su curp
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testFindIntegrantByCurp() throws BusinessConnectionException{
        IntegrantDAO integrantDAO = new IntegrantDAO();
        Integrant integrant = integrantDAO.findIntegrantByCurp("JCPA940514RDTREOP1");
        String nameIntegrantExpected = "Juan Carlos Pérez Arriaga";
        assertEquals("Prueba encontrar integrante por curp", nameIntegrantExpected, integrant.getNameIntegrant());
    }
    
    /**
     * Método que realiza test para la verificación de existencia de nombre de un integrante
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testExistsIntegrantName() throws BusinessConnectionException{
        IntegrantDAO integrantDAO = new IntegrantDAO();
        boolean exists = integrantDAO.existsIntegrantName("Juan Carlos Pérez Arriaga");
        assertTrue("Prueba mandar a validar un nombre que ya existe de un integrante", exists);
    }
    
    /**
     * Método que realiza test para la verificación de existencia de curp de un integrante
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testExistsIntegrantCurp() throws BusinessConnectionException{
        IntegrantDAO integrantDAO = new IntegrantDAO();
        boolean exists = integrantDAO.existsIntegrantCurp("MCUD940585RDTRER23");
        assertTrue("Prueba mandar a validar una curp que ya existe de un integrante", exists);
    }
    
    /**
     * Método que realiza test para la verificación de existencia de correo institucional de un integrante
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testExistsIntegrantEmail() throws BusinessConnectionException{
        IntegrantDAO integrantDAO = new IntegrantDAO();
        boolean exists = integrantDAO.existsIntegrantEmail("kcortes@uv.mx");
        assertTrue("Prueba mandar a validar una curp que ya existe de un integrante", exists);
    }
    
    /**
     * Método que realiza test para la obtención de todos los integrantes activos del CA
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testFindAllIntegrants() throws BusinessConnectionException{
        IntegrantDAO integrantDAO = new IntegrantDAO();
        ArrayList<Integrant> integrants = integrantDAO.findAllIntegrants();
        assertEquals("Prueba busqueda de todos los integrantes", integrants.size(), 4);
    }
    
    /**
     * Método que realiza test para la obtención de integrantes de acuerdo a las iniciales de su nombre
     * @throws BusinessConnectionException 
     */
    
    @Test
    public void testFindIntegrantByInitialesOfTitle() throws BusinessConnectionException{
        IntegrantDAO integrantDAO = new IntegrantDAO();
        ArrayList <Integrant> integrants = integrantDAO.findIntegrantsByInitialesOfTitle("Juan");
        assertEquals("Prueba encontrar integrante por iniciales de nombre", integrants.size(), 2);
    }    
          
}
