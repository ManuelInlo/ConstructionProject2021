
package mx.fei.ca.presentation;

import java.net.URL;
import java.sql.Date;
import java.time.ZoneId;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.fei.ca.businesslogic.CollaboratorDAO;
import mx.fei.ca.businesslogic.IntegrantDAO;
import mx.fei.ca.businesslogic.InvestigationProjectDAO;
import mx.fei.ca.businesslogic.ReceptionWorkDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Collaborator;
import mx.fei.ca.domain.Integrant;
import mx.fei.ca.domain.InvestigationProject;
import mx.fei.ca.domain.ReceptionWork;

/**
 * FXML Controller class
 *
 * @author david
 */
public class WindowAddReceptionWorkController implements Initializable {

    @FXML
    private TextField tfTitleReceptionWork;
    @FXML
    private TextField tfFileRoute;
    @FXML
    private TextField tfAuthor;
    @FXML
    private ComboBox cbImpactCA;
    @FXML
    private ComboBox<InvestigationProject> cbInvestigationProject;
    @FXML
    private ComboBox cbType;
    @FXML
    private ComboBox cbActualState;
    @FXML
    private DatePicker dpStartDate;
    @FXML
    private DatePicker dpEndDate;
    @FXML
    private ComboBox cbGrade;
    @FXML
    private ComboBox cbPositionAuthor;
    
    
    private enum TypeError{
        EMPTYFIELD, INVALIDSTRING, MISSINGSELECTION, MISSINGDATE, OVERDATE, INCONSISTENTDATE, TITLEDUPLICATE, 
        FILEROUTEDUPLICATE, COLLABORATORDUPLICATE;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillComboBoxImpactCA();
        fillComboBoxGrade();
        fillComboBoxActualState();
        fillComboBoxType();
        fillComboBoxPositionAuthor();
        try {
            fillComboBoxInvestigationProject();
        } catch (BusinessConnectionException ex) {
            showLostConnectionAlert();
        }
    }  
    
    @FXML
    private void fillComboBoxImpactCA(){
        ObservableList<String> listImpactCA = FXCollections.observableArrayList("SI","NO");
        cbImpactCA.setItems(listImpactCA);
    }
    
    @FXML
    private void fillComboBoxGrade(){
        ObservableList<String> listImpactCA = FXCollections.observableArrayList("Licenciatura");
        cbGrade.setItems(listImpactCA);
    }
    
    @FXML
    private void fillComboBoxActualState(){
        ObservableList<String> listActualState = FXCollections.observableArrayList("En proceso","Terminado");
        cbActualState.setItems(listActualState);
    }
    
    @FXML
    private void fillComboBoxType(){
        ObservableList<String> listType = FXCollections.observableArrayList("Tesis","Monografía", "Práctico");
        cbType.setItems(listType);
        
    }
    
    @FXML
    private void fillComboBoxPositionAuthor(){
        ObservableList<String> listPositionAuthor = FXCollections.observableArrayList("Estudiante");
        cbPositionAuthor.setItems(listPositionAuthor);
    }
    
    @FXML
    private void fillComboBoxInvestigationProject() throws BusinessConnectionException{
        InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
        ArrayList<InvestigationProject> investigationProjects = investigationProjectDAO.findAllInvestigationProjects();
        ObservableList<InvestigationProject> listInvestigationProject = FXCollections.observableArrayList(investigationProjects);
        cbInvestigationProject.setItems(listInvestigationProject);
    }

