
package mx.fei.ca.presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    private Label lbLgac1;
    @FXML
    private Label lbLgac2;   
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
     * @throws mx.fei.ca.businesslogic.exceptions.BusinessConnectionException
     */
    
    public void showIntegrantData(Integrant integrant) throws BusinessConnectionException{   
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
        LgacDAO lgacDAO = new LgacDAO();  
            if(lgacDAO.findFirstLgacOfIntegrant(integrant.getCurp())){
                lbLgac1.setText("L1");
            }        
            if(lgacDAO.findSecondLgacOfIntegrant(integrant.getCurp())){
                lbLgac2.setText("L2");
            }  
    }      

    /**
     * Método que manda a abrir la ventana de modificación de integrante
     * @param event Define el evento generado
     */
    
    @FXML
    private void modifyIntegrant(ActionEvent event) throws BusinessConnectionException {
        if(integrant.getCurp().equals(lbCurp.getText())){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowModifyIntegrant.fxml"));
                Parent root = null;
            try {
                root = fxmlLoader.load();
            } catch (IOException ex) {
                Logger.getLogger(WindowIntegrantDataController.class.getName()).log(Level.SEVERE, null, ex);
            }
            WindowModifyIntegrantController windowModifyIntegrantController = fxmlLoader.getController();
            windowModifyIntegrantController.setIntegrant(integrant);
            windowModifyIntegrantController.fillFieldsIntegrants(integrantSelect);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            closeIntegrantData(event);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Función de modificar deshabilitada");
            alert.setContentText("Solo el integrante puede modificar su propio perfil");
            alert.showAndWait();
        }   
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
