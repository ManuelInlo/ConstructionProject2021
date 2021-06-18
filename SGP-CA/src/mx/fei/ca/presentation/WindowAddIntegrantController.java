
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.fei.ca.businesslogic.IntegrantDAO;
import mx.fei.ca.businesslogic.LgacDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Integrant;

/**
 * Clase para representar el controlador del FXML WindowAddIntegrant
 * @author Gloria Mendoza González
 * @version 17-06-2021
 */

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
    private CheckBox checkBoxL1;
    @FXML
    private CheckBox checkBoxL2;
    @FXML
    private Label lbUser;
    private Integrant integrant;
    
    /**
     * Enumerado que representa los tipos de errores específicos de GUI al agregar un integrante
     */
    
    private enum TypeError{
        EMPTYFIELD, INVALIDSTRING, MISSINGSELECTION, MISSINGDATE, INCONSISTENTDATE, NOSELECTIONLGAC, NAMEDUPLICATE, 
        CURPDUPLICATE, EMAILDUPLICATE;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillComboBoxRole();
        fillComboBoxProdepParticipation();
        fillComboBoxStatusIntegrant();
        fillComboBoxStudyDegree();
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
     * Método que llena el ComboBox de tipo de participación dentro del CA
     */
    
    private void fillComboBoxRole() {
        ObservableList<String> listRole = FXCollections.observableArrayList("Responsable", "Integrante");
        cbRole.setItems(listRole);
    }
    
    /**
     * Método que llena el ComboBox de tipo de participación PRODEP
     */

    private void fillComboBoxProdepParticipation() {
        ObservableList<String> listProdepParticipation = FXCollections.observableArrayList("SI", "NO");
        cbProdepParticipation.setItems(listProdepParticipation);
    }
    
    /**
     * Método que llena el ComboBox del estado actual del integrante
     */
    
    private void fillComboBoxStatusIntegrant() {
        ObservableList<String> listStatusIntegrant = FXCollections.observableArrayList("Activo", "Inactivo");
        cbStatusIntegrant.setItems(listStatusIntegrant);
    }
    
    /**
     * Método que llena el ComboBox de máximo grado de estudios del integrante
     */
    
    private void fillComboBoxStudyDegree() {
        ObservableList<String> listStudyDegree = FXCollections.observableArrayList("Licenciatura", "Maestría", "Doctorado");
        cbStudyDegree.setItems(listStudyDegree);
    }
    
    /**
     * Método que manda a guardar un integrante de acuerdo a la información obtenida de los campos de la GUI
     * @param event Define el evento generado
     * @throws BusinessConnectionException 
     */
        
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
            integrant.setPassword(integrantDAO.encryptPassword(curp));
            boolean saveResult = integrantDAO.savedIntegrant(integrant);
            
            if(saveResult){
                if(checkBoxL1.isSelected()){
                    LgacDAO lgacDAO = new LgacDAO();
                    boolean result = lgacDAO.applyLgacByIntegrant(curp, checkBoxL1.getText());
                    if(result == false){
                        showLostConnectionAlert(); 
                        closeRegistrationIntegrant(event);
                    }
                }
                if(checkBoxL2.isSelected()){
                    LgacDAO lgacDAO = new LgacDAO();
                    boolean result = lgacDAO.applyLgacByIntegrant(curp, checkBoxL2.getText()); 
                    if(result == false){
                        showLostConnectionAlert(); 
                        closeRegistrationIntegrant(event);
                    }                    
                }
                showConfirmationAlert();
                closeRegistrationIntegrant(event);
            }else{
                showLostConnectionAlert(); 
                closeRegistrationIntegrant(event);
            }
        }
    }
    
    /**
     * Método que cierra la GUI actual
     * @param event Define el evento generado
     */

    @FXML
    private void closeRegistrationIntegrant(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Método que cambia una variable util.Date a sql.Date porque se necesita para guardar en la base de datos
     * @param date Define la variable de tipo util.Date obtenida de la GUI
     * @return Variable de tipo sql.Date
     */
    
    private java.sql.Date parseToSqlDate(java.util.Date date){
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        return sqlDate;
    }  
    
    /**
     * Método que devuelve si existen o no campos inválidos
     * @return Booleano con el resultado de la verificación, devuelve true si existen campos inválidos, de lo contrario devuelve false
     * @throws BusinessConnectionException 
     */
       
    private boolean existsInvalidFields() throws BusinessConnectionException{
        boolean invalidFields = false;
        if(existsEmptyFields() || existsInvalidStrings() || existsMissingSelection() || existsInvalidDates() ||
           existsDuplicateValues()){
            invalidFields = true;
        }
        return invalidFields;
    }

    /**
     * Método que verifica si existen campos de la GUI vacíos
     * @return Boolean con el resultado de la verificación, devuelve true si existen campos vacíos, de lo contrario, devuelve false
     */
    
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
 
    /**
     * Método que verifica si existen cadenas inválidas
     * @return Booleano con el resultado de la verificación, devuelve true si existen cadenas inválidas, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidStrings(){
        boolean invalidStrings = false;
        if(existsInvalidCharactersForIntegrant(tfNameIntegrant.getText()) || existsInvalidCharactersForCurp(tfCurp.getText()) || existsInvalidCharacters(tfStudyDiscipline.getText())
           || existsInvalidCharactersForTypeTeaching(tfTypeTeaching.getText()) || existsInvalidCharacters(tfIesStudyDegree.getText()) || existsInvalidCharactersForEmail(tfInstitutionalMail.getText()) || existsInvalidCharactersForNumberPhone(tfNumberPhone.getText())){
            invalidStrings = true;
            TypeError typeError = TypeError.INVALIDSTRING;
            showInvalidFieldAlert(typeError);
        }
        return invalidStrings;
    }
    
    /**
     * Método que verifica si existen caracteres inválidos en el nombre del integrante
     * @param textToValidate Define el nombre del integrante a validar 
     * @return 
     */
    
    private boolean existsInvalidCharactersForIntegrant(String textToValidate){
        boolean invalidCharacters = false;
        Pattern pattern = Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúñÑ\\s]+$");
        Matcher matcher = pattern.matcher(textToValidate);
        if(!matcher.find()){
           invalidCharacters = true; 
        }
        return invalidCharacters;
    }
    
    /**
     * Método que verifica si existen caracteres inválidos en el tipo de docencia
     * Se implementa el método porque puede tener ciertos caracteres válidos a diferencia de un nombre personal
     * @param textToValidate Define el tipo de docencia a validar
     * @return Booleano con el resultado de verificación, devuelve true si existen inválidos, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidCharactersForTypeTeaching(String textToValidate){
        boolean invalidCharacters = false;
        Pattern pattern = Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúñÑ\\s\\.,:]+$");
        Matcher matcher = pattern.matcher(textToValidate);
        if(!matcher.find()){
           invalidCharacters = true; 
        }
        return invalidCharacters;
    }
    
    /**
     * Método que verifica si existen caracteres inválidos para IES de máximo grado de estudios y disciplina de estudios
     * Se implementa el método porque puede tener ciertos caracteres válidos, números y letras
     * @param textToValidate Define la cadena a validar
     * @return Booleano con el resultado de verificación, devuelve true si existen inválidos, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidCharacters(String textToValidate){
        boolean invalidCharacters = false;
        Pattern pattern = Pattern.compile("^[A-Za-z0-9ÁÉÍÓÚáéíóúñÑ\\s\\.,:]+$");
        Matcher matcher = pattern.matcher(textToValidate);
        if(!matcher.find()){
           invalidCharacters = true; 
        }
        return invalidCharacters;
    }
    
    /**
     * Método que verifica si existen caracteres inválidos en la curp del integrante
     * @param textToValidate Define la curp a validar
     * @return Booleano con el resultado de verificación, devuelve true si existen inválidos, de lo contrario, devuelve false
     */
    
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
     
    /**
     * Método que verifica si existen caracteres inválidos en el correo electrónico del integrante
     * @param textToValidate Define el correo a validar
     * @return Booleano con el resultado de verificación, devuelve true si existen inválidos, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidCharactersForEmail(String textToValidate){
        boolean invalidCharacters = false;
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(textToValidate);
        if(!matcher.find()){
           invalidCharacters = true; 
        }
        return invalidCharacters;
    }
    
    /**
     * Método que verifica si existen caracteres inválidos en el número de teléfono del integrante
     * @param textToValidate Define el número de teléfono a validar
     * @return Booleano con el resultado de verificación, devuelve true si existen inválidos, de lo contrario, devuelve false
     */
    
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
    
    /**
     * Método que verifica si existen selecciones de campos faltantes en la GUI
     * @return Booleano con el resultado de verificación, devuelve true si existen faltantes, de lo contrario, devuelve false
     */
    
    private boolean existsMissingSelection(){
        boolean missingSelection = false;
        if(cbRole.getSelectionModel().getSelectedIndex() < 0 || cbProdepParticipation.getSelectionModel().getSelectedIndex() < 0 ||
           cbStatusIntegrant.getSelectionModel().getSelectedIndex() < 0 || cbStudyDegree.getSelectionModel().getSelectedIndex() < 0 ||
           !checkBoxL1.isSelected() && !checkBoxL2.isSelected()){
            missingSelection = true;
            TypeError typeError = TypeError.MISSINGSELECTION;
            showInvalidFieldAlert(typeError);
        }
        return missingSelection;
    }

    /**
     * Método que verifica si la fecha es inválida en los campos de la GUI
     * @return Booleano con el resultado de verificación, devuelve true si existen inválidos, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidDates(){
        boolean invalidDates = false;
        if(existMissingDate(dpDateBirthday) || existInconsistentDates()){
            invalidDates = true;
        }
        return invalidDates;
    }
    
    /**
     * Método que verifica si existe selección de fecha faltante del campo de tipo DatePicker
     * @param datePicker Define la fecha seleccionada a verificar del campo de tipo DatePicker
     * @return Booleano con el resultado de verificación, devuelve true si existe faltante, de lo contrario, devuelve false
     */
    
    private boolean existMissingDate(DatePicker datePicker){
        boolean missingDate = false;
        if(datePicker.getValue() == null){
            missingDate = true;
            TypeError typeError = TypeError.MISSINGDATE;
            showInvalidFieldAlert(typeError);
        }
        return missingDate;
    }
    
    /**
     * Método que verifica si existen inconsistencias con la fecha
     * @return Booleano con el resultado de verificación, devuelve true si existe incosistencia, de lo contrario, devuelve false
     */
    
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
    
        /**
     * Método que manda a verificar si valores obtenidos de la GUI que no pueden duplicarse ya existen en la base de datos
     * El método manda a llamar a otros métodos que se encargan de la verificación en la capa lógica
     * @return Booleano con el resultado de la verificación, devuelve true si existe valor duplicado, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    private boolean existsDuplicateValues() throws BusinessConnectionException{
        IntegrantDAO integrantDAO = new IntegrantDAO();
        boolean duplicateValues = false;        
        boolean duplicateName = false;
        boolean duplicateCurp = false;
        boolean duplicateEmail = false;
        if(integrantDAO.existsIntegrantName(tfNameIntegrant.getText())){  
            duplicateName = true;
            TypeError typeError = TypeError.NAMEDUPLICATE;
            showInvalidFieldAlert(typeError);
        }
           
        if(integrantDAO.existsIntegrantCurp(tfCurp.getText())){
            duplicateCurp = true;
            TypeError typeError = TypeError.CURPDUPLICATE;
            showInvalidFieldAlert(typeError);
        }
        
        if(integrantDAO.existsIntegrantEmail(tfInstitutionalMail.getText())){
            duplicateEmail = true;
            TypeError typeError = TypeError.EMAILDUPLICATE;
            showInvalidFieldAlert(typeError);
        }
        
        if(duplicateName || duplicateCurp || duplicateEmail){
            duplicateValues = true;
        }
        
        return duplicateValues;
    }
    
    /**
     * Método que muestra alerta de campo inválido de acuerdo al tipo de error
     * @param typeError Define el tipo de error que encontró 
     */
 
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
        
        if(typeError == TypeError.NOSELECTIONLGAC){
            alert.setContentText("Debe seleccionar al menos una LGAC, selecciona el campo para poder guardar");
        }
        
        if(typeError == TypeError.NAMEDUPLICATE){
            alert.setContentText("El nombre del integrante ya se encuentra registrado en el sistema");
        }
        
        if(typeError == TypeError.CURPDUPLICATE){
            alert.setContentText("La curp del integrante ya se encuentra registrada en el sistema");
        }  
        
        if(typeError == TypeError.EMAILDUPLICATE){
            alert.setContentText("El correo electrónico del integrante ya se encuentra registrado en el sistema");
        }       
        
        alert.showAndWait();    
    }

    /**
     * Método que muestra alerta de confirmación de guardado
     */
    
    private void showConfirmationAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación de guardado");
        alert.setContentText("La información del miembro fue guardada con éxito");
        alert.showAndWait();    
    }   
    
    /**
     * Método que muestra alerta de perdida de conexión con la base de datos
     */
 
    private void showLostConnectionAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Perdida de conexión");
        alert.setContentText("Perdida de conexión con la base de datos, no se pudo guardar. Intente más tarde");
        alert.showAndWait();
    }
}
