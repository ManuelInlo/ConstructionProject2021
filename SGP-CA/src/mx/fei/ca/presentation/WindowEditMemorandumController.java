package mx.fei.ca.presentation;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mx.fei.ca.businesslogic.AgreementDAO;
import mx.fei.ca.businesslogic.MemorandumDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Agreement;
import mx.fei.ca.domain.Memorandum;

/**
 * FXML Controller class
 *
 * @author david
 */
public class WindowEditMemorandumController implements Initializable {

    @FXML
    private TableView<Agreement> tbAgreements;
    @FXML
    private TableColumn<Agreement, String> columnAgreement;
    @FXML
    private TableColumn<Agreement, String> columnIntegrant;
    @FXML
    private TableColumn<Agreement, String> columnDate;
    @FXML
    private TextField tfAgreement;
    @FXML
    private ComboBox cbIntegrants;
    @FXML
    private ComboBox cbMonth;
    @FXML
    private TextArea taNotes;
    @FXML
    private TextArea taPendings;
    @FXML
    private TextField tfYear;
    
    private int idMemorandum;
    private ObservableList<Agreement> agreements;

    public enum TypeError{
        EMPTYFIELDS, MISSINGSELECTION, INVALIDYEAR, EMPTYTABLE, COLUMNMISSINGSELECTION, INVALIDSTRING, DUPLICATEVALUE;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnAgreement.setCellValueFactory(new PropertyValueFactory("description"));
        columnIntegrant.setCellValueFactory(new PropertyValueFactory("responsible"));
        columnDate.setCellValueFactory(new PropertyValueFactory("dateAgreement"));
        agreements = FXCollections.observableArrayList();
    }    
    
    public void fillMemorandumData(Memorandum memorandum){
        this.idMemorandum = memorandum.getIdMemorandum();
        taNotes.setText(memorandum.getNote());
        taPendings.setText(memorandum.getPending());
        //fillAgreementsTable(memorandum.getAgreements());
    }
    
    private void fillComboBoxMonth(){
        ObservableList<String> listHours = FXCollections.observableArrayList("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", 
                                                                             "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre");                                    
        cbMonth.setItems(listHours);
    }
    
    private void fillComboBoxIntegrants(){
        //Debe recuperar y llenar con los nombres de los integrantes
        ObservableList<String> listIntegrants = FXCollections.observableArrayList("Juan carlos, es prueba");
        cbIntegrants.setItems(listIntegrants);
    }
    
    private void fillAgreementsTable(ArrayList<Agreement> agreements){
        ObservableList<Agreement> listAgreements = FXCollections.observableArrayList(agreements);
        tbAgreements.setItems(listAgreements);
    }

    @FXML
    private void addAgreement(ActionEvent event) throws BusinessConnectionException{
        if(!existsInvalidFieldsForAgreement() && !existsDuplicateValueForAddAgreement()){
            AgreementDAO agreementDAO = new AgreementDAO();
            String description = tfAgreement.getText();
            String dateAgreement = cbMonth.getSelectionModel().getSelectedItem().toString() + "-" + tfYear.getText();
            String responsible = cbIntegrants.getSelectionModel().getSelectedItem().toString();
            Agreement agreement = new Agreement(description, dateAgreement, responsible);
            boolean savedAgreement = agreementDAO.savedAgreement(agreement, idMemorandum);
            if(savedAgreement){
                agreements.add(agreement);
                cleanFieldsAgreement();
            }else{
                showLostConnectionAlert();
            }
        }
    }

    @FXML
    private void deleteAgreement(ActionEvent event) throws BusinessConnectionException{
        Agreement agreement = tbAgreements.getSelectionModel().getSelectedItem();
        if(agreement == null){
            TypeError typeError = TypeError.COLUMNMISSINGSELECTION;
            showInvalidFieldAlert(typeError);
        }else{
            AgreementDAO agreementDAO = new AgreementDAO();
            boolean deletedAgreement = agreementDAO.deletedAgreementById(agreement.getIdAgreement());
            if(deletedAgreement){
                agreements.remove(agreement);
                tbAgreements.refresh();
            }else{
                showLostConnectionAlert();
            }
        }
    }
    
    @FXML
    private void fillAgreementFields(MouseEvent event){
        Agreement agreement = tbAgreements.getSelectionModel().getSelectedItem();
        if(agreement != null){
            tfAgreement.setText(agreement.getDateAgreement());
            cbIntegrants.getSelectionModel().select(agreement.getResponsible());
            cbMonth.getSelectionModel().select(takeMonth(agreement.getDateAgreement()));
            tfYear.setText(takeYear(agreement.getDateAgreement()));
        }
    }
    
    @FXML
    private void updateAgreement(ActionEvent event) throws BusinessConnectionException{
        Agreement agreement = tbAgreements.getSelectionModel().getSelectedItem();
        if(agreement == null){
            TypeError typeError = TypeError.COLUMNMISSINGSELECTION;
            showInvalidFieldAlert(typeError);
        }else if(!existsInvalidFieldsForAgreement() && !existsDuplicateValueForUpdateAgreement(agreement.getIdAgreement())){
            String description = tfAgreement.getText();
            String dateAgreement = cbMonth.getSelectionModel().getSelectedItem().toString() + "-" + tfYear.getText();
            String responsible = cbIntegrants.getSelectionModel().getSelectedItem().toString();
            Agreement modifiedAgreement = new Agreement(description, dateAgreement, responsible);
            AgreementDAO agreementDAO = new AgreementDAO();
            boolean updatedAgreement = agreementDAO.updatedAgreement(modifiedAgreement, agreement.getIdAgreement(), idMemorandum);
            if(updatedAgreement){
                agreement.setDateAgreement(modifiedAgreement.getDateAgreement());
                agreement.setDescription(modifiedAgreement.getDescription());
                agreement.setResponsible(modifiedAgreement.getResponsible());
                tbAgreements.refresh();
                cleanFieldsAgreement();
            }else{
                showLostConnectionAlert();
            }
            
        }
    }
    
