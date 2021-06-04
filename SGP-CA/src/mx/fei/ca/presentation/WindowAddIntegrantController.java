
package mx.fei.ca.presentation;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.fei.ca.businesslogic.IntegrantDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Integrant;

public class WindowAddIntegrantController implements Initializable {

    @FXML
    private TextField tfNameIntegrant;
    @FXML
    private TextField tfStudyDiscipline;
    @FXML
    private ComboBox cbStudyDegree;
    @FXML
    private ComboBox cbRole;
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
    private TextField tfCurp;
    @FXML
    private TextField tfInstitutionalMail;
    @FXML
    private ComboBox cbStatusIntegrant;
    @FXML
    private DatePicker dpDateBirthday;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;
    
    private enum TypeError{
        EMPTYFIELD, INVALIDSTRING, MISSINGSELECTION, MISSINGDATE, INCONSISTENTDATE;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillComboBoxRole();
        fillComboBoxProdepParticipation();
        fillComboBoxStatusIntegrant();
        fillComboBoxStudyDegree();
    }    
    private void fillComboBoxRole() {
        ObservableList<String> listRole = FXCollections.observableArrayList("Responsable", "Integrante");
        cbRole.setItems(listRole);
    }

    private void fillComboBoxProdepParticipation() {
        ObservableList<String> listProdepParticipation = FXCollections.observableArrayList("SI", "NO");
        cbProdepParticipation.setItems(listProdepParticipation);
    }
    
    private void fillComboBoxStatusIntegrant() {
        ObservableList<String> listStatusIntegrant = FXCollections.observableArrayList("Activo", "Inactivo");
        cbStatusIntegrant.setItems(listStatusIntegrant);
    }
    
    private void fillComboBoxStudyDegree() {
        ObservableList<String> listStudyDegree = FXCollections.observableArrayList("Licenciatura", "Maestría", "Doctorado");
        cbStatusIntegrant.setItems(listStudyDegree);
    }
        
    @FXML
    public void saveIntegrant(ActionEvent event) throws BusinessConnectionException {
        if(!existsInvalidFields()){        
            String curp = tfCurp.getText();
            String role = cbRole.getSelectionModel().getSelectedItem().toString();
            String nameIntegrant = tfNameIntegrant.getText();
            String studyDegree = cbStudyDegree.getSelectionModel().getSelectedItem().toString();
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
            boolean saveResult = integrantDAO.savedIntegrant(integrant);
            if(saveResult){
                showConfirmationAlert(saveResult);
                closeRegistrationIntegrant(event);
            }else{
                showLostConnectionAlert(); 
                closeRegistrationIntegrant(event);
            }
        }
    }

    @FXML
    private void closeRegistrationIntegrant(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    private java.sql.Date parseToSqlDate(java.util.Date date){
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        return sqlDate;
    }  
    
    private boolean existsInvalidFields() throws BusinessConnectionException{
        boolean invalidFields = false;
        if(existsEmptyFields() || existsInvalidStrings() || existsMissingSelection() || existsInvalidDates()){
            invalidFields = true;
        }
        return invalidFields;
    }
    
    private boolean existsEmptyFields(){
        boolean emptyFields = false;
        if(tfNameIntegrant.getText().isEmpty() || tfStudyDiscipline.getText().isEmpty() || tfTypeTeaching.getText().isEmpty() || tfIesStudyDegree.getText().isEmpty() 
           || tfNumberPhone.getText().isEmpty() || tfCurp.getText().isEmpty() || tfInstitutionalMail.getText().isEmpty()){
            emptyFields = true;
            TypeError typeError = TypeError.EMPTYFIELD;
            showInvalidFieldAlert(typeError);
        }
        return emptyFields;
    }
    
    private boolean existsInvalidStrings(){
        boolean invalidStrings = false;
        if(existsInvalidCharacters(tfNameIntegrant.getText()) || existsInvalidCharactersForCurp(tfCurp.getText()) || existsInvalidCharacters(tfStudyDiscipline.getText())
           || existsInvalidCharacters(tfTypeTeaching.getText()) || existsInvalidCharacters(tfIesStudyDegree.getText()) || existsInvalidCharactersForEmail(tfInstitutionalMail.getText()) || existsInvalidCharactersForNumberPhone(tfNumberPhone.getText())){
            invalidStrings = true;
            TypeError typeError = TypeError.INVALIDSTRING;
            showInvalidFieldAlert(typeError);
        }
        return invalidStrings;
    }
    
    private boolean existsInvalidCharacters(String textToValidate){
        boolean invalidCharacters = false;
        Pattern pattern = Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúñÑ\\s]+$");
        Matcher matcher = pattern.matcher(textToValidate);
        if(!matcher.find()){
           invalidCharacters = true; 
        }
        return invalidCharacters;
    }
    
