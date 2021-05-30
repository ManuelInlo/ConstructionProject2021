/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.fei.ca.presentation;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mx.fei.ca.businesslogic.AgendaPointDAO;
import mx.fei.ca.businesslogic.MeetingDAO;
import mx.fei.ca.businesslogic.PrerequisiteDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.AgendaPoint;
import mx.fei.ca.domain.Meeting;
import mx.fei.ca.domain.MeetingAssistant;
import mx.fei.ca.domain.Prerequisite;

/**
 * FXML Controller class
 *
 * @author david
 */
public class WindowModifyAgendaController implements Initializable {

    @FXML
    private TextField tfProjectName;
    @FXML
    private TextField tfMeetingPlace;
    @FXML
    private TextField tfAffair;
    @FXML
    private DatePicker dpMeetingDate;
    @FXML
    private ComboBox cbHours;
    @FXML
    private ComboBox cbMinutes;
    @FXML
    private TableView<MeetingAssistant> tbIntegrants;
    @FXML
    private TableColumn<MeetingAssistant, String> columnIntegrant;
    @FXML
    private TableColumn<MeetingAssistant, String> columnLeader;
    @FXML
    private TableColumn<MeetingAssistant, String> columnTimeTaker;
    @FXML
    private TableColumn<MeetingAssistant, String> columnSecretary;
    @FXML
    private TableView<Prerequisite> tbPrerequisites;
    @FXML
    private TableColumn<Prerequisite, String> columnDescription;
    @FXML
    private TableColumn<Prerequisite, String> columnPrerequisiteManager;
    @FXML
    private TextField tfDescription;
    @FXML
    private ComboBox cbPrerequisiteManager;
    @FXML
    private TextField tfTopic;
    @FXML
    private ComboBox cbLeaderDiscussion;
    @FXML
    private TableView<AgendaPoint> tbAgendaPoints;
    @FXML
    private TableColumn<AgendaPoint, Time> columnTimeStart;
    @FXML
    private TableColumn<AgendaPoint, Time> columnTimeEnd;
    @FXML
    private TableColumn<AgendaPoint, String> columnTopic;
    @FXML
    private TableColumn<AgendaPoint, String> columnLeaderDiscussion;
    @FXML
    private ComboBox cbHourStart;
    @FXML
    private ComboBox cbMinuteStart;
    @FXML
    private ComboBox cbHourEnd;
    @FXML
    private ComboBox cbMinuteEnd;
    
    private int idMeeting;
    private ObservableList<Prerequisite> prerequisites;
    private ObservableList<AgendaPoint> agendaPoints;

    private enum TypeError{
        EMPTYFIELDS, INVALIDSTRINGS, MISSINGMEETINGTIME, MISSINGDATE, MEETINGAFFAIRDUPLICATE, DATEANDTIMEDUPLICATE,
        MANYROLES, DUPLICATEROLE, INCORRETDATE, MISSINGSELECTION, MINORHOUR, BUSYTIME, WRONGTIMEAGENDAPOINT, COLUMNMISSINGSELECTION,
        EMPTYTABLE, MISSINGROLE, DUPLICATEVALUE, UNNECESSARYSELECTION;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillComboBoxHours(cbHours);
        fillComboBoxMinutes(cbMinutes);
        fillComboBoxHours(cbHourStart);
        fillComboBoxMinutes(cbMinuteStart);
        fillComboBoxHours(cbHourEnd);
        fillComboBoxMinutes(cbMinuteEnd);
        prerequisites = FXCollections.observableArrayList();
        agendaPoints = FXCollections.observableArrayList();
    }    

    @FXML
    private void addPrerequisite(ActionEvent event) throws BusinessConnectionException{
        if(!existsInvalidFieldsForPrerequisites() && !existsDuplicateValueForAddPrerequisite()){
            PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
            columnDescription.setCellValueFactory(new PropertyValueFactory("description"));
            columnPrerequisiteManager.setCellValueFactory(new PropertyValueFactory("prerequisiteManager"));
            String description = tfDescription.getText();
            String prerequisiteManager = cbPrerequisiteManager.getSelectionModel().getSelectedItem().toString();
            Prerequisite prerequisite = new Prerequisite(description, prerequisiteManager);
            boolean savedPrerequisite = prerequisiteDAO.savedPrerequisite(prerequisite, idMeeting);
            if(savedPrerequisite){
                prerequisites.add(prerequisite);
                tbPrerequisites.setItems(prerequisites);
            }else{
                showLostConnectionAlert();
            }
            cleanFieldsPrerequisite();
        }
        
    }
    