    @FXML
    private void editMemorandum(ActionEvent event){
        if(!existsInvalidFieldsForMemorandum()){
            String pending = taPendings.getText();
            String note = taNotes.getText();
            Memorandum memorandum = new Memorandum(pending, note, "Por aprobar");
            MemorandumDAO memorandumDAO = new MemorandumDAO();
            boolean updatedMemorandum;
            try {
                updatedMemorandum = memorandumDAO.updatedMemorandum(memorandum, idMemorandum, idMemorandum);
                if(updatedMemorandum){
                    showConfirmationSaveAlert();
                    closeEditMemorandum(event);
                }
            } catch (BusinessConnectionException ex) {
                showLostConnectionAlert();
            }
        }
    }
    
    @FXML
    private void closeEditMemorandum(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    private String takeMonth(String dateToSeparate){
        String[] partsDate = dateToSeparate.split("-");
        String agreementMonth = partsDate[0];
        return agreementMonth;
    }
    
    private String takeYear(String dateToSeparate){
        String[] partsDate = dateToSeparate.split("-");
        String agreementYear = partsDate[1];
        return agreementYear;
    }
    
    private boolean existsInvalidFieldsForAgreement(){
        boolean invalidFields = false;
        if(existsEmptyFields(tfAgreement.getText()) || existsInvalidCharacters(tfAgreement.getText()) || existsMissingSelection() ||
           existsEmptyFields(tfYear.getText()) || existsInvalidCharactersForYear(tfYear.getText())){
            invalidFields = true;
        }
        return invalidFields;
    }
    
    private boolean existsInvalidFieldsForMemorandum(){
        boolean invalidFields = false;
        if(existsEmptyFields(taPendings.getText()) || existsEmptyFields(taNotes.getText()) || existsInvalidCharactersForMemorandum(taPendings.getText()) ||
           existsInvalidCharactersForMemorandum(taNotes.getText()) || existsEmptyTable()){
            invalidFields = true;
        }
        return invalidFields;
    }
    
    private boolean existsEmptyFields(String fieldToValidate){
        boolean emptyField = false;
        if(fieldToValidate.isEmpty()){
            emptyField = true;
            TypeError typeError = TypeError.EMPTYFIELDS;
            showInvalidFieldAlert(typeError);
        }
        return emptyField;
    }
    
    private boolean existsMissingSelection(){
        boolean missingSelection = false;
        if(cbMonth.getSelectionModel().getSelectedItem().equals("")|| cbIntegrants.getSelectionModel().getSelectedItem().equals("")){ //Cambiar acá debe ser a null porque selecciona integrantes
            missingSelection = true;
            TypeError typeError = TypeError.MISSINGSELECTION;
            showInvalidFieldAlert(typeError);
        }
        return missingSelection;
    }
    
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
        
    private boolean existsDuplicateValueForAddAgreement(){
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
    
    private boolean existsDuplicateValueForUpdateAgreement(int idAgreement){
        boolean duplicateValue = false;
        for(Agreement agreement: tbAgreements.getItems()){
            if(agreement.getIdAgreement() != idAgreement && agreement.getDescription().equals(tfAgreement.getText())){
                duplicateValue = true;
                TypeError typeError = TypeError.DUPLICATEVALUE;
                showInvalidFieldAlert(typeError);
            }
        }
        return duplicateValue;
    }
    
    private void cleanFieldsAgreement(){
        tfAgreement.clear();
        cbIntegrants.getSelectionModel().clearSelection();
        cbMonth.getSelectionModel().clearSelection();
        tfYear.clear();
    }
    
    private void showInvalidFieldAlert(TypeError typeError){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Campo inválido");
        if(typeError == TypeError.EMPTYFIELDS){
          alert.setContentText("Existen campos vacíos, llena los campos para poder modificar");  
        }
        
        if(typeError == TypeError.MISSINGSELECTION){
            alert.setContentText("Falta seleccionar información, selecciona para poder añadir");
        }
        
        if(typeError == TypeError.INVALIDYEAR){
            alert.setContentText("Año inválido, corrobora la información");
        }
        
        if(typeError == TypeError.EMPTYTABLE){
            alert.setContentText("La reunión debe tener por lo menos un acuerdo, llena la tabla acuerdos para poder modificar");
        }
        
        if(typeError == TypeError.COLUMNMISSINGSELECTION){
            alert.setContentText("Selección de columna faltante. Selecciona una columna para poder eliminar o modificar");
        }
        
        if(typeError == TypeError.INVALIDSTRING){
            alert.setContentText("Existen carácteres inválidos, corrije por favor");
        }
        
        if(typeError == TypeError.DUPLICATEVALUE){
            alert.setContentText("La descripción del acuerdo ya se encuentra registrado, por favor verifica la información");
        }
        
        alert.showAndWait();
    }
    
    private void showLostConnectionAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Perdida de conexión");
        alert.setContentText("Perdida de conexión con la base de datos, no se pudo guardar. Intente más tarde");
        alert.showAndWait();
    }
    
    private void showConfirmationSaveAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación de guardado");
        alert.setContentText("La información fue guardada con éxito");
        alert.showAndWait();
    }

}