    private boolean existsInvalidCharactersForCurp(String textToValidate){
        boolean invalidCharacters = false;
        String validText = "[A-Z]{1}[AEIOU]{1}[A-Z]{2}[0-9]{2}" + "(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])" +
                   "[HM]{1}" + "(AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)" +
                   "[B-DF-HJ-NP-TV-Z]{3}" + "[0-9A-Z]{1}[0-9]{1}$";
        Pattern pattern = Pattern.compile(validText);
        Matcher matcher = pattern.matcher(textToValidate);
        if(!matcher.find()){
            invalidCharacters = true;
        }    
        return invalidCharacters;
    }
     
    private boolean existsInvalidCharactersForEmail(String textToValidate){
        boolean invalidCharacters = false;
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(textToValidate);
        if(!matcher.find()){
           invalidCharacters = true; 
        }
        return invalidCharacters;
    }
    
    private boolean existsInvalidCharactersForNumberPhone(String textToValidate){
        boolean invalidCharacters = false;
        String validText = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$";
        Pattern pattern = Pattern.compile(validText);
        Matcher matcher = pattern.matcher(textToValidate);
        if(!matcher.find()){
           invalidCharacters = true; 
        }
        return invalidCharacters;
    }
    
    private boolean existsMissingSelection(){
        boolean missingSelection = false;
        if(cbRole.getSelectionModel().getSelectedIndex() < 0 || cbProdepParticipation.getSelectionModel().getSelectedIndex() < 0 ||
           cbStatusIntegrant.getSelectionModel().getSelectedIndex() < 0 || cbStudyDegree.getSelectionModel().getSelectedIndex() < 0){
            missingSelection = true;
            TypeError typeError = TypeError.MISSINGSELECTION;
            showInvalidFieldAlert(typeError);
        }
        return missingSelection;
    }

    private boolean existsInvalidDates(){
        boolean invalidDates = false;
        if(existMissingDate(dpDateBirthday) || existInconsistentDates()){
            invalidDates = true;
        }
        return invalidDates;
    }
    
    private boolean existMissingDate(DatePicker datePicker){
        boolean missingDate = false;
        if(datePicker.getValue() == null){
            missingDate = true;
            TypeError typeError = TypeError.MISSINGDATE;
            showInvalidFieldAlert(typeError);
        }
        return missingDate;
    }
    
    private boolean existInconsistentDates(){
        boolean inconsistentDates = false;
        
        LocalDate currentDate = LocalDate.now();        
        int dateBirthdayYear = dpDateBirthday.getValue().getYear();
        int currentDateYear = currentDate.getYear();
        currentDateYear = currentDateYear - 20;
        if(dateBirthdayYear > currentDateYear){
            inconsistentDates = true;
            TypeError typeError = TypeError.INCONSISTENTDATE;
            showInvalidFieldAlert(typeError);
        }
        return inconsistentDates;
    }
    
    private void showInvalidFieldAlert(TypeError typeError){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Campo inválido");
        if(typeError == TypeError.EMPTYFIELD){
          alert.setContentText("Existen campos vacíos, llena los campos para poder guardar");  
        }
        
        if(typeError == TypeError.INVALIDSTRING){
          alert.setContentText("Existen caracteres inválidos, revisa los textos para poder guardar");  
        }
        
        if(typeError == TypeError.MISSINGSELECTION){
            alert.setContentText("Existe selección de campo faltante, selecciona los campos para poder guardar");
        }   
        
        if(typeError == TypeError.INCONSISTENTDATE){
            alert.setContentText("La fecha de nacimiento es incorrecta, corrige el campo para poder guardar");
        }
        alert.showAndWait();    
    }
    
    private void showConfirmationAlert(boolean saveResult){
        if(saveResult){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Confirmación de guardado");
            alert.setContentText("La información del miembro fue guardada con éxito");
            alert.showAndWait();
        }       
    }   
    
    private void showLostConnectionAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Perdida de conexión");
        alert.setContentText("Perdida de conexión con la base de datos, no se pudo guardar. Intente más tarde");
        alert.showAndWait();
    }
}
