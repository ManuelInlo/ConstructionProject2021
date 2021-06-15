
package testbusinesslogic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.fei.ca.businesslogic.CollaboratorDAO;
import mx.fei.ca.businesslogic.IntegrantDAO;
import mx.fei.ca.businesslogic.ReceptionWorkDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Collaborator;
import mx.fei.ca.domain.Integrant;
import mx.fei.ca.domain.InvestigationProject;
import mx.fei.ca.domain.ReceptionWork;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ReceptionWorkTest {
    
    public ReceptionWorkTest(){
        
    }
    
    @Test
    public void testInsertReceptionWork() throws BusinessConnectionException{
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        CollaboratorDAO collaboratorDAO = new CollaboratorDAO();
        String startDate = "28-02-2020";
        String endDate = "01-07-2020";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        java.sql.Date startDateReceptionWork = null;
        java.sql.Date endDateReceptionWork = null;
        try{
            startDateReceptionWork = new java.sql.Date(simpleDateFormat.parse(startDate).getTime());
            endDateReceptionWork = new java.sql.Date(simpleDateFormat.parse(endDate).getTime());
        }catch(ParseException ex){
            Logger.getLogger(ReceptionWorkTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Collaborator collaborator = new Collaborator("Roberto Méndez Mendoza", "Estudiante");
        int idCollaboratorResult = collaboratorDAO.saveCollaboratorAndReturnId(collaborator);
        collaborator.setIdCollaborator(idCollaboratorResult);
        
        InvestigationProject investigationProject = new InvestigationProject();
        investigationProject.setIdProject(1);
        
        IntegrantDAO integrantDAO = new IntegrantDAO();
        Integrant integrant = integrantDAO.findIntegrantByCurp("JCPA940514RDTREOP1");
        
        ReceptionWork receptionWork = new ReceptionWork("SI", "La IA en las pruebas de software",
                                                        "C:\\Users\\david\\Documents\\La IA en las pruebas de software.pdf", 
                                                        startDateReceptionWork, endDateReceptionWork, "Licenciatura", "Tesis", "Terminado");
        
        receptionWork.setCollaborator(collaborator);
        receptionWork.setIntegrant(integrant);
        receptionWork.setInvestigationProject(investigationProject);
        
        boolean saveResult = receptionWorkDAO.savedReceptionWork(receptionWork);
        assertTrue("Prueba insertar trabajo recepcional", saveResult);
    }
    
    @Test
    public void testUpdateReceptionWorkById() throws BusinessConnectionException{
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        String startDate = "28-02-2020";
        String endDate = "01-07-2020";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        java.sql.Date startDateReceptionWork = null;
        java.sql.Date endDateReceptionWork = null;
        try{
            startDateReceptionWork = new java.sql.Date(simpleDateFormat.parse(startDate).getTime());
            endDateReceptionWork = new java.sql.Date(simpleDateFormat.parse(endDate).getTime());
        }catch(ParseException ex){
            Logger.getLogger(ReceptionWorkTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Collaborator collaborator = new Collaborator("Roberto Méndez Mendoza", "Estudiante");
        collaborator.setIdCollaborator(6);
        
        InvestigationProject investigationProject = new InvestigationProject();
        investigationProject.setIdProject(1);
        
        IntegrantDAO integrantDAO = new IntegrantDAO();
        Integrant integrant = integrantDAO.findIntegrantByCurp("JCPA940514RDTREOP1");
        
        ReceptionWork receptionWork = new ReceptionWork("NO", "La IA en las pruebas de software",
                                                        "C:\\Users\\david\\Documents\\La IA en las pruebas de software.pdf", 
                                                        startDateReceptionWork, endDateReceptionWork, "Licenciatura", "Tesis", "Terminado");
        
        receptionWork.setCollaborator(collaborator);
        receptionWork.setIntegrant(integrant);
        receptionWork.setInvestigationProject(investigationProject);
        
        boolean updateResult = receptionWorkDAO.updatedReceptionWorkById(receptionWork, 11);
        assertTrue("Prueba modificar trabajo recepcional", updateResult);
    }
    
    @Test
    public void testFindReceptionWorksByPositiveImpactCA() throws BusinessConnectionException{
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        ArrayList<ReceptionWork> receptionWorks = receptionWorkDAO.findReceptionWorksByPositiveImpactCA();
        assertEquals("Prueba busqueda de trabajos recepcionales por su impacto al ca", receptionWorks.size(), 7);
    }
    
    @Test
    public void testFindReceptionWorksByCurpIntegrant() throws BusinessConnectionException{
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        ArrayList<ReceptionWork> receptionWorks = receptionWorkDAO.findLastTwoReceptionWorksByCurpIntegrant("JCPA940514RDTREOP1");
        assertEquals("Prueba busqueda de trabajos recepcionales de un integrante por curp", receptionWorks.size(), 2);
    }
    
    @Test
    public void testFindReceptionWorkByInitialesOfTitle() throws BusinessConnectionException{
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        ArrayList <ReceptionWork> receptionWorks = receptionWorkDAO.findReceptionWorkByInitialesOfTitle("Impa", "JCPA940514RDTREOP1");
        assertEquals("Prueba encontrar trabajo recepcional por título", receptionWorks.size(), 2);
    }
    
    @Test
    public void testExistsReceptionWorkTitle() throws BusinessConnectionException{
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        boolean exists = receptionWorkDAO.existsReceptionWorkTitle("Impacto de la Inteligencia Artificial en el diseño de software");
        assertTrue("Prueba mandar a validar un titulo que ya existe de trabajo recepcional", exists);
    }
    
    @Test
    public void testExistsReceptionWorkFileRoute() throws BusinessConnectionException{
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        boolean exists = receptionWorkDAO.existsReceptionWorkFileRoute("C:\\Users\\david\\Documents\\La IA en las pruebas de software.pdf");
        assertTrue("Prueba mandar a validar una ruta de archivo que ya existe en un trabajo recepcional", exists);
    }  
    
    @Test
    public void testExistsReceptionWorkTitleForUpdate() throws BusinessConnectionException{
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        boolean exists = receptionWorkDAO.existsReceptionWorkTitleForUpdate("La reeingeniería de software", 1);
        assertFalse("Prueba mandar a validar un titulo modificado que no existe de trabajo recepcional", exists);
    }
    
    @Test
    public void testExistsReceptionWorkFileRouteForUpdate() throws BusinessConnectionException{
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        boolean exists = receptionWorkDAO.existsReceptionWorkFileRouteForUpdate("C:\\Users\\david\\Documents\\La IA en la IS.pdf", 1);
        assertFalse("Prueba mandar a validar una ruta de archivo modificada que no existe en un trabajo recepcional", exists);
    } 
}
