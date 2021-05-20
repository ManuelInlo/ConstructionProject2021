
package mx.fei.ca.presentation;

import java.net.URL;
import java.sql.Date;
import java.time.ZoneId;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import mx.fei.ca.businesslogic.IntegrantDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Integrant;


public class WindowAddIntegrantController implements Initializable {

    @FXML
    private TextField tfNameIntegrant;
    @FXML
    private ComboBox cbRole;    
    @FXML
    private TextField tfStudyDiscipline;
    @FXML
    private TextField tfStudyDegree;
    @FXML
    private ComboBox cbProdepParticipation;
    @FXML
    private TextField tfTypeTeaching;   
    @FXML
    private RadioButton rbL1;
    @FXML
    private RadioButton rbL2;   
    @FXML
    private TextField tfIesStudyDegree;  
    @FXML
    private TextField tfNumberPhone;         
    @FXML
    private DatePicker dpDateBirthday;
    @FXML
    private TextField tfInstitutionalMail;  
    @FXML
    private TextField tfCurp;   
    @FXML
    private ComboBox cbStatusIntegrant;   
    @FXML   
    private Button btnCancel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillComboBoxRole();
        fillComboBoxProdepParticipation();
        fillComboBoxStatusIntegrant();
    }    

    @FXML
    private void fillComboBoxRole() {
        ObservableList<String> listRole = FXCollections.observableArrayList("Responsable","Integrante");
        cbRole.setItems(listRole);
    }

    @FXML
    private void fillComboBoxProdepParticipation() {
        ObservableList<String> listProdepParticipation = FXCollections.observableArrayList("SI","NO");
        cbProdepParticipation.setItems(listProdepParticipation);
    }
    
    @FXML
    private void fillComboBoxStatusIntegrant() {
        ObservableList<String> listStatusIntegrant = FXCollections.observableArrayList("Activo","Inactivo");
        cbStatusIntegrant.setItems(listStatusIntegrant);
    }
    
    @FXML
    public void saveReceptionWork(ActionEvent event) throws BusinessConnectionException {
        String curp = tfCurp.getText();
        String role = cbRole.getSelectionModel().getSelectedItem().toString();
        String nameIntegrant = tfNameIntegrant.getText();
        String studyDegree = tfStudyDegree.getText();
        String studyDiscipline = tfStudyDiscipline.getText();  
        String prodepParticipation = cbProdepParticipation.getSelectionModel().getSelectedItem().toString();
        String typeTeaching = tfTypeTeaching.getText();
        String iesStudyDegree = tfIesStudyDegree.getText();
        String institutionalMail = tfInstitutionalMail.getText();
        String numberPhone = tfNumberPhone.getText();
        Date dateBirthday = parseToSqlDate(java.util.Date.from(dpDateBirthday.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        String statusIntegrant = cbStatusIntegrant.getSelectionModel().getSelectedItem().toString();        
        Integrant integrant = new Integrant(curp, role, nameIntegrant, studyDegree, studyDiscipline, prodepParticipation, typeTeaching,
                iesStudyDegree, institutionalMail, numberPhone, dateBirthday, statusIntegrant);
        IntegrantDAO integrantDAO = new IntegrantDAO();
        boolean saveResult = integrantDAO.saveIntegrant(integrant);
        showConfirmationAlert(saveResult);
    }

    private java.sql.Date parseToSqlDate(java.util.Date date){
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        return sqlDate;
    }  
    
    @FXML
    private void showConfirmationAlert(boolean saveResult){
        if(saveResult){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Confirmación de guardado");
            alert.setContentText("La información del miembro fue guardada con éxito");
            alert.showAndWait();
        }       
    }
        
}
