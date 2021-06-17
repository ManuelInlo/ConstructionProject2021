package mx.fei.ca.presentation;

import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mx.fei.ca.businesslogic.AgreementDAO;
import mx.fei.ca.businesslogic.IntegrantDAO;
import mx.fei.ca.businesslogic.MemorandumDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Agreement;
import mx.fei.ca.domain.Integrant;
import mx.fei.ca.domain.Memorandum;

/**
 * Clase para representar el controlador del FXML WindowEditMemorandum
 * @author David Alexander Mijangos Paredes
 * @version 17-06-2021
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
    @FXML
    private Label lbUser;
    private int idMemorandum;
    private ObservableList<Agreement> listAgreements;
    
    /**
     * Enumerado que representa los tipos de errores específicos al editar una minuta
     */
    
    public enum TypeError{
        EMPTYFIELDS, MISSINGSELECTION, INVALIDYEAR, EMPTYTABLE, COLUMNMISSINGSELECTION, INVALIDSTRING, DUPLICATEVALUE;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillComboBoxMonth();
        fillComboBoxIntegrants();
        columnAgreement.setCellValueFactory(new PropertyValueFactory("description"));
        columnIntegrant.setCellValueFactory(new PropertyValueFactory("responsible"));
        columnDate.setCellValueFactory(new PropertyValueFactory("dateAgreement"));
        listAgreements = FXCollections.observableArrayList();
    }  
    
    /**
     * Método que establece el integrante loggeado, permitiendo proyectar su nombre en la GUI
     * @param integrant Define el integrante a proyectar su nombre
     */
    
    public void setIntegrant(Integrant integrant){
        lbUser.setText(integrant.getNameIntegrant());
    }
    
    /**
     * Método para llenar la información de la minuta en la GUI
     * @param memorandum Define la minuta de la cual se quiere mostrar la información
     */
    
    public void fillMemorandumData(Memorandum memorandum){
        this.idMemorandum = memorandum.getIdMemorandum();
        taNotes.setText(memorandum.getNote());
        taPendings.setText(memorandum.getPending());
        fillAgreementsTable(memorandum.getAgreements());
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
     * Método que llena la tabla de acuerdos de la minuta 
     * @param agreements Define la lista de acuerdos correspondientes a la minuta
     */
    
    private void fillAgreementsTable(ArrayList<Agreement> agreements){
        listAgreements = FXCollections.observableArrayList(agreements);
        tbAgreements.setItems(listAgreements);
    }
    
    /**
     * Método que manda a agregar un nuevo acuerdo de minuta a la base de datos y la muestra en la tabla de acuerdos de la GUI
     * @param event Define el evento generado
     * @throws BusinessConnectionException 
     */

    @FXML
    private void addAgreement(ActionEvent event) throws BusinessConnectionException{
        if(!existsInvalidFieldsForAgreement() && !existsDuplicateValueForAddAgreement()){
            AgreementDAO agreementDAO = new AgreementDAO();
            String description = tfAgreement.getText();
            String dateAgreement = cbMonth.getSelectionModel().getSelectedItem().toString() + "-" + tfYear.getText();
            String responsible = cbIntegrants.getSelectionModel().getSelectedItem().toString();
            Agreement agreement = new Agreement(description, dateAgreement, responsible);
            boolean savedAgreement = agreementDAO.savedAgreement(agreement, idMemorandum);
            int idAgreement = agreementDAO.getIdAgreementByDescription(agreement.getDescription());
            if(savedAgreement && idAgreement != 0){
                agreement.setIdAgreement(idAgreement);
                listAgreements.add(agreement);
                tbAgreements.setItems(listAgreements);
                cleanFieldsAgreement();
            }else{
                showLostConnectionAlert();
            }
        }
    }
    
    /**
     * Método que manda a eliminar un acuerdo de minuta de la base de datos y la elimina de la tabla de acuerdos de la GUI
     * @param event Define el evento generado
     * @throws BusinessConnectionException 
     */

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
                listAgreements.remove(agreement);
                tbAgreements.refresh();
            }else{
                showLostConnectionAlert();
            }
        }
        cleanFieldsAgreement();
    }
    
    /**
     * Método que llena los campos de acuerdo de un acuerdo seleccionado de la tabla
     * @param event Define el evento generado
     */
    
    @FXML
    private void fillAgreementFields(MouseEvent event){
        Agreement agreement = tbAgreements.getSelectionModel().getSelectedItem();
        if(agreement != null){
            tfAgreement.setText(agreement.getDescription());
            cbIntegrants.getSelectionModel().select(agreement.getResponsible());
            cbMonth.getSelectionModel().select(takeMonth(agreement.getDateAgreement()));
            tfYear.setText(takeYear(agreement.getDateAgreement()));
        }
    }
    
    /**
     * Método que manda a modificar un acuerdo en la base de datos y lo muestra modificado en la tabla de acuerdos de la GUI
     * @param event Define el evento generado
     * @throws BusinessConnectionException 
     */
    
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
                
            }else{
                showLostConnectionAlert();
            } 
            cleanFieldsAgreement();
        }
    }
    
    /**
     * Método que manda a modificar los campos de la minuta a la base de datos
     * @param event Define el evento generado
     */
    
    @FXML
    private void editMemorandum(ActionEvent event){
        if(!existsInvalidFieldsForMemorandum()){
            String pending = taPendings.getText();
            String note = taNotes.getText();
            Memorandum memorandum = new Memorandum(pending, note);
            MemorandumDAO memorandumDAO = new MemorandumDAO();
            boolean updatedMemorandum;
            try {
                updatedMemorandum = memorandumDAO.updatedMemorandum(memorandum, idMemorandum);
                if(updatedMemorandum){
                    showConfirmationSaveAlert();
                    closeEditMemorandum(event);
                }
            } catch (BusinessConnectionException ex) {
                showLostConnectionAlert();
            }
        }
    }
    
    /**
     * Método que cierra la ventana actual "Editar minuta"
     * @param event Define el evento generado
     */
    
    @FXML
    private void closeEditMemorandum(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Método que toma el mes de una fecha obtenida de la GUI
     * @param dateToSeparate Define la fecha a separar su mes
     * @return String con el mes de la fecha
     */
    
    private String takeMonth(String dateToSeparate){
        String[] partsDate = dateToSeparate.split("-");
        String agreementMonth = partsDate[0];
        return agreementMonth;
    }
    
    /**
     * Método que toma el año de una fecha obtenida de la GUI
     * @param dateToSeparate Define la fecha a separar su año
     * @return String con el año de la fecha
     */
    
    private String takeYear(String dateToSeparate){
        String[] partsDate = dateToSeparate.split("-");
        String agreementYear = partsDate[1];
        return agreementYear;
    }
    
    /**
     * Método que verifica si existen campos inválidos para un acuerdo
     * El método invoca a otros métodos de validación específicos
     * @return Booleano con el resultado de verificación, devuelve true si existen inválidos, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidFieldsForAgreement(){
        boolean invalidFields = false;
        if(existsEmptyFields(tfAgreement.getText()) || existsInvalidCharacters(tfAgreement.getText()) || existsMissingSelection() ||
           existsEmptyFields(tfYear.getText()) || existsInvalidCharactersForYear(tfYear.getText())){
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
        if(cbMonth.getSelectionModel().getSelectedItem().equals("")|| cbIntegrants.getSelectionModel().getSelectedItem().equals("")){ //Cambiar acá debe ser a null porque selecciona integrantes
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
        ObservableList<Agreement> agreements = tbAgreements.getItems();
        if(agreements.isEmpty()){
            emptyTable = true;
            TypeError typeError = TypeError.EMPTYTABLE;
            showInvalidFieldAlert(typeError);
        }
        return emptyTable;
    }
    
    /**
     * Método que verifica si existen valores duplicados en un nuevo acuerdo a agregar
     * @return Booleano con el resultado de verificación, devuelve true si existe duplicado, de lo contrario, devuelve false
     */
        
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
    
    /**
     * Método que verifica si existen valores duplicados en un acuerdo a modificar
     * Se implementa el método porque verifica con todos los acuerdos de la tabla excepto con el acuerdo a modificar
     * @param idAgreement Define el identificador del acuerdo a modificar
     * @return Booleano con el resultado de verificación, devuelve true si existe duplicado, de lo contrario, devuelve false
     */
    
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
     * Método que muestra alerta de campo inválido de acuerdo al tipo de error
     * @param typeError Define el tipo de error que encontró
     */
    
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
