package mx.fei.ca.presentation;

import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mx.fei.ca.businesslogic.AgreementDAO;
import mx.fei.ca.businesslogic.IntegrantDAO;
import mx.fei.ca.businesslogic.MeetingDAO;
import mx.fei.ca.businesslogic.MemorandumDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.AgendaPoint;
import mx.fei.ca.domain.Agreement;
import mx.fei.ca.domain.Integrant;
import mx.fei.ca.domain.Memorandum;

/**
 * Clase para representar el controlador del FXML WindowMeeting
 * @author David Alexander Mijangos Paredes
 * @version 17-06-2021
 */

public class WindowMeetingController implements Initializable {

    @FXML
    private TableView<AgendaPoint> tbAgenda;
    @FXML
    private TableColumn<AgendaPoint, String> columnTopic;
    @FXML
    private TableColumn<AgendaPoint, Time> columnHourStart;
    @FXML
    private TableColumn<AgendaPoint, Time> columnHourEnd;
    @FXML
    private TextArea taPendings;
    @FXML
    private TextField tfAgreement;
    @FXML
    private ComboBox cbIntegrants;
    @FXML
    private ComboBox cbMonth;
    @FXML
    private TextArea taNotes;
    @FXML
    private TextField tfYear;
    @FXML
    private TableView<Agreement> tbAgreements;
    @FXML
    private TableColumn<Agreement, String> columnAgreement;
    @FXML
    private TableColumn<Agreement, String> columnIntegrant;
    @FXML
    private TableColumn<Agreement, String> columnDate;
    @FXML
    private Label lbUser;
    private ObservableList<Agreement> agreements;
    private int idMemorandum;
    private int idMeeting;
    
    /**
     * Enumerado que representa los tipos de errores específicos al iniciar una reunión y crear la minuta
     */
    
    public enum TypeError{
        EMPTYFIELDS, MISSINGSELECTION, INVALIDYEAR, EMPTYTABLE, COLUMNMISSINGSELECTION, INVALIDSTRING, DUPLICATEVALUE;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillComboBoxMonth();
        fillComboBoxIntegrants();
        agreements = FXCollections.observableArrayList();
    } 
    
    /**
     * Método que establece el integrante loggeado, permitiendo proyectar su nombre en la GUI
     * @param integrant Define el integrante a proyectar su nombre
     */
    
    public void setIntegrant(Integrant integrant){
        lbUser.setText(integrant.getNameIntegrant());
    }
    
    /**
     * Método que muestra los puntos de agenda de la reunión en una tabla de la GUI
     * @param agendaPoints Define la lista de puntos de agenda de la reunión
     * @param idMeeting Define el identificador de la reunión iniciada
     */
    
    public void showAgendaPoints(ArrayList<AgendaPoint> agendaPoints, int idMeeting){
        this.idMeeting = idMeeting;
        columnTopic.setCellValueFactory(new PropertyValueFactory("topic"));
        columnHourStart.setCellValueFactory(new PropertyValueFactory("startTime"));
        columnHourEnd.setCellValueFactory(new PropertyValueFactory("endTime"));
        ObservableList<AgendaPoint> listAgendaPoints = FXCollections.observableArrayList(agendaPoints);
        tbAgenda.setItems(listAgendaPoints);
    }
    
    /**
     * Método que llena el ComboBox de mes de la GUI
     */
    
    private void fillComboBoxMonth(){
        ObservableList<String> listHours = FXCollections.observableArrayList("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", 
                                                                             "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre");                                    
        cbMonth.setItems(listHours);
    }
    
    /**
     * Método que llena el ComboBox de integrantes del CA
     */
    
    private void fillComboBoxIntegrants(){
        IntegrantDAO integrantDAO = new IntegrantDAO();
        ArrayList<Integrant> integrants = null;
        try {
            integrants = integrantDAO.findAllIntegrants();
        } catch (BusinessConnectionException ex) {
            showLostConnectionAlert();
        }
        ObservableList<Integrant> listIntegrants = FXCollections.observableArrayList(integrants);
        cbIntegrants.setItems(listIntegrants);
    }
    