    @FXML
    public void saveReceptionWork(ActionEvent event) throws BusinessConnectionException {
        if(!existsInvalidFields()){
            Date endDate = null;
            String impactCA = cbImpactCA.getSelectionModel().getSelectedItem().toString();
            String titleReceptionWork = tfTitleReceptionWork.getText();
            String fileRoute = tfFileRoute.getText();
            Date startDate = parseToSqlDate(java.util.Date.from(dpStartDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            String grade = cbGrade.getSelectionModel().getSelectedItem().toString();
            String workType = cbType.getSelectionModel().getSelectedItem().toString();
            String actualState = cbActualState.getSelectionModel().getSelectedItem().toString();
            String nameAuthor = tfAuthor.getText();
            String positionAuthor = cbPositionAuthor.getSelectionModel().getSelectedItem().toString();
            InvestigationProject investigationProject = cbInvestigationProject.getSelectionModel().getSelectedItem();
            if(cbActualState.getSelectionModel().getSelectedItem().toString().equals("Terminado")){
               endDate = parseToSqlDate(java.util.Date.from(dpEndDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())); 
            }
            
            Collaborator collaborator = new Collaborator(nameAuthor, positionAuthor);
            CollaboratorDAO collaboratorDAO = new CollaboratorDAO();
            int idCollaborator = collaboratorDAO.saveCollaboratorAndReturnId(collaborator);
            if(idCollaborator != 0){
                collaborator.setIdCollaborator(idCollaborator);
                ReceptionWork receptionWork = new ReceptionWork(impactCA, titleReceptionWork, fileRoute, startDate, endDate, grade,
                                                        workType, actualState);
                receptionWork.setCollaborator(collaborator);
                receptionWork.setInvestigationProject(investigationProject);
                IntegrantDAO integrantDAO = new IntegrantDAO();
                Integrant integrant = integrantDAO.findIntegrantByCurp("JCPA940514RDTREOP1"); //La curp es de prueba, checar como recuperar la del loggeado
                receptionWork.setIntegrant(integrant);
                ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
                boolean saveResult = receptionWorkDAO.savedReceptionWork(receptionWork);
                showConfirmationAlert(saveResult);
            }else{
                showLostConnectionAlert();
            }
        }
    }
    
    @FXML
    private void closeReceptionWorkRegistration(ActionEvent event){
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
        }else if(existsDuplicateValues()){
            invalidFields = true;
        }
        return invalidFields;
    }
    
    @FXML
    private boolean existsEmptyFields(){
        boolean emptyFields = false;
        if(tfTitleReceptionWork.getText().isEmpty() || tfFileRoute.getText().isEmpty() || tfAuthor.getText().isEmpty()){
            emptyFields = true;
            TypeError typeError = TypeError.EMPTYFIELD;
            showInvalidFieldAlert(typeError);
        }
        return emptyFields;
    }
    
    @FXML
    private boolean existsInvalidStrings(){
        boolean invalidStrings = false;
        if(existsInvalidCharactersForTitle(tfTitleReceptionWork.getText()) || existsInvalidCharactersForAuthor(tfAuthor.getText())){
            invalidStrings = true;
            TypeError typeError = TypeError.INVALIDSTRING;
            showInvalidFieldAlert(typeError);
        }
        return invalidStrings;
    }
    
    private boolean existsInvalidCharactersForAuthor(String textToValidate){
        boolean invalidCharacters = false;
        Pattern pattern = Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúñÑ\\s]+$");
        Matcher matcher = pattern.matcher(textToValidate);
        if(!matcher.find()){
           invalidCharacters = true; 
        }
        return invalidCharacters;
    }
    
