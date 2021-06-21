
package mx.fei.ca.presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import mx.fei.ca.businesslogic.LgacDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Integrant;

/**
 * Clase para representar el controlador del FXML WindowIntegrantData
 * @author Gloria Mendoza González
 * @version 18-06-2021
 */

public class WindowIntegrantDataController implements Initializable {
    
    @FXML
    private Label lbUser;   
    @FXML
    private Label lbRole;
    @FXML
    private Label lbStudyDiscipline;
    @FXML
    private Label lbStudyDegree;
    @FXML
    private Label lbProdepParticipation;
    @FXML
    private Label lbTypeTeaching;
    @FXML
    private Label lbStatusIntegrant;
    @FXML
    private Label lbL1;
    @FXML
    private Label lbNameIntegrant;
    @FXML
    private Label lbIesStudyDegree;
    @FXML
    private Label lbNumberPhone;
    @FXML
    private Label lbDateBirthday;
    @FXML
    private Label lbCurp;
    @FXML
    private Label lbInstitutionalMail;
    private Integrant integrant; 
    private Integrant integrantSelect;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    /**
     * Método que establece el integrante loggeado al sistema, permitiendo proyectar su nombre en la GUI
     * @param integrant Define el integrante a establecer en la GUI
     */
    
    public void setIntegrant(Integrant integrant){
        this.integrant = integrant;
        lbUser.setText(integrant.getNameIntegrant());  
    }  
    
    /**
     * Método que muestra la información del capítulo de libro en la GUI
     * @param integrant Define el integrante con la información a mostrar
     */
    
    public void showIntegrantData(Integrant integrant) throws BusinessConnectionException{   
        LgacDAO lgacDAO = new LgacDAO();
        this.integrantSelect = integrant;
        lbNameIntegrant.setText(integrant.getNameIntegrant());
        lbRole.setText(integrant.getRole());
        lbStudyDiscipline.setText(integrant.getStudyDiscipline());
        lbStudyDegree.setText(integrant.getStudyDegree());
        lbProdepParticipation.setText(integrant.getProdepParticipation());
        lbTypeTeaching.setText(integrant.getTypeTeaching());
        lbStatusIntegrant.setText(integrant.getStatusIntegrant());
        lbIesStudyDegree.setText(integrant.getStudyDegree());
        lbNumberPhone.setText(integrant.getNumberPhone());
        lbDateBirthday.setText(String.valueOf(integrant.getDateBirthday()));
        lbCurp.setText(integrant.getCurp());
        lbInstitutionalMail.setText(integrant.getInstitutionalMail());
        lbL1.setText(String.valueOf(lgacDAO.findLgacOfIntegrant(lbCurp.getText())));       
    }    

    /**
     * Método que manda a abrir la ventana de modificación de integrante
     * @param event Define el evento generado
     */
    
    @FXML
    private void modifyIntegrant(ActionEvent event) {
    }
    
    /**
     * Método que cierra la ventana actual "Datos de integrante"
     * @param event Define el evento generado
     */

    @FXML
    private void closeIntegrantData(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();        
    }
    
}