    /**
     * Método que agrega un nuevo acuerdo a la tabla de acuerdos de la GUI
     * @param event Define el evento generado
     */
    
    @FXML
    private void addAgreement(ActionEvent event){
        if(!existsInvalidFieldsForAgreement()){
            columnAgreement.setCellValueFactory(new PropertyValueFactory("description"));
            columnIntegrant.setCellValueFactory(new PropertyValueFactory("responsible"));
            columnDate.setCellValueFactory(new PropertyValueFactory("dateAgreement"));
            String description = tfAgreement.getText();
            String dateAgreement = cbMonth.getSelectionModel().getSelectedItem().toString() + "-" + tfYear.getText();
            String responsible = cbIntegrants.getSelectionModel().getSelectedItem().toString();
            Agreement agreement = new Agreement(description, dateAgreement, responsible);
            agreements.add(agreement);
            tbAgreements.setItems(agreements);
            cleanFieldsAgreement();
        }
    }
    
    /**
     * Método que elimina un acuerdo seleccionado de la tabla de acuerdos de la GUI
     * @param event Define el evento generado 
     */

    @FXML
    private void deleteAgreement(ActionEvent event){
        Agreement agreement = tbAgreements.getSelectionModel().getSelectedItem();
        if(agreement != null){
            agreements.remove(agreement);
            tbAgreements.refresh();
        }else{
            TypeError typeError = TypeError.COLUMNMISSINGSELECTION;
            showInvalidFieldAlert(typeError);
        }
    }
    
    /**
     * Método que concluye la reunión, mandando a guardar la información de acuerdos y minuta
     * @param event Define el evento generado
     * @throws BusinessConnectionException 
     */

    @FXML
    private void concludeMeeting(ActionEvent event) throws BusinessConnectionException{
        Optional<ButtonType> action = showConfirmationAlert();
        if (action.get() == ButtonType.OK && !existsInvalidFieldsForMemorandum()){
            String pending = taPendings.getText();
            String note = taNotes.getText();
            Memorandum memorandum = new Memorandum(pending, note);
            MemorandumDAO memorandumDAO = new MemorandumDAO();
            MeetingDAO meetingDAO = new MeetingDAO();
            try{
                idMemorandum = memorandumDAO.saveAndReturnIdMemorandum(memorandum, this.idMeeting);
                if (idMemorandum != 0 && savedAgreements() && meetingDAO.updatedStateOfMeeting("Finalizada", idMeeting)) {
                    showConfirmationSaveAlert();
                    closeMeeting(event);
                }    
            } catch (BusinessConnectionException ex) {
                showLostConnectionAlert();  
            }
        }
    }
    
    /**
     * Método que cierra le ventana actual "Reunión"
     * @param event Define el evento generado
     */