    private boolean existsInvalidCharactersForTitle(String textToValidate){
        boolean invalidCharacters = false;
        Pattern pattern = Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúñÑ\\s\\.,:]+$");
        Matcher matcher = pattern.matcher(textToValidate);
        if(!matcher.find()){
           invalidCharacters = true; 
        }
        return invalidCharacters;
    }
    
    private boolean existsMissingSelection(){
        boolean missingSelection = false;
        if(cbImpactCA.getSelectionModel().getSelectedIndex() < 0 || cbInvestigationProject.getSelectionModel().getSelectedIndex() < 0 ||
           cbType.getSelectionModel().getSelectedIndex() < 0 || cbActualState.getSelectionModel().getSelectedIndex() < 0 ||
           cbGrade.getSelectionModel().getSelectedIndex() < 0 || cbPositionAuthor.getSelectionModel().getSelectedIndex() < 0){
            missingSelection = true;
            TypeError typeError = TypeError.MISSINGSELECTION;
            showInvalidFieldAlert(typeError);
        }
        return missingSelection;
    }
    
    private boolean existsInvalidDates(){
        boolean invalidDates = false;
        if(cbActualState.getSelectionModel().getSelectedItem().toString().equals("Terminado")){
            if(existMissingDate(dpStartDate) || existMissingDate(dpEndDate) || existInconsistentDates()){
                invalidDates = true;
            }
        }else{
            if(existMissingDate(dpStartDate) || existLeftOverDateSelection(dpEndDate)){
                invalidDates = true;
            }
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
    
    private boolean existLeftOverDateSelection(DatePicker datePicker){
        boolean selection = false;
        if(datePicker.getValue() != null){
            selection = true;
            TypeError typeError = TypeError.OVERDATE;
            showInvalidFieldAlert(typeError);
        }
        return selection;
    }
    
    private boolean existInconsistentDates(){
        boolean inconsistentDates = false;
        int startDay = dpStartDate.getValue().getDayOfMonth();
        int startMonth = dpStartDate.getValue().getMonthValue();
        int startYear = dpStartDate.getValue().getYear();
        int endDay = dpEndDate.getValue().getDayOfMonth();
        int endMonth = dpEndDate.getValue().getMonthValue();
        int endYear = dpEndDate.getValue().getYear();
        if(startDay > endDay && startMonth >= endMonth && startYear >= endYear){
            inconsistentDates = true;
            TypeError typeError = TypeError.INCONSISTENTDATE;
            showInvalidFieldAlert(typeError);
        }
        return inconsistentDates;
    }
    
    @FXML
    private boolean existsDuplicateValues() throws BusinessConnectionException{
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        CollaboratorDAO collaboratorDAO = new CollaboratorDAO();
        boolean duplicateValues = false;
        boolean receptionWorkTitleDuplicate = false;
        boolean fileRouteDuplicate = false;
        boolean collaboratorNameDuplicate = false;
        if(receptionWorkDAO.existsReceptionWorkTitle(tfTitleReceptionWork.getText())){  //DEBE HACER LO MISMO CON LAS OTRAS EVIDENCIAS
            receptionWorkTitleDuplicate = true;
            TypeError typeError = TypeError.TITLEDUPLICATE;
            showInvalidFieldAlert(typeError);
        }
           
        if(receptionWorkDAO.existsReceptionWorkFileRoute(tfFileRoute.getText())){
            fileRouteDuplicate = true;
            TypeError typeError = TypeError.FILEROUTEDUPLICATE;
            showInvalidFieldAlert(typeError);
        }
        
        if(collaboratorDAO.existsCollaboratorName(tfAuthor.getText())){
            collaboratorNameDuplicate = true;
            TypeError typeError = TypeError.COLLABORATORDUPLICATE;
            showInvalidFieldAlert(typeError);
        }
        
        if(receptionWorkTitleDuplicate || fileRouteDuplicate || collaboratorNameDuplicate){
            duplicateValues = true;
        }
        
        return duplicateValues;
    }
    
    @FXML 
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
        
        if(typeError == TypeError.MISSINGDATE){
            alert.setContentText("Existe fecha faltante, selecciona las fechas para poder guardar");
        }
        
        if(typeError == TypeError.INCONSISTENTDATE){
            alert.setContentText("La fecha de inicio es mayor a la fecha de fin, corrige campos para poder guardar");
        }
        
        if(typeError == TypeError.OVERDATE){
            alert.setContentText("El trabajo recepcional no está terminado, por lo tanto no puedes guardar una fecha de fin");
        }
        
        if(typeError == TypeError.TITLEDUPLICATE){
            alert.setContentText("El título del trabajo recepcional ya se encuentra registrado en el sistema");
        }
        
        if(typeError == TypeError.FILEROUTEDUPLICATE){
            alert.setContentText("La ruta de archivo del trabajo recepcional ya se encuentra registrado en otro trabajo recepcional");
        }

        if(typeError == TypeError.COLLABORATORDUPLICATE){
            alert.setContentText("El estudiante ya cuenta con un trabajo recepcional registrado en el sistema");
        }
        alert.showAndWait();    
    }
    
    @FXML
    private void showConfirmationAlert(boolean saveResult){
        if(saveResult){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Confirmación de guardado");
            alert.setContentText("La información fue guardada con éxito");
            alert.showAndWait();
        }else{
            showLostConnectionAlert();
        }
        
    }
    
    @FXML
    private void showLostConnectionAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Perdida de conexión");
        alert.setContentText("Perdida de conexión con la base de datos, no se pudo guardar. Intente más tarde");
        alert.showAndWait();
    }

}
