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
     * Enumerado que representa los tipos de errores espec??ficos al iniciar una reuni??n y crear la minuta
     */
    
    public enum TypeError{
        EMPTYFIELDS, MISSINGSELECTION, INVALIDYEAR, EMPTYTABLE, COLUMNMISSINGSELECTION, INVALIDSTRING, DUPLICATEVALUE, INVALIDLENGTH;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillComboBoxMonth();
        fillComboBoxIntegrants();
        agreements = FXCollections.observableArrayList();
    } 
    
    /**
     * M??todo que establece el integrante loggeado, permitiendo proyectar su nombre en la GUI
     * @param integrant Define el integrante a proyectar su nombre
     */
    
    public void setIntegrant(Integrant integrant){
        lbUser.setText(integrant.getNameIntegrant());
    }
    
    /**
     * M??todo que muestra los puntos de agenda de la reuni??n en una tabla de la GUI
     * @param agendaPoints Define la lista de puntos de agenda de la reuni??n
     * @param idMeeting Define el identificador de la reuni??n iniciada
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
     * M??todo que llena el ComboBox de mes de la GUI
     */
    
    private void fillComboBoxMonth(){
        ObservableList<String> listHours = FXCollections.observableArrayList("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", 
                                                                             "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre");                                    
        cbMonth.setItems(listHours);
    }
    
    /**
     * M??todo que llena el ComboBox de integrantes del CA
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
     * M??todo que agrega un nuevo acuerdo a la tabla de acuerdos de la GUI
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
     * M??todo que elimina un acuerdo seleccionado de la tabla de acuerdos de la GUI
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
     * M??todo que concluye la reuni??n, mandando a guardar la informaci??n de acuerdos y minuta
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
     * M??todo que cierra le ventana actual "Reuni??n"
     * @param event Define el evento generado
     */

    @FXML
    private void closeMeeting(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    /**
     * M??todo que manda a guardar todos los acuerdos de la tabla de acuerdos de la GUI
     * @return Booleano con el resultado de guardado, devuelve true si guard??, de lo contrario, devuelve false
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
     * M??todo que verifica si existen campos inv??lidos para un acuerdo
     * El m??todo invoca a otros m??todos de validaci??n espec??ficos
     * @return Booleano con el resultado de verificaci??n, devuelve true si existen inv??lidos, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidFieldsForAgreement(){
        boolean invalidFields = false;
        if(existsEmptyFields(tfAgreement.getText()) || existsInvalidCharacters(tfAgreement.getText()) || existsMissingSelection() ||
           existsEmptyFields(tfYear.getText()) || existsInvalidCharactersForYear(tfYear.getText()) || existsDuplicateValueForAgreement() ||
           existsInvalidLength(tfAgreement.getText())){
            invalidFields = true;
        }
        return invalidFields;
    }
    
    /**
     * M??todo que verifica si existen campos inv??lidos para minuta
     * El m??todo invoca a otros m??todos de validaci??n espec??ficos
     * @return Booleano con el resultado de verificaci??n, devuelve true si existen inv??lidos, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidFieldsForMemorandum(){
        boolean invalidFields = false;
        if(existsEmptyFields(taPendings.getText()) || existsEmptyFields(taNotes.getText()) || existsInvalidCharactersForMemorandum(taPendings.getText()) ||
           existsInvalidCharactersForMemorandum(taNotes.getText()) || existsEmptyTable() || existsInvalidLength(taPendings.getText()) || existsInvalidLength(taNotes.getText())){
            invalidFields = true;
        }
        return invalidFields;
    }
    
    /**
     * M??todo que verifica si existe campo de texto vac??o
     * @param fieldToValidate Define el texto a verificar si est?? vac??o o no
     * @return Booleano con el resultado de verificaci??n, devuelve true si est?? vac??o, de lo contrario, devuelve false
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
     * M??todo que verifica si existe selecci??n de campo faltante en la GUI
     * @return Booleano con el resultado de verificaci??n, devuelve true si existe selecci??n faltante, de lo contrario, devuelve false
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
     * M??todo que verifica si la longitud del campo excede el l??mite permitido
     * @param textToValidate Define el texto a validar
     * @return Boolean con el resultado de la verificaci??n, devuelve true si existen campos vac??os, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidLength(String textToValidate){
        boolean invalidLength = false;
        if(textToValidate.length() > 255){
            invalidLength = true;
            TypeError typeError = TypeError.INVALIDLENGTH;
            showInvalidFieldAlert(typeError);
        }
        return invalidLength;
    }
    
    /**
     * M??todo que verifica si un texto de la GUI contiene caracteres no permitidos
     * @param textToValidate Define el texto a verificar
     * @return Booleano con el resultado de verificaci??n, devuelve true si existe inv??lidos, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidCharacters(String textToValidate){
        boolean invalidCharacters = false;
        Pattern pattern = Pattern.compile("^[A-Za-z????????????????????????\\s.,:]+$");
        Matcher matcher = pattern.matcher(textToValidate);
        if(!matcher.find()){
           invalidCharacters = true; 
           TypeError typeError = TypeError.INVALIDSTRING;
           showInvalidFieldAlert(typeError);
        }
        return invalidCharacters;
    }
    
    /**
     * M??todo que verifica si existe texto de los campos de minuta que cuenten con caracteres no permitidos
     * Se implementa el m??todo porque los campos de una minuta, los cuales son notas y pendientes, pueden tener m??s variedad de tipos de caracteres a comparaci??n de los campos de acuerdo
     * @param textToValidate Define texto del campo de minuta a verificar
     * @return Booleano con el resultado de verificaci??n, devuelve true si existen inv??lidos, de los contrario, devuelve false
     */
    
    private boolean existsInvalidCharactersForMemorandum(String textToValidate){
        boolean invalidCharacters = false;
        Pattern pattern = Pattern.compile("^[A-Za-z0-9????????????????????????\\s.,:()]+$");
        Matcher matcher = pattern.matcher(textToValidate);
        if(!matcher.find()){
           invalidCharacters = true; 
           TypeError typeError = TypeError.INVALIDSTRING;
           showInvalidFieldAlert(typeError);
        }
        return invalidCharacters;
    }
    
    /**
     * M??todo que verifica si existe a??o inv??lido en la GUI
     * Se implementa el m??todo porque un a??o solo puede contener n??meros y no debe ser menor al a??o actual
     * @param yearToValidate Define el a??o recuperado de la GUI a validar
     * @return Booleano con el resultado de verificaci??n, devuelve true si existe inv??lido, de lo contario, devuelve false
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
     * M??todo que verifica si la tabla de acuerdos est?? vac??a
     * Se implementa el m??todo porque una reuni??n debe tener por lo menos un acuerdo
     * @return Booleano con el resultado de verificaci??n, devuelve true si est?? vac??a, de lo contrario, devuelve false
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
     * M??todo que limpia los campos de acuerdo
     */
    
    private void cleanFieldsAgreement(){
        tfAgreement.clear();
        cbIntegrants.getSelectionModel().clearSelection();
        cbMonth.getSelectionModel().clearSelection();
        tfYear.clear();
    }
    
    /**
     * M??todo que verifica si existen valores duplicados en un nuevo acuerdo
     * @return Booleano con el resultado de verificaci??n, devuelve true si existe duplicado, de lo contrario, devuelve false
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
     * M??todo que muestra alerta de campo inv??lido de acuerdo al tipo de error
     * @param typeError Define el tipo de error que encontr??
     */
    
    private void showInvalidFieldAlert(TypeError typeError){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Campo inv??lido");
        if(typeError == TypeError.EMPTYFIELDS){
          alert.setContentText("Existen campos vac??os, llena los campos para poder guardar");  
        }
        
        if(typeError == TypeError.MISSINGSELECTION){
            alert.setContentText("Falta seleccionar informaci??n, selecciona para poder a??adir");
        }
        
        if(typeError == TypeError.INVALIDYEAR){
            alert.setContentText("A??o inv??lido, corrobora la informaci??n");
        }
        
        if(typeError == TypeError.EMPTYTABLE){
            alert.setContentText("La reuni??n debe tener por lo menos un acuerdo, llena la tabla acuerdos para poder guardar");
        }
        
        if(typeError == TypeError.INVALIDLENGTH){
            alert.setContentText("El n??mero de car??cteres excede el l??mite permitido (255 caracteres), corrige los campos para poder guardar");
        }   
        
        if(typeError == TypeError.COLUMNMISSINGSELECTION){
            alert.setContentText("Selecci??n de columna faltante. Selecciona una columna para poder eliminar");
        }
        
        if(typeError == TypeError.INVALIDSTRING){
            alert.setContentText("Existen car??cteres inv??lidos, corrije por favor");
        }
        
        if(typeError == TypeError.DUPLICATEVALUE){
            alert.setContentText("La descripci??n del acuerdo ya se encuentra registrado, por favor verifica la informaci??n");
        }
        
        alert.showAndWait();
    }
    
    /**
     * M??todo que muestra alerta de confirmaci??n de conclusi??n de la reuni??n
     * @return Devuelve el tipo de bot??n seleccionado por el usuario
     */
    
    private Optional<ButtonType> showConfirmationAlert(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmaci??n");
        alert.setContentText("??Deseas concluir la reuni??n?");
        Optional<ButtonType> action = alert.showAndWait();
        return action;
    } 
    
    /**
     * M??todo que muestra alerta de perdida de conexi??n con la base de datos
     */
    
    private void showLostConnectionAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Perdida de conexi??n");
        alert.setContentText("Perdida de conexi??n con la base de datos, no se pudo guardar. Intente m??s tarde");
        alert.showAndWait();
    }
    
    /**
     * M??todo que muestra alerta de confirmaci??n de guardado en la base de datos
     */
    
    private void showConfirmationSaveAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmaci??n de guardado");
        alert.setContentText("La informaci??n fue guardada con ??xito");
        alert.showAndWait();
    }
}
