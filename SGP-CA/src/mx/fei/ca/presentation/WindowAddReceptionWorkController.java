/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.fei.ca.presentation;

import java.net.URL;
import java.sql.Date;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.fei.ca.businesslogic.CollaboratorDAO;
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
    private ComboBox cbInvestigationProject;
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
    @FXML
    private Button btnCancel;
    
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
        fillComboBoxInvestigationProject();
    }  
    
    private void fillComboBoxImpactCA(){
        ObservableList<String> listImpactCA = FXCollections.observableArrayList("SI","NO");
        cbImpactCA.setItems(listImpactCA);
    }
    
    private void fillComboBoxGrade(){
        ObservableList<String> listImpactCA = FXCollections.observableArrayList("Licenciatura");
        cbGrade.setItems(listImpactCA);
    }
    
    private void fillComboBoxActualState(){
        ObservableList<String> listActualState = FXCollections.observableArrayList("En proceso","Terminado");
        cbActualState.setItems(listActualState);
    }
    
    private void fillComboBoxType(){
        ObservableList<String> listType = FXCollections.observableArrayList("Tesis","Monografía");
        cbType.setItems(listType);
        
    }
    
    private void fillComboBoxPositionAuthor(){
        ObservableList<String> listPositionAuthor = FXCollections.observableArrayList("Estudiante");
        cbPositionAuthor.setItems(listPositionAuthor);
    }
    
    private void fillComboBoxInvestigationProject(){
        //En realidad debe mandar a recuperar los proyectos de la base, esta es una prueba
        ObservableList<String> listInvestigationProject = FXCollections.observableArrayList("Inteligencia artificial");
        cbInvestigationProject.setItems(listInvestigationProject);
    }

    @FXML
    public void saveReceptionWork(ActionEvent event) throws BusinessConnectionException {
        if(!existMissingSelection() && !existsEmptyFields() && !existsInvalidStrings() && !existInvalidDates()){
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
            String nameInvestigationProject = cbInvestigationProject.getSelectionModel().getSelectedItem().toString();
            if(cbActualState.getSelectionModel().getSelectedItem().toString().equals("Terminado")){
               endDate = parseToSqlDate(java.util.Date.from(dpEndDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())); 
            }
            
            Collaborator collaborator = new Collaborator(nameAuthor, positionAuthor);
            InvestigationProject investigationProject = new InvestigationProject();
            investigationProject.setName(nameInvestigationProject);  //Debe mandar a llamar al buscar por nombre
            investigationProject.setIdProject(1);
            ReceptionWork receptionWork = new ReceptionWork(impactCA, titleReceptionWork, fileRoute, startDate, endDate, grade,
                                                        workType, actualState);
            Integrant integrant = new Integrant("JCPA940514RDTREOP1"); //Es prueba, deberia recuperar la curp del que está loggeado
            if(!existsDuplicateValues(receptionWork, collaborator)){
                ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
                CollaboratorDAO collaboratorDAO = new CollaboratorDAO();
                int idCollaborator = collaboratorDAO.saveCollaboratorAndReturnId(collaborator);
                collaborator.setIdCollaborator(idCollaborator);
                receptionWork.setCollaborator(collaborator);
                receptionWork.setInvestigationProject(investigationProject);
                receptionWork.setIntegrant(integrant);
                boolean saveResult = receptionWorkDAO.savedReceptionWork(receptionWork);
                showConfirmationAlert(saveResult);
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
    
    private boolean existsEmptyFields(){
        boolean emptyFields = false;
        if(tfTitleReceptionWork.getText().isEmpty() || tfFileRoute.getText().isEmpty() || tfAuthor.getText().isEmpty()){
            emptyFields = true;
            TypeError typeError = TypeError.EMPTYFIELD;
            showInvalidFieldAlert(typeError);
        }
        return emptyFields;
    }
    
    private boolean existsInvalidStrings(){
        boolean invalidStrings = false;
        if(existsInvalidCharacters(tfTitleReceptionWork.getText()) || existsInvalidCharacters(tfAuthor.getText())){
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
    private boolean existMissingSelection(){
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
    
    private boolean existInvalidDates(){
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
    
    private boolean existsDuplicateValues(ReceptionWork receptionWork, Collaborator collaborator) throws BusinessConnectionException{
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        CollaboratorDAO collaboratorDAO = new CollaboratorDAO();
        boolean duplicateValues = false;
        boolean receptionWorkTitleDuplicate = false;
        boolean fileRouteDuplicate = false;
        boolean collaboratorNameDuplicate = false;
        if(receptionWorkDAO.existsReceptionWorkTitle(receptionWork.getTitleReceptionWork())){
            receptionWorkTitleDuplicate = true;
            TypeError typeError = TypeError.TITLEDUPLICATE;
            showInvalidFieldAlert(typeError);
        }
           
        if(receptionWorkDAO.existsReceptionWorkFileRoute(receptionWork.getFileRoute())){
            fileRouteDuplicate = true;
            TypeError typeError = TypeError.FILEROUTEDUPLICATE;
            showInvalidFieldAlert(typeError);
        }
        
        if(collaboratorDAO.existsCollaboratorName(collaborator.getName())){
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
            alert.setContentText("El trabajo recepcional está en proceso, no puedes guardar una fecha de fin");
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
        }
        
    }

}