    @FXML
    private void closeMeeting(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Método que manda a guardar todos los acuerdos de la tabla de acuerdos de la GUI
     * @return Booleano con el resultado de guardado, devuelve true si guardó, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    private boolean savedAgreements() throws BusinessConnectionException{
        boolean savedAgreement = true;
        AgreementDAO agreementDAO = new AgreementDAO();
        for (Agreement agreement: tbAgreements.getItems()){
            savedAgreement = agreementDAO.savedAgreement(agreement, idMemorandum);
            if(!savedAgreement){
                break;
            }
        }
        return savedAgreement;
    }
    
    /**
     * Método que verifica si existen campos inválidos para un acuerdo
     * El método invoca a otros métodos de validación específicos
     * @return Booleano con el resultado de verificación, devuelve true si existen inválidos, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidFieldsForAgreement(){
        boolean invalidFields = false;
        if(existsEmptyFields(tfAgreement.getText()) || existsInvalidCharacters(tfAgreement.getText()) || existsMissingSelection() ||
           existsEmptyFields(tfYear.getText()) || existsInvalidCharactersForYear(tfYear.getText()) || existsDuplicateValueForAgreement()){
            invalidFields = true;
        }
        return invalidFields;
    }
    
    /**
     * Método que verifica si existen campos inválidos para minuta
     * El método invoca a otros métodos de validación específicos
     * @return Booleano con el resultado de verificación, devuelve true si existen inválidos, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidFieldsForMemorandum(){
        boolean invalidFields = false;
        if(existsEmptyFields(taPendings.getText()) || existsEmptyFields(taNotes.getText()) || existsInvalidCharactersForMemorandum(taPendings.getText()) ||
           existsInvalidCharactersForMemorandum(taNotes.getText()) || existsEmptyTable()){
            invalidFields = true;
        }
        return invalidFields;
    }
    
    /**
     * Método que verifica si existe campo de texto vacío
     * @param fieldToValidate Define el texto a verificar si está vacío o no
     * @return Booleano con el resultado de verificación, devuelve true si está vacío, de lo contrario, devuelve false
     */
    
    private boolean existsEmptyFields(String fieldToValidate){
        boolean emptyField = false;
        if(fieldToValidate.isEmpty()){
            emptyField = true;
            TypeError typeError = TypeError.EMPTYFIELDS;
            showInvalidFieldAlert(typeError);
        }
        return emptyField;
    }
    
    /**
     * Método que verifica si existe selección de campo faltante en la GUI
     * @return Booleano con el resultado de verificación, devuelve true si existe selección faltante, de lo contrario, devuelve false
     */
    
    private boolean existsMissingSelection(){
        boolean missingSelection = false;
        if(cbMonth.getSelectionModel().getSelectedIndex() < 0 || cbIntegrants.getSelectionModel().getSelectedIndex() < 0){
            missingSelection = true;
            TypeError typeError = TypeError.MISSINGSELECTION;
            showInvalidFieldAlert(typeError);
        }
        return missingSelection;
    }
    
    /**
     * Método que verifica si un texto de la GUI contiene caracteres no permitidos
     * @param textToValidate Define el texto a verificar
     * @return Booleano con el resultado de verificación, devuelve true si existe inválidos, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidCharacters(String textToValidate){
        boolean invalidCharacters = false;
        Pattern pattern = Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúñÑ\\s.,:]+$");
        Matcher matcher = pattern.matcher(textToValidate);
        if(!matcher.find()){
           invalidCharacters = true; 
           TypeError typeError = TypeError.INVALIDSTRING;
           showInvalidFieldAlert(typeError);
        }
        return invalidCharacters;
    }
    
    /**
     * Método que verifica si existe texto de los campos de minuta que cuenten con caracteres no permitidos
     * Se implementa el método porque los campos de una minuta, los cuales son notas y pendientes, pueden tener más variedad de tipos de caracteres a comparación de los campos de acuerdo
     * @param textToValidate Define texto del campo de minuta a verificar
     * @return Booleano con el resultado de verificación, devuelve true si existen inválidos, de los contrario, devuelve false
     */
    
    private boolean existsInvalidCharactersForMemorandum(String textToValidate){
        boolean invalidCharacters = false;
        Pattern pattern = Pattern.compile("^[A-Za-z0-9ÁÉÍÓÚáéíóúñÑ\\s.,:()]+$");
        Matcher matcher = pattern.matcher(textToValidate);
        if(!matcher.find()){
           invalidCharacters = true; 
           TypeError typeError = TypeError.INVALIDSTRING;
           showInvalidFieldAlert(typeError);
        }
        return invalidCharacters;
    }
    
    /**
     * Método que verifica si existe año inválido en la GUI
     * Se implementa el método porque un año solo puede contener números y no debe ser menor al año actual
     * @param yearToValidate Define el año recuperado de la GUI a validar
     * @return Booleano con el resultado de verificación, devuelve true si existe inválido, de lo contario, devuelve false
     */
    
    private boolean existsInvalidCharactersForYear(String yearToValidate){
        boolean invalidYear = false;
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher matcher = pattern.matcher(yearToValidate);
        if(!matcher.find()){
           invalidYear = true; 
        }
        
        if(!invalidYear){
           int year = Integer.parseInt(yearToValidate);
           if(year < 2021){
              invalidYear = true; 
           }  
        }
        
        if(invalidYear){
            TypeError typeError = TypeError.INVALIDYEAR;
            showInvalidFieldAlert(typeError);
        }  
        return invalidYear;
    }
    
    /**
     * Método que verifica si la tabla de acuerdos está vacía
     * Se implementa el método porque una reunión debe tener por lo menos un acuerdo
     * @return Booleano con el resultado de verificación, devuelve true si está vacía, de lo contrario, devuelve false
     */
    
    private boolean existsEmptyTable(){
        boolean emptyTable = false;
        ObservableList<Agreement> listAgreements = tbAgreements.getItems();
        if(listAgreements.isEmpty()){
            emptyTable = true;
            TypeError typeError = TypeError.EMPTYTABLE;
            showInvalidFieldAlert(typeError);
        }
        return emptyTable;
    }
    
    /**
     * Método que limpia los campos de acuerdo
     */
    
    private void cleanFieldsAgreement(){
        tfAgreement.clear();
        cbIntegrants.getSelectionModel().clearSelection();
        cbMonth.getSelectionModel().clearSelection();
        tfYear.clear();
    }
    
    /**
     * Método que verifica si existen valores duplicados en un nuevo acuerdo
     * @return Booleano con el resultado de verificación, devuelve true si existe duplicado, de lo contrario, devuelve false
     */
    
    private boolean existsDuplicateValueForAgreement(){
        boolean duplicateValue = false;
        for(Agreement agreement: tbAgreements.getItems()){
            if(agreement.getDescription().equals(tfAgreement.getText())){
                duplicateValue = true;
                TypeError typeError = TypeError.DUPLICATEVALUE;
                showInvalidFieldAlert(typeError);
            }
        }
        return duplicateValue;
    }
    
    /**
     * Método que muestra alerta de campo inválido de acuerdo al tipo de error
     * @param typeError Define el tipo de error que encontró
     */
    
    private void showInvalidFieldAlert(TypeError typeError){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Campo inválido");
        if(typeError == TypeError.EMPTYFIELDS){
          alert.setContentText("Existen campos vacíos, llena los campos para poder guardar");  
        }
        
        if(typeError == TypeError.MISSINGSELECTION){
            alert.setContentText("Falta seleccionar información, selecciona para poder añadir");
        }
        
        if(typeError == TypeError.INVALIDYEAR){
            alert.setContentText("Año inválido, corrobora la información");
        }
        
        if(typeError == TypeError.EMPTYTABLE){
            alert.setContentText("La reunión debe tener por lo menos un acuerdo, llena la tabla acuerdos para poder guardar");
        }
        
        if(typeError == TypeError.COLUMNMISSINGSELECTION){
            alert.setContentText("Selección de columna faltante. Selecciona una columna para poder eliminar");
        }
        
        if(typeError == TypeError.INVALIDSTRING){
            alert.setContentText("Existen carácteres inválidos, corrije por favor");
        }
        
        if(typeError == TypeError.DUPLICATEVALUE){
            alert.setContentText("La descripción del acuerdo ya se encuentra registrado, por favor verifica la información");
        }
        
        alert.showAndWait();
    }
    
    /**
     * Método que muestra alerta de confirmación de conclusión de la reunión
     * @return Devuelve el tipo de botón seleccionado por el usuario
     */
    
    private Optional<ButtonType> showConfirmationAlert(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText("¿Deseas concluir la reunión?");
        Optional<ButtonType> action = alert.showAndWait();
        return action;
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
    
    /**
     * Método que muestra alerta de confirmación de guardado en la base de datos
     */
    
    private void showConfirmationSaveAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación de guardado");
        alert.setContentText("La información fue guardada con éxito");
        alert.showAndWait();
    }
}
