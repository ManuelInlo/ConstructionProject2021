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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
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
            String impactCA = cbImpactCA.getSelectionModel().getSelectedItem().toString();
            String titleReceptionWork = tfTitleReceptionWork.getText();
            String fileRoute = tfFileRoute.getText();
            Date startDate = parseToSqlDate(java.util.Date.from(dpStartDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            Date endDate = parseToSqlDate(java.util.Date.from(dpEndDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            String grade = cbGrade.getSelectionModel().getSelectedItem().toString();
            String workType = cbType.getSelectionModel().getSelectedItem().toString();
            String actualState = cbActualState.getSelectionModel().getSelectedItem().toString();
            String nameAuthor = tfAuthor.getText();
            String positionAuthor = cbPositionAuthor.getSelectionModel().getSelectedItem().toString();
            String nameInvestigationProject = cbInvestigationProject.getSelectionModel().getSelectedItem().toString();
            
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
    
    private java.sql.Date parseToSqlDate(java.util.Date date){
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        return sqlDate;
    }
    
    @FXML
    private boolean existsEmptyFields(){
        boolean emptyFields = false;
        if(tfTitleReceptionWork.getText().isEmpty() || tfFileRoute.getText().isEmpty() || tfAuthor.getText().isEmpty()){
            emptyFields = true;
            showEmptyFieldsAlert();
        }
        return emptyFields;
    }
    
    private boolean existsInvalidStrings(){
        boolean invalidStrings = false;
        if(existsInvalidCharacters(tfTitleReceptionWork.getText()) || existsInvalidCharacters(tfAuthor.getText())){
            invalidStrings = true;
            showInvalidStringsAlert();
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
            showMissingSelectionAlert();
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
            if(existMissingDate(dpStartDate) || !existMissingDate(dpEndDate)){
                invalidDates = true;
                showOverDateAlert();
            }
        }
        return invalidDates;
    }
    
    private boolean existMissingDate(DatePicker datePicker){
        boolean missingDate = false;
        if(datePicker.getValue() == null){
            missingDate = true;
            showMissingDateAlert();
        }
        return missingDate;
    }
    
    @FXML
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
            showInconsistentDatesAlert();
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
            showTitleReceptionWorkDuplicateAlert();
        }
           
        if(receptionWorkDAO.existsReceptionWorkFileRoute(receptionWork.getFileRoute())){
            fileRouteDuplicate = true;
            showFileRouteDuplicateAlert();
        }
        
        if(collaboratorDAO.existsCollaboratorName(collaborator.getName())){
            collaboratorNameDuplicate = true;
            showCollaboratorNameDuplicateAlert();
        }
        
        if(receptionWorkTitleDuplicate || fileRouteDuplicate || collaboratorNameDuplicate){
            duplicateValues = true;
        }
        
        return duplicateValues;
    }
    
    @FXML
    private void showEmptyFieldsAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Campo inválido");
        alert.setContentText("Existen campos vacíos, llena los campos para poder guardar");
        alert.showAndWait();
    }
    
    @FXML
    private void showMissingSelectionAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Campo inválido");
        alert.setContentText("Existe selección de campo faltante, selecciona los campos para poder guardar");
        alert.showAndWait();
    }
    
    @FXML
    private void showInvalidStringsAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Campo inválido");
        alert.setContentText("Existen caracteres inválidos, revisa los textos para poder guardar");
        alert.showAndWait();
    }
    
    @FXML
    private void showMissingDateAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Campo inválido");
        alert.setContentText("Existe fecha faltante, selecciona las fechas para poder guardar");
        alert.showAndWait();
    }
    
    @FXML
    private void showInconsistentDatesAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Campo inválido");
        alert.setContentText("La fecha de inicio es mayor a la fecha de fin, corrige campos para poder guardar");
        alert.showAndWait();
    }
    
    @FXML
    private void showOverDateAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Campo inválido");
        alert.setContentText("El trabajo recepcional está en proceso, no puedes guardar una fecha de fin");
        alert.showAndWait();
    }
    
    @FXML
    private void showTitleReceptionWorkDuplicateAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Campo inválido");
        alert.setContentText("El título del trabajo recepcional ya se encuentra registrado en el sistema");
        alert.showAndWait();
    }
    
    @FXML
    private void showFileRouteDuplicateAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Campo inválido");
        alert.setContentText("La ruta de archivo del trabajo recepcional ya se encuentra registrado en otro trabajo recepcional");
        alert.showAndWait();
    }
    
    @FXML
    private void showCollaboratorNameDuplicateAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Campo inválido");
        alert.setContentText("El estudiante ya cuenta con un trabajo recepcional registrado en el sistema");
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