    @FXML
    private void deletePrerequisite(ActionEvent event) throws BusinessConnectionException{
        Prerequisite prerequisite = tbPrerequisites.getSelectionModel().getSelectedItem();
        if(prerequisite == null){
            TypeError typeError = TypeError.COLUMNMISSINGSELECTION;
            showInvalidFieldAlert(typeError);
        }else{
            PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
            boolean deletedPrerequisite = prerequisiteDAO.deletedPrerequisiteById(prerequisite.getIdPrerequisite());
            if(deletedPrerequisite){
                prerequisites.remove(prerequisite);
                tbPrerequisites.refresh();
            }else{
                showLostConnectionAlert();
            }  
        }
    }
    
    @FXML
    private void fillPrerequisiteFields(MouseEvent event){
        Prerequisite prerequisite = tbPrerequisites.getSelectionModel().getSelectedItem();
        if(prerequisite != null){
            tfDescription.setText(prerequisite.getDescription());
            cbPrerequisiteManager.getSelectionModel().select(prerequisite.getPrerequisiteManager());
        }
    }
    
    @FXML
    private void updatePrerequisite(ActionEvent event) throws BusinessConnectionException{
        Prerequisite prerequisite = tbPrerequisites.getSelectionModel().getSelectedItem();
        if(prerequisite == null){
            TypeError typeError = TypeError.COLUMNMISSINGSELECTION;
            showInvalidFieldAlert(typeError);
        }else if(!existsInvalidFieldsForPrerequisites() && !existsDuplicateValueForUpdatePrerequisite(prerequisite.getIdPrerequisite())){ 
            PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
            columnDescription.setCellValueFactory(new PropertyValueFactory("description"));
            columnPrerequisiteManager.setCellValueFactory(new PropertyValueFactory("prerequisiteManager"));
            String description = tfDescription.getText();
            String prerequisiteManager = cbPrerequisiteManager.getSelectionModel().getSelectedItem().toString();
            Prerequisite modifiedPrerequisite = new Prerequisite(description, prerequisiteManager);
            boolean updatedPrerequisite = prerequisiteDAO.updatedPrerequisite(modifiedPrerequisite, prerequisite.getIdPrerequisite(), idMeeting);
            if(updatedPrerequisite){
                prerequisite.setDescription(modifiedPrerequisite.getDescription());
                prerequisite.setPrerequisiteManager(modifiedPrerequisite.getPrerequisiteManager());
                tbPrerequisites.refresh();
                cleanFieldsPrerequisite();
            }else{
                showLostConnectionAlert();
            } 
        }      
    }

    @FXML
    private void addAgendaPoint(ActionEvent event) throws BusinessConnectionException{
        if(!existsInvalidFieldsForAgendaPoint() && !existsDuplicateValueForAddAgendaPoint()){
            AgendaPointDAO agendaPointDAO = new AgendaPointDAO();
            columnTimeStart.setCellValueFactory(new PropertyValueFactory("startTime"));
            columnTimeEnd.setCellValueFactory(new PropertyValueFactory("endTime"));
            columnTopic.setCellValueFactory(new PropertyValueFactory("topic"));
            columnLeaderDiscussion.setCellValueFactory(new PropertyValueFactory("leader"));
            Time startTime = parseToSqlTime(cbHourStart.getSelectionModel().getSelectedItem().toString(), cbMinuteStart.getSelectionModel().getSelectedItem().toString());
            Time endTime = parseToSqlTime(cbHourEnd.getSelectionModel().getSelectedItem().toString(), cbMinuteEnd.getSelectionModel().getSelectedItem().toString());
            String topic = tfTopic.getText();
            String leader = cbLeaderDiscussion.getSelectionModel().getSelectedItem().toString();
            AgendaPoint agendaPoint = new AgendaPoint(startTime, endTime, topic, leader);
            boolean savedAgendaPoint = agendaPointDAO.savedAgendaPoint(agendaPoint, idMeeting);
            if(savedAgendaPoint){
                agendaPoints.add(agendaPoint);
                tbAgendaPoints.setItems(agendaPoints);
            }
            cleanFieldsAgendaPoint();
        }
    }

