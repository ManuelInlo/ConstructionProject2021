package testbusinesslogic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.fei.ca.businesslogic.PreliminaryProjectDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.PreliminaryProject;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class PreliminaryProjectTest {
    public PreliminaryProjectTest(){
        
    }
    
    @Test
    public void testAddPreliminaryProject() throws BusinessConnectionException{
        PreliminaryProjectDAO preliminaryProjectDAO = new PreliminaryProjectDAO();
        String date = "25-05-2021";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        java.sql.Date startDate = null;
        try {
            startDate = new java.sql.Date(simpleDateFormat.parse(date).getTime());
        } catch (ParseException e) {
            Logger.getLogger(IntegrantTest.class.getName()).log(Level.SEVERE, null, e);
        }
        PreliminaryProject preliminaryProject = new PreliminaryProject(1, 4, 6, "Aprendisaje de Frontend", "en curso", "1 semestre",
                "escolarizado", "Un projecto para aprender mas sobre las herramientas de Frontend", startDate);
        boolean saveResult = preliminaryProjectDAO.savedPreliminaryProject(preliminaryProject, 4, 6);
        assertEquals("Prueba correcta, si guardó", saveResult, true);
    }   
}