    @FXML
    private void deleteAgendaPoint(ActionEvent event) throws BusinessConnectionException{
        AgendaPoint agendaPoint = tbAgendaPoints.getSelectionModel().getSelectedItem();
        if(agendaPoint == null){
            TypeError typeError = TypeError.COLUMNMISSINGSELECTION;
            showInvalidFieldAlert(typeError);
        }else{
            AgendaPointDAO agendaPointDAO = new AgendaPointDAO();
            boolean deletedAgendaPoint = agendaPointDAO.deletedAgendaPointById(agendaPoint.getIdAgendaPoint());
            if(deletedAgendaPoint){
                agendaPoints.remove(agendaPoint);
                tbAgendaPoints.refresh();
            }else{
                showLostConnectionAlert();
            }
        }
    }
    
    @FXML
    private void fillAgendaPointFields(MouseEvent event){
        AgendaPoint agendaPoint = tbAgendaPoints.getSelectionModel().getSelectedItem();
        if(agendaPoint != null){
            cbHourStart.getSelectionModel().select(takeHours(agendaPoint.getStartTime()));
            cbMinuteStart.getSelectionModel().select(takeMinutes(agendaPoint.getStartTime()));
            cbHourEnd.getSelectionModel().select(takeHours(agendaPoint.getEndTime()));
            cbMinuteEnd.getSelectionModel().select(takeMinutes(agendaPoint.getEndTime()));
            tfTopic.setText(agendaPoint.getTopic());
            cbLeaderDiscussion.getSelectionModel().select(agendaPoint.getLeader());
        }
    }
      
    @FXML
    private void updateAgendaPoint(ActionEvent event) throws BusinessConnectionException{
        AgendaPoint agendaPoint = tbAgendaPoints.getSelectionModel().getSelectedItem();
        if(agendaPoint == null){
            TypeError typeError = TypeError.COLUMNMISSINGSELECTION;
            showInvalidFieldAlert(typeError);
        }else if(!existsInvalidFieldsForAgendaPoint() && !existsDuplicateValueForUpdateAgendaPoint(agendaPoint.getIdAgendaPoint())){
            AgendaPointDAO agendaPointDAO = new AgendaPointDAO();
            columnTimeStart.setCellValueFactory(new PropertyValueFactory("startTime"));
            columnTimeEnd.setCellValueFactory(new PropertyValueFactory("endTime"));
            columnTopic.setCellValueFactory(new PropertyValueFactory("topic"));
            columnLeaderDiscussion.setCellValueFactory(new PropertyValueFactory("leader"));
            Time startTime = parseToSqlTime(cbHourStart.getSelectionModel().getSelectedItem().toString(), cbMinuteStart.getSelectionModel().getSelectedItem().toString());
            Time endTime = parseToSqlTime(cbHourEnd.getSelectionModel().getSelectedItem().toString(), cbMinuteEnd.getSelectionModel().getSelectedItem().toString());
            String topic = tfTopic.getText();
            String leader = cbLeaderDiscussion.getSelectionModel().getSelectedItem().toString();
            AgendaPoint modifiedAgendaPoint = new AgendaPoint(startTime, endTime, topic, leader);
            boolean updatedAgendaPoint = agendaPointDAO.updatedAgendaPoint(modifiedAgendaPoint, agendaPoint.getIdAgendaPoint(), idMeeting);
            if(updatedAgendaPoint){
                agendaPoint.setStartTime(modifiedAgendaPoint.getStartTime());
                agendaPoint.setEndTime(modifiedAgendaPoint.getEndTime());
                agendaPoint.setTopic(modifiedAgendaPoint.getTopic());
                agendaPoint.setLeader(modifiedAgendaPoint.getLeader());
                tbAgendaPoints.refresh();
                cleanFieldsAgendaPoint();
            }else{
                showLostConnectionAlert();
            }
        }
        
    }

    @FXML
    private void updateMeeting(ActionEvent event) throws BusinessConnectionException{
        if(!existsInvalidFieldsForMeeting()){
             String projectName = tfProjectName.getText();
            String meetingPlace = tfMeetingPlace.getText();
            String affair = tfAffair.getText();
            Date meetingDate = parseToSqlDate(java.util.Date.from(dpMeetingDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            Time meetingTime = parseToSqlTime(cbHours.getSelectionModel().getSelectedItem().toString(), cbMinutes.getSelectionModel().getSelectedItem().toString());
            Meeting meeting = new Meeting(meetingDate, meetingTime,meetingPlace, affair, projectName, "Pendiente");     
            MeetingDAO meetingDAO = new MeetingDAO();
            boolean updatedMeeting = meetingDAO.updatedMeeting(meeting, idMeeting);
            if(updatedMeeting){
                showConfirmationAlert();
                closeModifyAgenda(event);
            }else{
                showLostConnectionAlert();
                closeModifyAgenda(event);
            }
        }
    }

    @FXML
    private void closeModifyAgenda(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    public void fillMeetingData(Meeting meeting){
        this.idMeeting = meeting.getIdMeeting();
        tfProjectName.setText(meeting.getProjectName());
        tfMeetingPlace.setText(meeting.getMeetingPlace());
        tfAffair.setText(meeting.getAffair());
        dpMeetingDate.setValue(meeting.getMeetingDate().toLocalDate());
        cbHours.getSelectionModel().select(takeHours(meeting.getMeetingTime()));
        cbMinutes.getSelectionModel().select(takeMinutes(meeting.getMeetingTime()));
        fillPrerequisitesTable(meeting.getPrerequisites());
        fillAgendaPointsTable(meeting.getAgendaPoints());
        //FALTA LLENAR LA TABLA DE ASISTENTES
    }
    
    private void fillComboBoxHours(ComboBox cbToFill){
        ObservableList<String> listHours = FXCollections.observableArrayList("07","08","09","10", "11","12","13","14","15","16","17",
                                                                             "18","19","20");                                    
        cbToFill.setItems(listHours);
    }
    
    private void fillComboBoxMinutes(ComboBox cbToFill){
        ObservableList<String> listMinutes = FXCollections.observableArrayList("00","01","02","03","04","05","06","07","08","09","10",
                                                                             "11","12","13","14","15","16","17","18","19",
                                                                             "20","21","22","23","24","25","26","27","28","29",
                                                                             "30","31","32","33","34","35","36","37","38","39",
                                                                             "40","41","42","43","44","45","46","47","48","49",
                                                                             "50","51","52","53","54","55","56","57","58","59");
        cbToFill.setItems(listMinutes);
    }
    
    private String takeHours(Time timeToSeparate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String stringTime = simpleDateFormat.format(timeToSeparate);
        String[] partsTime = stringTime.split(":");
        String stringHour = partsTime[0];
        return stringHour;
    }
    
    private String takeMinutes(Time timeToSeparate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String stringTime = simpleDateFormat.format(timeToSeparate);
        String[] partsTime = stringTime.split(":");
        String stringMinute = partsTime[1];
        return stringMinute;
    }
    
    private void fillPrerequisitesTable(ArrayList<Prerequisite> prerequisites){
        columnDescription.setCellValueFactory(new PropertyValueFactory("description"));
        columnPrerequisiteManager.setCellValueFactory(new PropertyValueFactory("prerequisiteManager"));
        ObservableList<Prerequisite> listPrerequisites = FXCollections.observableArrayList(prerequisites);
        tbPrerequisites.setItems(listPrerequisites);
    }
    
    private void fillAgendaPointsTable(ArrayList<AgendaPoint> agendaPoints){
        columnTimeStart.setCellValueFactory(new PropertyValueFactory("startTime"));
        columnTimeEnd.setCellValueFactory(new PropertyValueFactory("endTime"));
        columnTopic.setCellValueFactory(new PropertyValueFactory("topic"));
        columnLeaderDiscussion.setCellValueFactory(new PropertyValueFactory("leader"));
        ObservableList<AgendaPoint> listAgendaPoints = FXCollections.observableArrayList(agendaPoints);
        tbAgendaPoints.setItems(listAgendaPoints);
    }
    
    private java.sql.Date parseToSqlDate(java.util.Date date){
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        return sqlDate;
    }
    
    private java.sql.Time parseToSqlTime(String hours, String minutes){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        java.sql.Time meetingTime = null;
        try {
            meetingTime = new java.sql.Time(simpleDateFormat.parse(hours+":"+minutes).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(WindowNewMeetingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return meetingTime;
    }
    
    private boolean existsInvalidFieldsForMeeting() throws BusinessConnectionException{
        boolean invalidFields = false;
        if(existsEmptyFields(tfProjectName.getText()) || existsEmptyFields(tfMeetingPlace.getText()) || existsEmptyFields(tfAffair.getText())){
            invalidFields = true;
        }
        
        if(invalidFields || existsInvalidCharactersForName(tfProjectName.getText()) || existsInvalidCharacters(tfMeetingPlace.getText()) || 
            existsInvalidCharacters(tfAffair.getText())){
            invalidFields = true;
        }
        
        if(invalidFields || existsMissingDate() || existsIncorretDate() || existsMissingTime(cbHours) || existsMissingTime(cbMinutes) ||
           existsInvalidRoleSelection()){
            invalidFields = true;  
        }
  
        if(invalidFields || existsDuplicateValuesForMeeting() || existsEmptyTable()){
            invalidFields = true;
        }
        return invalidFields;
    }
    
    private boolean existsInvalidFieldsForPrerequisites(){
        boolean invalidFields = false;
        if(existsEmptyFields(tfDescription.getText()) || existsInvalidCharacters(tfDescription.getText()) 
           || existsMissingSelection(cbPrerequisiteManager)){
            invalidFields = true;
        }
        return invalidFields;
    }
    
    private boolean existsInvalidFieldsForAgendaPoint(){
        boolean invalidFields = false;
        if(existsEmptyFields(tfTopic.getText()) || existsMissingSelection(cbLeaderDiscussion) ||
           existsInvalidCharacters(tfTopic.getText()) || existsInvalidHours()){
            invalidFields = true;
        }
        return invalidFields;
    }
    
    private boolean existsEmptyFields(String textToValidate){
        boolean emptyFields = false;
        if(textToValidate.isEmpty()){
            emptyFields = true;
            TypeError typeError = TypeError.EMPTYFIELDS;
            showInvalidFieldAlert(typeError);     
        }
        return emptyFields;
    }
     
    private boolean existsMissingDate(){
        boolean missingDate = false;
        if(dpMeetingDate.getValue() == null){
            missingDate = true;
            TypeError typeError = TypeError.MISSINGDATE;
            showInvalidFieldAlert(typeError);
        }
        return missingDate;
    }
    
    private boolean existsIncorretDate(){
        boolean incorretDate = false;
        int meetingDay = dpMeetingDate.getValue().getDayOfMonth();
        int meetingMonth = dpMeetingDate.getValue().getMonthValue();
        int meetingYear = dpMeetingDate.getValue().getYear();
        
        Calendar currentDate = Calendar.getInstance();
        int currentDay = currentDate.get(Calendar.DATE);
        int currentMonth = currentDate.get(Calendar.MONTH);
        int currentYear = currentDate.get(Calendar.YEAR);
      
        if(currentDay > meetingDay && currentMonth+1 >= meetingMonth && currentYear >= meetingYear){
            incorretDate = true;
            TypeError typeError = TypeError.INCORRETDATE;
            showInvalidFieldAlert(typeError);
        }
        return incorretDate;
    }
    
    private boolean existsMissingTime(ComboBox cbTimeToValidate){
        boolean missingMeetingTime = false;
        if(cbTimeToValidate.getSelectionModel().getSelectedItem().equals("")){
            missingMeetingTime = true;
            TypeError typeError = TypeError.MISSINGMEETINGTIME;
            showInvalidFieldAlert(typeError);
        }
        return missingMeetingTime;
    }
    
    private boolean existsInvalidCharactersForName(String textToValidate){
        boolean invalidCharactersForName = false;
        Pattern pattern = Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúñÑ\\s]+$");
        Matcher matcher = pattern.matcher(textToValidate);
        if(!matcher.find()){
           invalidCharactersForName = true; 
           TypeError typeError = TypeError.INVALIDSTRINGS;
           showInvalidFieldAlert(typeError);
        }
        return invalidCharactersForName;
    }
    
    private boolean existsInvalidCharacters(String textToValidate){
        boolean invalidCharacters = false;
        Pattern pattern = Pattern.compile("^[0-9A-Za-zÁÉÍÓÚáéíóúñÑ\\s\\.,]+$");
        Matcher matcher = pattern.matcher(textToValidate);
        if(!matcher.find()){
           invalidCharacters = true; 
           TypeError typeError = TypeError.INVALIDSTRINGS;
           showInvalidFieldAlert(typeError);
        }
        return invalidCharacters;
    }
    
    private boolean existsInvalidRoleSelection(){
        boolean invalidRole = false;
        if(existsIntegrantWithManyRoles() || existsDuplicateRole()){
            invalidRole = true;
        }
        return invalidRole;
    }
    
    private boolean existsIntegrantWithManyRoles(){
        boolean existsIntegrant = false;
        for (MeetingAssistant meetingAssistant : tbIntegrants.getItems()) {
            if((meetingAssistant.getRbLeaderRole().isSelected() && meetingAssistant.getRbSecretaryRole().isSelected()) || (meetingAssistant.getRbLeaderRole().isSelected() && meetingAssistant.getRbTimeTakerRole().isSelected()) || (meetingAssistant.getRbSecretaryRole().isSelected() && meetingAssistant.getRbTimeTakerRole().isSelected())){
                existsIntegrant = true; 
                TypeError typeError = TypeError.MANYROLES;
                showInvalidFieldAlert(typeError);
                break;
            }
        }
        return existsIntegrant;
    }
    
    private boolean existsDuplicateRole(){
        boolean duplicateRole = false;
        boolean leaderSelected = false;
        boolean secretarySelected = false;
        boolean timeTakerSelected = false;
        for (MeetingAssistant meetingAssistant: tbIntegrants.getItems()){
            if(meetingAssistant.getRbLeaderRole().isSelected() && leaderSelected){
                duplicateRole = true;
                TypeError typeError = TypeError.DUPLICATEROLE;
                showInvalidFieldAlert(typeError);
                break;
            }else if(meetingAssistant.getRbLeaderRole().isSelected()){
                leaderSelected = true;
            }
            
            if(meetingAssistant.getRbSecretaryRole().isSelected() && secretarySelected){
                duplicateRole = true;
                TypeError typeError = TypeError.DUPLICATEROLE;
                showInvalidFieldAlert(typeError);
                break;
            }else if(meetingAssistant.getRbSecretaryRole().isSelected()){
                secretarySelected = true;
            }
            
            if(meetingAssistant.getRbTimeTakerRole().isSelected() && timeTakerSelected){
                duplicateRole = true;
                TypeError typeError = TypeError.DUPLICATEROLE;
                showInvalidFieldAlert(typeError);
                break;
            }else if(meetingAssistant.getRbTimeTakerRole().isSelected()){
                timeTakerSelected = true;
            }
            
        }
        return duplicateRole;
    }
    
    private boolean existsMissingRole(){
        boolean missingRole = false;
        boolean leaderSelected = false;
        boolean secretarySelected = false;
        boolean timeTakerSelected = false;
        for(MeetingAssistant meetingAssistant: tbIntegrants.getItems()){
            if(meetingAssistant.getRbLeaderRole().isSelected()){
                leaderSelected = true;
            }
            if(!leaderSelected && meetingAssistant.getRbSecretaryRole().isSelected()){
                secretarySelected = true;
            }
            if(!secretarySelected && meetingAssistant.getRbTimeTakerRole().isSelected()){
                timeTakerSelected = true;
            }
        }
        if(!leaderSelected || !secretarySelected || !timeTakerSelected){
            missingRole = true;
            TypeError typeError = TypeError.MISSINGROLE;
            showInvalidFieldAlert(typeError);
        }
        return missingRole;
    }
    
    private boolean existsDuplicateValuesForMeeting() throws BusinessConnectionException{
        MeetingDAO meetingDAO = new MeetingDAO();
        boolean meetingAffairDuplicate = false;
        if(meetingDAO.existsMeetingAffairForUpdate(tfAffair.getText(), idMeeting)){
            meetingAffairDuplicate = true;
            TypeError typeError = TypeError.MEETINGAFFAIRDUPLICATE;
            showInvalidFieldAlert(typeError);
        }
        
        boolean dateAndTimeDuplicate = false;
        if(!meetingDAO.existsDateAndTimeAvailableForUpdate(parseToSqlDate(java.util.Date.from(dpMeetingDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())),
           parseToSqlTime(cbHours.getSelectionModel().getSelectedItem().toString(), cbMinutes.getSelectionModel().getSelectedItem().toString()), idMeeting)){
            dateAndTimeDuplicate = true;
            TypeError typeError = TypeError.DATEANDTIMEDUPLICATE;
            showInvalidFieldAlert(typeError);
        }
        
        boolean duplicateValues = false;
        if(meetingAffairDuplicate || dateAndTimeDuplicate){
            duplicateValues = true;
        }
        
        return duplicateValues;
    }
    
    private boolean existsMissingSelection(ComboBox cbToValidate){
        boolean missingSelection = false;
        if(cbToValidate.getSelectionModel().getSelectedItem().equals("")){
            missingSelection = true;
            TypeError typeError = TypeError.MISSINGSELECTION;
            showInvalidFieldAlert(typeError);
        }
        return missingSelection;
    }
    
    private boolean existsInvalidHours(){
        boolean invalidHours = false;
        if(existsMissingTime(cbHourStart) || existsMissingTime(cbMinuteStart) || existsMissingTime(cbHourEnd) || existsMissingTime(cbMinuteEnd)){
            invalidHours = true;
        }
        
        if(!invalidHours && !existsMissingTime(cbHours) && !existsMissingTime(cbMinutes)){
            int agendaPointHourStart = Integer.parseInt((String) cbHourStart.getSelectionModel().getSelectedItem());
            int agendaPointMinuteStart = Integer.parseInt((String) cbMinuteStart.getSelectionModel().getSelectedItem());
            int hourMeeting = Integer.parseInt((String) cbHours.getSelectionModel().getSelectedItem());
            int minuteMeeting = Integer.parseInt((String) cbMinutes.getSelectionModel().getSelectedItem());
            if(agendaPointHourStart < hourMeeting || (agendaPointHourStart == hourMeeting && agendaPointMinuteStart < minuteMeeting)){
                invalidHours = true;
                TypeError typeError = TypeError.MINORHOUR;
                showInvalidFieldAlert(typeError);
            }
            int agendaPointHourEnd = Integer.parseInt((String) cbHourEnd.getSelectionModel().getSelectedItem());
            int agendaPointMinuteEnd = Integer.parseInt((String) cbMinuteEnd.getSelectionModel().getSelectedItem());
            if(!invalidHours && (agendaPointHourEnd < agendaPointHourStart || (agendaPointHourEnd <= agendaPointHourStart && agendaPointMinuteEnd <= agendaPointMinuteStart))){
                invalidHours = true;
                TypeError typeError = TypeError.WRONGTIMEAGENDAPOINT;
                showInvalidFieldAlert(typeError);
            }
        }
        
        if(!invalidHours && existsBusyTimeForAgendaPoint()){
            invalidHours = true;
        }
        
        return invalidHours;
    }
    
    private boolean existsBusyTimeForAgendaPoint(){
        boolean exists = false;
        Time hourStart = parseToSqlTime(cbHourStart.getSelectionModel().getSelectedItem().toString(), cbMinuteStart.getSelectionModel().getSelectedItem().toString());
        Time hourEnd = parseToSqlTime(cbHourEnd.getSelectionModel().getSelectedItem().toString(), cbMinuteEnd.getSelectionModel().getSelectedItem().toString());
        for(AgendaPoint agendaPoint: tbAgendaPoints.getItems()){ 
            if(agendaPoint.getStartTime().equals(hourStart) || agendaPoint.getEndTime().equals(hourEnd)){
                exists = true;
                TypeError typeError = TypeError.BUSYTIME;
                showInvalidFieldAlert(typeError);
            }
        }
        return exists;
    }
    
    private boolean existsDuplicateValueForAddPrerequisite(){
        boolean duplicateValue = false;
        for(Prerequisite prerequisite: tbPrerequisites.getItems()){
            if(prerequisite.getDescription().equals(tfDescription.getText())){
                duplicateValue = true;
                TypeError typeError = TypeError.DUPLICATEVALUE;
                showInvalidFieldAlert(typeError);
            }
        }
        return duplicateValue;
    }
    
    private boolean existsDuplicateValueForAddAgendaPoint(){
        boolean duplicateValue = false;
        for(AgendaPoint agendaPoint: tbAgendaPoints.getItems()){
            if(agendaPoint.getTopic().equals(tfTopic.getText())){
                duplicateValue = true;
                TypeError typeError = TypeError.DUPLICATEVALUE;
                showInvalidFieldAlert(typeError);
            }
        }
        return duplicateValue;
    }
    
    private boolean existsDuplicateValueForUpdatePrerequisite(int idPrerequisite){
        boolean duplicateValue = false;
        for(Prerequisite prerequisite: tbPrerequisites.getItems()){
            if(prerequisite.getIdPrerequisite() != idPrerequisite && prerequisite.getDescription().equals(tfDescription.getText())){
                duplicateValue = true;
                TypeError typeError = TypeError.DUPLICATEVALUE;
                showInvalidFieldAlert(typeError);
            }
        }
        return duplicateValue;
    }
    
    private boolean existsDuplicateValueForUpdateAgendaPoint(int idAgendaPoint){
        boolean duplicateValue = false;
        for(AgendaPoint agendaPoint: tbAgendaPoints.getItems()){
            if(agendaPoint.getIdAgendaPoint() !=  idAgendaPoint && agendaPoint.getTopic().equals(tfTopic.getText())){
                duplicateValue = true;
                TypeError typeError = TypeError.DUPLICATEVALUE;
                showInvalidFieldAlert(typeError);
            }
        }
        return duplicateValue;
    }
    
     private boolean existsEmptyTable(){
        boolean emptyTable = false;
        ObservableList<Prerequisite> listPrerequisites = tbPrerequisites.getItems();
        ObservableList<AgendaPoint> listAgendaPoints = tbAgendaPoints.getItems();
        if(listPrerequisites.isEmpty() || listAgendaPoints.isEmpty()){
            emptyTable = true;
            TypeError typeError = TypeError.EMPTYTABLE;
            showInvalidFieldAlert(typeError);
        }
        return emptyTable;
    }
    
    private void cleanFieldsPrerequisite(){
        tfDescription.clear();
        cbPrerequisiteManager.getSelectionModel().clearSelection();
    }
    
    private void cleanFieldsAgendaPoint(){
        cbHourStart.getSelectionModel().clearSelection();
        cbMinuteStart.getSelectionModel().clearSelection();
        cbHourEnd.getSelectionModel().clearSelection();
        cbMinuteEnd.getSelectionModel().clearSelection();
        tfTopic.clear();
        cbLeaderDiscussion.getSelectionModel().clearSelection();
    }
    
    private void showInvalidFieldAlert(TypeError typeError){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Campo inválido");
        if(typeError == TypeError.EMPTYFIELDS){
          alert.setContentText("Existen campos vacíos, llena los campos para poder modificar");  
        }
        
        if(typeError == TypeError.INVALIDSTRINGS){
            alert.setContentText("Existen caracteres inválidos, revisa los textos para poder modificar");
        }
        
        if(typeError == TypeError.MISSINGDATE){
            alert.setContentText("Falta seleccionar la fecha de la reunión, selecciona la fecha para poder modificar");
        }
        
        if(typeError == TypeError.MISSINGMEETINGTIME){
            alert.setContentText("Falta seleccionar la hora selecciona la hora y minutos para poder modificar");
        }
        
        if(typeError == TypeError.MEETINGAFFAIRDUPLICATE){
            alert.setContentText("El asunto de la reunión ya se encuentra registrado en otra reunión");
        }
        
        if(typeError == TypeError.MEETINGAFFAIRDUPLICATE){
            alert.setContentText("La fecha y hora de la reunión no se encuentran disponibles debido a que ya existe una reunión registrada con dicha fecha y hora");
        }
        
        if(typeError == TypeError.MANYROLES){
            alert.setContentText("Un integrante solo puede tener un rol, modifica los roles para poder guardar");
        }
        
        if(typeError == TypeError.DUPLICATEROLE){
            alert.setContentText("Rol duplicado. Un rol solo puede pertenecer a un integrante, corrija para poder guardar");
        }
        
        if(typeError == TypeError.INCORRETDATE){
            alert.setContentText("La fecha de la reunión debe ser una fecha posterior a la fecha actual");
        }
        
        if(typeError == TypeError.MISSINGSELECTION){
            alert.setContentText("Selección faltante, selecciona la información para poder Añadir");
        }
        
        if(typeError == TypeError.MINORHOUR){
            alert.setContentText("La hora de inicio de un punto de agenda debe ser igual o mayor a la hora de la reunión");
        }
        
        if(typeError == TypeError.BUSYTIME){
            alert.setContentText("La hora de inicio o fin del punto de agenda no se encuentra disponible debido a que otro punto de agenda ya tiene esa hora");
        }
        
        if(typeError == TypeError.WRONGTIMEAGENDAPOINT){
            alert.setContentText("La hora de fin no puede ser menor o igual a la hora de inicio de un punto de agenda. Corrije para poder añadir");
        }
        
        if(typeError == TypeError.COLUMNMISSINGSELECTION){
            alert.setContentText("Selección de fila faltante. Selecciona una fila para poder eliminar o modificar");
        }
        
        if(typeError == TypeError.EMPTYTABLE){
            alert.setContentText("La reunión debe tener por lo menos un punto de agenda y un prerequisito. Añade para poder guardar");
        }
        
        if(typeError == TypeError.MISSINGROLE){
            alert.setContentText("Falta asignar rol, por favor asigna el rol a un integrante para poder guardar");
        }
        
        if(typeError == TypeError.DUPLICATEVALUE){
            alert.setContentText("El texto que deseas añadir ya se encuentra registrado, verifica por favor");
        }
        
        if(typeError == TypeError.UNNECESSARYSELECTION){
            alert.setContentText("Para añadir no debes seleccionar una fila de la tabla");
        }
        alert.showAndWait();
    }
    
    private void showConfirmationAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación de guardado");
        alert.setContentText("La información fue guardada con éxito");
        alert.showAndWait();
    }
    
    private void showLostConnectionAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Perdida de conexión");
        alert.setContentText("Perdida de conexión con la base de datos, no se pudo guardar. Intente más tarde");
        alert.showAndWait();
    }
    
    
    
}
