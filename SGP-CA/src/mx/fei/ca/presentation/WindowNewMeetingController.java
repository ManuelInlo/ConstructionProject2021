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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mx.fei.ca.businesslogic.AgendaPointDAO;
import mx.fei.ca.businesslogic.IntegrantDAO;
import mx.fei.ca.businesslogic.MeetingAssistantDAO;
import mx.fei.ca.businesslogic.MeetingDAO;
import mx.fei.ca.businesslogic.PrerequisiteDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.AgendaPoint;
import mx.fei.ca.domain.Integrant;
import mx.fei.ca.domain.Meeting;
import mx.fei.ca.domain.MeetingAssistant;
import mx.fei.ca.domain.Prerequisite;

/**
 * Clase para representar el controlador del FXML WindowNewMeeting
 * @author David Alexander Mijangos Paredes
 * @version 17-06-2021
 */

public class WindowNewMeetingController implements Initializable {

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
    @FXML
    private Label lbUser;
    private ObservableList<Prerequisite> prerequisites;
    private ObservableList<AgendaPoint> agendaPoints;
    private ArrayList<Integrant> integrants;
    private Integrant integrant;
    int idMeeting;
    
    /**
     * Enumerado que representa los tipos de errores espec??ficos al agendar una nueva reuni??n
     */
    
    private enum TypeError{
        EMPTYFIELDS, INVALIDSTRINGS, MISSINGMEETINGTIME, MISSINGDATE, MEETINGAFFAIRDUPLICATE, DATEANDTIMEDUPLICATE,
        MANYROLES, DUPLICATEROLE, INCORRETDATE, MISSINGSELECTION, MINORHOUR, BUSYTIME, WRONGTIMEAGENDAPOINT, COLUMNMISSINGSELECTION,
        EMPTYTABLE, MISSINGROLE, DUPLICATEVALUE, INVALIDLENGTH;
    }
    
    /**
     * M??todo que establece el integrante loggeado al sistema, permitiendo proyectar su nombre en la GUI
     * @param integrant Define el integrante a establecer en la GUI
     */
    
    public void setIntegrant(Integrant integrant){
        this.integrant = integrant;
        lbUser.setText(integrant.getNameIntegrant());
    }
    
    /**
     * M??todo que agrega un nuevo prerequisito a la tabla de prerequisitos de la GUI
     * @param event Define el evento generado
     */

    @FXML
    private void addPrerequisite(ActionEvent event){
        if(!existsInvalidFieldsForPrerequisites()){
            columnDescription.setCellValueFactory(new PropertyValueFactory("description"));
            columnPrerequisiteManager.setCellValueFactory(new PropertyValueFactory("prerequisiteManager"));
            String description = tfDescription.getText();
            String prerequisiteManager = cbPrerequisiteManager.getSelectionModel().getSelectedItem().toString();
            Prerequisite prerequisite = new Prerequisite(description, prerequisiteManager);
            prerequisites.add(prerequisite);
            tbPrerequisites.setItems(prerequisites);
            cleanFieldsPrerequisite();
        }
    }
    
    /**
     * M??todo que elimina un prerequisito seleccionado de la tabla de perequisitos de la GUI
     * @param event Define el evento generado
     */

    @FXML
    private void deletePrerequisite(ActionEvent event){
        Prerequisite prerequisite = tbPrerequisites.getSelectionModel().getSelectedItem();
        if(prerequisite == null){
            TypeError typeError = TypeError.COLUMNMISSINGSELECTION;
            showInvalidFieldAlert(typeError);
        }else{
            prerequisites.remove(prerequisite);
            tbPrerequisites.refresh();
        }
    }
    
    /**
     * M??todo que agrega un nuevo punto de agenda a la tabla de agenda de la GUI
     * @param event Define el evento generado
     */

    @FXML
    private void addAgendaPoint(ActionEvent event){
        if(!existsInvalidFieldsForAgendaPoint()){
            columnTimeStart.setCellValueFactory(new PropertyValueFactory("startTime"));
            columnTimeEnd.setCellValueFactory(new PropertyValueFactory("endTime"));
            columnTopic.setCellValueFactory(new PropertyValueFactory("topic"));
            columnLeaderDiscussion.setCellValueFactory(new PropertyValueFactory("leader"));
            Time startTime = parseToSqlTime(cbHourStart.getSelectionModel().getSelectedItem().toString(), cbMinuteStart.getSelectionModel().getSelectedItem().toString());
            Time endTime = parseToSqlTime(cbHourEnd.getSelectionModel().getSelectedItem().toString(), cbMinuteEnd.getSelectionModel().getSelectedItem().toString());
            String topic = tfTopic.getText();
            String leader = cbLeaderDiscussion.getSelectionModel().getSelectedItem().toString();
            AgendaPoint agendaPoint = new AgendaPoint(startTime, endTime, topic, leader);
            agendaPoints.add(agendaPoint);
            tbAgendaPoints.setItems(agendaPoints);
            cleanFieldsAgendaPoint();
        }
    }
    
    /**
     * M??todo que elimina un punto de agenda seleccionado de la tabla de agenda de la GUI
     * @param event 
     */
    
    @FXML
    private void deleteAgendaPoint(ActionEvent event){
        AgendaPoint agendaPoint = tbAgendaPoints.getSelectionModel().getSelectedItem();
        if(agendaPoint == null){
            TypeError typeError = TypeError.COLUMNMISSINGSELECTION;
            showInvalidFieldAlert(typeError);
        }else{
            agendaPoints.remove(agendaPoint);
            tbAgendaPoints.refresh();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        IntegrantDAO integrantDAO = new IntegrantDAO();
        try {
            this.integrants = integrantDAO.findAllIntegrants();
        } catch (BusinessConnectionException ex) {
            showLostConnectionAlert();
        }
        fillComboBoxHours(cbHours);
        fillComboBoxMinutes(cbMinutes);
        fillComboBoxHours(cbHourStart);
        fillComboBoxMinutes(cbMinuteStart);
        fillComboBoxHours(cbHourEnd);
        fillComboBoxMinutes(cbMinuteEnd);
        fillComboBoxForIntegrants(cbPrerequisiteManager);
        fillComboBoxForIntegrants(cbLeaderDiscussion);
        prerequisites = FXCollections.observableArrayList();
        agendaPoints = FXCollections.observableArrayList();
        fillIntegrantsTable();
    }   
    
    /**
     * M??todo que manda a guardar la nueva reuni??n con sus componentes a la base de datos
     * @param event Define el evento generado
     * @throws BusinessConnectionException 
     */

    @FXML
    private void scheduleMeeting(ActionEvent event) throws BusinessConnectionException{
        if(!existsInvalidFields()){
            String projectName = tfProjectName.getText();
            String meetingPlace = tfMeetingPlace.getText();
            String affair = tfAffair.getText();
            Date meetingDate = parseToSqlDate(java.util.Date.from(dpMeetingDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            Time meetingTime = parseToSqlTime(cbHours.getSelectionModel().getSelectedItem().toString(), cbMinutes.getSelectionModel().getSelectedItem().toString());
            Meeting meeting = new Meeting(meetingDate, meetingTime,meetingPlace, affair, projectName, "Pendiente");     
            MeetingDAO meetingDAO = new MeetingDAO();
            idMeeting = meetingDAO.saveAndReturnIdNewMeeting(meeting, integrant.getCurp()); 
            if(idMeeting != 0 && savedPrerequisites() && savedAgendaPoints() && savedMeetingAssistants()){  
                showConfirmationAlert();
                closeNewMeeting(event);
            }else{
                showLostConnectionAlert(); 
            }
        }
    }
    
    /**
     * M??todo que manda a guardar los prerequisitos de la tabla de prerequisitos a la base de datos
     * @return Booleano con el resultado de guardado, devuelve true, si guard??, de lo contrario devuelve false
     * @throws BusinessConnectionException 
     */
    
    private boolean savedPrerequisites() throws BusinessConnectionException{
        boolean savedPrerequisite = true;
        PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
        for (Prerequisite prerequisite: tbPrerequisites.getItems()){
            savedPrerequisite = prerequisiteDAO.savedPrerequisite(prerequisite, idMeeting);
            if(!savedPrerequisite){
                break;
            }
        }
        return savedPrerequisite;
    }
    
    /**
     * M??todo que manda a guardar los puntos de agenda de la tabla agenda a la base de datos
     * @return Booleano con el resultado de guardado, devuelve true, si guard??, de lo contrario devuelve false
     * @throws BusinessConnectionException 
     */
    
    private boolean savedAgendaPoints() throws BusinessConnectionException{
        boolean savedAgendaPoint = true;
        AgendaPointDAO agendaPointDAO = new AgendaPointDAO();
        for (AgendaPoint agendaPoint: tbAgendaPoints.getItems()){
            savedAgendaPoint = agendaPointDAO.savedAgendaPoint(agendaPoint, idMeeting);
            if(!savedAgendaPoint){
                break;
            }
        }
        return savedAgendaPoint;
    }
    
    /**
     * M??todo que manda a guardar los asistentes de reuni??n de la tabla integrantes a la base de datos
     * @return Booleano con el resultado de guardado, devuelve true, si guard??, de lo contrario devuelve false
     * @throws BusinessConnectionException 
     */
    
    private boolean savedMeetingAssistants() throws BusinessConnectionException{
        boolean savedMeetingAssistant = true;
        MeetingAssistantDAO meetingAssistantDAO = new MeetingAssistantDAO();
        for (MeetingAssistant meetingAssistant: tbIntegrants.getItems()){
            if(meetingAssistant.getRbLeaderRole().isSelected()){
                meetingAssistant.setRole("Lider");
            }else if(meetingAssistant.getRbSecretaryRole().isSelected()){
                meetingAssistant.setRole("Secretario");
            }else if(meetingAssistant.getRbTimeTakerRole().isSelected()){
                meetingAssistant.setRole("Tomador de tiempo");
            }
            savedMeetingAssistant = meetingAssistantDAO.savedMeetingAssistant(meetingAssistant, idMeeting);
            if(!savedMeetingAssistant){
                break;
            }
        }
        return savedMeetingAssistant;
    }
    
    /** 
     * M??todo que cierra la ventana actual "Nueva reuni??n"
     * @param event Define el evento generado
     */
    
    @FXML
    private void closeNewMeeting(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    /**
     * M??todo que llena los ComboBox que requieren horas
     * @param cbToFill Define el ComboBox a llenar
     */
    
    private void fillComboBoxHours(ComboBox cbToFill){
        ObservableList<String> listHours = FXCollections.observableArrayList("07","08","09","10", "11","12","13","14","15","16","17",
                                                                             "18","19","20");                                    
        cbToFill.setItems(listHours);
    }
    
    /**
     * M??todo que llena los ComboBox que requieren minutos
     * @param cbToFill Define el ComboBox a llenar
     */
    
    private void fillComboBoxMinutes(ComboBox cbToFill){
        ObservableList<String> listMinutes = FXCollections.observableArrayList("00","01","02","03","04","05","06","07","08","09","10",
                                                                             "11","12","13","14","15","16","17","18","19",
                                                                             "20","21","22","23","24","25","26","27","28","29",
                                                                             "30","31","32","33","34","35","36","37","38","39",
                                                                             "40","41","42","43","44","45","46","47","48","49",
                                                                             "50","51","52","53","54","55","56","57","58","59");
        cbToFill.setItems(listMinutes);
    }
    
    /**
     * M??todo que llena los ComboBox que requieren los integrantes del CA
     * @param cbToFill Define el ComboBox a llenar
     */
    
    private void fillComboBoxForIntegrants(ComboBox cbToFill){
        ObservableList<Integrant> listIntegrants = FXCollections.observableArrayList(this.integrants);
        cbToFill.setItems(listIntegrants);
    }
    
    /**
     * M??todo que llena la tabla integrantes con los integrantes del CA
     */
    
    private void fillIntegrantsTable(){
        ArrayList<MeetingAssistant> meetingAssistants = new ArrayList<>();
        for(int i = 0; i < this.integrants.size(); i++){
            MeetingAssistant meetingAssistant = new MeetingAssistant(integrants.get(i));
            meetingAssistants.add(meetingAssistant);
        }
        ObservableList<MeetingAssistant> listMeetingAssistants = FXCollections.observableArrayList(meetingAssistants);
        columnIntegrant.setCellValueFactory(new PropertyValueFactory("nameAssistant"));
        columnLeader.setCellValueFactory(new PropertyValueFactory("rbLeaderRole"));
        columnTimeTaker.setCellValueFactory(new PropertyValueFactory("rbTimeTakerRole"));
        columnSecretary.setCellValueFactory(new PropertyValueFactory("rbSecretaryRole"));
        tbIntegrants.setItems(listMeetingAssistants);
    }
    
    /**
     * M??todo que cambia una variable util.Date a sql.Date porque se necesita para guardar en la base de datos
     * @param date Define la variable de tipo util.Data a cambiar
     * @return Variable cambiada a tipo sql.Date
     */
   
    private java.sql.Date parseToSqlDate(java.util.Date date){
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        return sqlDate;
    }
    
    /**
     * M??todo que cambia horas y minutos en cadena hacia una variable en formato sql.Time porque se necesita para guardar en la base de datos
     * @param hours Define el String con la hora seleccionada
     * @param minutes Define el String con minutos seleccionados
     * @return Variable cambiada a tipo sql.Time
     */
    
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
    
    /**
     * M??todo que verifica si existen campos inv??lidos en la GUI
     * El m??todo invoca a otros m??todos con validaciones m??s espec??ficas
     * @return Booleano con el resultado de la verificaci??n, devuelve true si existen inv??lidos, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    private boolean existsInvalidFields() throws BusinessConnectionException{
        boolean invalidFields = false;
        if(existsEmptyFields(tfProjectName.getText()) || existsEmptyFields(tfMeetingPlace.getText()) || existsEmptyFields(tfAffair.getText())){
            invalidFields = true;
        }
        
        if(invalidFields || existsInvalidCharactersForName(tfProjectName.getText()) || existsInvalidCharacters(tfMeetingPlace.getText()) || 
            existsInvalidCharacters(tfAffair.getText())){
            invalidFields = true;
        }
        
        if(invalidFields || existsMissingDate() || existsIncorretDate() || existsMissingTime(cbHours) || existsMissingTime(cbMinutes) ||
           existsInvalidRoleSelection() || existsMissingRole()){
            invalidFields = true;  
        }
  
        if(invalidFields || existsDuplicateValuesForMeeting() || existsEmptyTable()){
            invalidFields = true;
        }
        
        if(invalidFields || existsInvalidLength(tfProjectName.getText()) || existsInvalidLength(tfMeetingPlace.getText()) || existsInvalidLength(tfAffair.getText())){
            invalidFields = true;
        }
        return invalidFields;
    }
    
    /**
     * M??todo que verifica si existen tablas vac??as en la GUI
     * Se implementa el m??todo porque una reuni??n debe tener por lo menos un prerequisito y un punto de agenda
     * @return Booleano con el resultado de verificaci??n, devuelve true si existen vac??as, de lo contario, devuelve false
     */
    
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
     * M??todo que verifica si existe texto obtenido de la GUI que est?? vac??o
     * @param textToValidate Define el texto a validar
     * @return Booleano con el resultado de verificaci??n, devuelve true si est?? vac??o, de lo contrario, devuelve false
     */
    
    private boolean existsEmptyFields(String textToValidate){
        boolean emptyFields = false;
        if(textToValidate.isEmpty()){
            emptyFields = true;
            TypeError typeError = TypeError.EMPTYFIELDS;
            showInvalidFieldAlert(typeError);     
        }
        return emptyFields;
    }
    
    /**
     * M??todo que verifica si existe selecci??n de fecha faltante
     * @return Booleano con el resultado de verificaci??n, devuelve true si existe faltante, de lo contrario, devuelve false
     */
     
    private boolean existsMissingDate(){
        boolean missingDate = false;
        if(dpMeetingDate.getValue() == null){
            missingDate = true;
            TypeError typeError = TypeError.MISSINGDATE;
            showInvalidFieldAlert(typeError);
        }
        return missingDate;
    }
    
    /**
     * M??todo que verifica si existen fechas incorrectas
     * Se implementa el m??todo porque se necesita verificar que la fecha de la reuni??n no sea menor a la fecha actual
     * @return Booleano con el resultado de verificaci??n, devuelve true si existe fecha incorrecta, de lo contrario, devuelve true
     */
    
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
    
    /**
     * M??todo que verifica si existe selecci??n de hora o minutos faltante
     * @param cbTimeToValidate Define el ComboBox a validar
     * @return Booleano con el resultado de verificaci??n, devuelve true si existe selecci??n faltante, de lo contrario, devuelve false
     */
    
    private boolean existsMissingTime(ComboBox cbTimeToValidate){
        boolean missingMeetingTime = false;
        if(cbTimeToValidate.getSelectionModel().getSelectedIndex() < 0){
            missingMeetingTime = true;
            TypeError typeError = TypeError.MISSINGMEETINGTIME;
            showInvalidFieldAlert(typeError);
        }
        return missingMeetingTime;
    }
    
    /**
     * M??todo que verifica si un nombre tiene caracteres no permitidos
     * Se implementa el m??todo porque un nombre no puede tener ciertos caracteres a comparaci??n de otros textos
     * @param textToValidate Define el nombre a verificar
     * @return Booleano con el resultado de verificaci??n, devuelve true si existe inv??lido, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidCharactersForName(String textToValidate){
        boolean invalidCharactersForName = false;
        Pattern pattern = Pattern.compile("^[A-Za-z????????????????????????\\s]+$");
        Matcher matcher = pattern.matcher(textToValidate);
        if(!matcher.find()){
           invalidCharactersForName = true; 
           TypeError typeError = TypeError.INVALIDSTRINGS;
           showInvalidFieldAlert(typeError);
        }
        return invalidCharactersForName;
    }
    
    /**
     * M??todo que verifica si existen caracteres inv??lidos para el resto de campos de la GUI
     * @param textToValidate Define el texto a validar
     * @return Booleano con el resultado de verificaci??n, devuelve true si existe inv??lido, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidCharacters(String textToValidate){
        boolean invalidCharacters = false;
        Pattern pattern = Pattern.compile("^[0-9A-Za-z????????????????????????\\s\\.,]+$");
        Matcher matcher = pattern.matcher(textToValidate);
        if(!matcher.find()){
           invalidCharacters = true; 
           TypeError typeError = TypeError.INVALIDSTRINGS;
           showInvalidFieldAlert(typeError);
        }
        return invalidCharacters;
    }
    
    /**
     * M??todo que verifica si existe selecci??n inv??lida de rol en la tabla de integrantes
     * El m??todo invoca a otros m??todos de verificaci??n m??s espec??ficos
     * @return Booleano con el resultado de verificaci??n, devuelve true si existe selecci??n inv??lida, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidRoleSelection(){
        boolean invalidRole = false;
        if(existsIntegrantWithManyRoles() || existsDuplicateRole()){
            invalidRole = true;
        }
        return invalidRole;
    }
    
    /**
     * M??todo que valida si un integrante cuenta con m??s de un rol seleccionado
     * @return Booleano con el resultado de verificaci??n, devuelve true si existe m??s de un rol en un integrante, de lo contrario, devuelve false
     */
    
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
    
    /**
     * M??todo que verifica si existen roles duplicados
     * Se implementa el m??todo porque un rol solo puede estar asociado a un solo integrante
     * @return Booleano con el resultado de verificaci??n, devuelve true si existe rol duplicado, de lo contrario, devuelve false
     */
    
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
    
    /**
     * M??todo que verifica si falta asignar un rol
     * @return Booleano con el resultado de verificaci??n, devuelve true si existe asignaci??n faltante, de lo contario, devuelve false
     */
    
    private boolean existsMissingRole(){
        boolean missingRole = false;
        boolean leaderSelected = false;
        boolean secretarySelected = false;
        boolean timeTakerSelected = false;
        for(MeetingAssistant meetingAssistant: tbIntegrants.getItems()){
            if(meetingAssistant.getRbLeaderRole().isSelected()){
                leaderSelected = true;
            }else if(meetingAssistant.getRbSecretaryRole().isSelected()){
                secretarySelected = true;
            }else if(meetingAssistant.getRbTimeTakerRole().isSelected()){
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
    
    /**
     * M??todo que manda a verificar en base de datos si ciertos campos de una reuni??n que no se pueden repetir, se encuentran o no registrados
     * @return Booleano con el resultado de verificaci??n, devuelve true si existe valor duplicado, de lo contario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    private boolean existsDuplicateValuesForMeeting() throws BusinessConnectionException{
        MeetingDAO meetingDAO = new MeetingDAO();
        boolean meetingAffairDuplicate = false;
        if(meetingDAO.existsMeetingAffair(tfAffair.getText())){
            meetingAffairDuplicate = true;
            TypeError typeError = TypeError.MEETINGAFFAIRDUPLICATE;
            showInvalidFieldAlert(typeError);
        }
        
        boolean dateAndTimeDuplicate = false;
        if(!meetingDAO.existsDateAndTimeAvailable(parseToSqlDate(java.util.Date.from(dpMeetingDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())),
           parseToSqlTime(cbHours.getSelectionModel().getSelectedItem().toString(), cbMinutes.getSelectionModel().getSelectedItem().toString()))){
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
    
    /**
     * M??todo que verifica si existen campos inv??lidos para prerequisito
     * El m??todo invoca m??todos de verificaci??n m??s espec??ficos
     * @return Booleano con el resultado de verificaci??n, devuelve true si existen inv??lidos, de lo contario, devuelve false
     */
    
    private boolean existsInvalidFieldsForPrerequisites(){
        boolean invalidFields = false;
        if(existsEmptyFields(tfDescription.getText()) || existsInvalidCharacters(tfDescription.getText()) 
           || existsMissingSelection(cbPrerequisiteManager) || existsDuplicateValueForPrerequisite() || existsInvalidLength(tfDescription.getText())){
            invalidFields = true;
        }
        return invalidFields;
    }
    
    /**
     * M??todo que verifica si existen campos inv??lidos para punto de agenda
     * El m??todo invoca m??todos de verificaci??n m??s espec??ficos
     * @return Booleano con el resultado de verificaci??n, devuelve true si existen inv??lidos, de lo contario, devuelve false 
     */
    
    private boolean existsInvalidFieldsForAgendaPoint(){
        boolean invalidFields = false;
        if(existsEmptyFields(tfTopic.getText()) || existsMissingSelection(cbLeaderDiscussion) ||
           existsInvalidCharacters(tfTopic.getText()) || existsInvalidHours() || existsDuplicateValueForAgendaPoint() || existsInvalidLength(tfTopic.getText())){
            invalidFields = true;
        }
        return invalidFields;
    }
    
    /**
     * M??todo que verifica si existe selecci??n de ComboBox faltante en la GUI
     * @param cbToValidate Define el ComboBox a validar
     * @return Booleano con el resultado de la verificaci??n, devuelve true si existe selecci??n faltante, de lo contrario, devuelve false
     */
    
    private boolean existsMissingSelection(ComboBox cbToValidate){
        boolean missingSelection = false;
        if(cbToValidate.getSelectionModel().getSelectedIndex() < 0){
            missingSelection = true;
            TypeError typeError = TypeError.MISSINGSELECTION;
            showInvalidFieldAlert(typeError);
        }
        return missingSelection;
    }
    
    /**
     * M??todo que verifica si existen horas inv??lidas
     * Se implementa porque es necesario verificar que no existan selecciones faltantes as?? como horas de inicio mayores a la hora de fin 
     * @return Booleano con el resultado de verificaci??n, devuelve true si existen inv??lidas, de lo contrario, devuelve false
     */
    
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
    
    /**
     * M??todo que verifica si existen horas para un punto de agenda ocupadas por otro punto de agenda
     * @return Booleano con el resultado de verificaci??n, devuelve true si est??n ocupadas, de lo contrario devuelve false
     */
    
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
    
    /**
     * M??todo que verifica si la descripci??n de un prerequisito ya se encuentra en otro prerequisito
     * @return Booleano con el resultado de verificaci??n, devuelve true si est?? duplicado, de lo contrario, devuelve false
     */
    
    private boolean existsDuplicateValueForPrerequisite(){
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
    
    /**
     * M??todo que verifica si el tema de un punto de agenda ya se encuentra en otro punto de agenda
     * @return Booleano con el resultado de verificaci??n, devuelve true si est?? duplicado, de lo contrario, devuelve false
     */
    
    private boolean existsDuplicateValueForAgendaPoint(){
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
    
    /**
     * M??todo que limpia los campos de prerequisito
     */
    
    private void cleanFieldsPrerequisite(){
        tfDescription.clear();
        cbPrerequisiteManager.getSelectionModel().clearSelection();
    }
    
    /**
     * M??todo que limpia los campos de punto de agenda
     */
    
    private void cleanFieldsAgendaPoint(){
        cbHourStart.getSelectionModel().clearSelection();
        cbMinuteStart.getSelectionModel().clearSelection();
        cbHourEnd.getSelectionModel().clearSelection();
        cbMinuteEnd.getSelectionModel().clearSelection();
        tfTopic.clear();
        cbLeaderDiscussion.getSelectionModel().clearSelection();
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
        
        if(typeError == TypeError.INVALIDSTRINGS){
            alert.setContentText("Existen caracteres inv??lidos, revisa los textos para poder guardar");
        }
        
        if(typeError == TypeError.MISSINGDATE){
            alert.setContentText("Falta seleccionar la fecha de la reuni??n, selecciona la fecha para poder guardar");
        }
        
        if(typeError == TypeError.MISSINGMEETINGTIME){
            alert.setContentText("Falta seleccionar la hora de la reuni??n, selecciona la hora y minutos para poder guardar");
        }
        
        if(typeError == TypeError.MEETINGAFFAIRDUPLICATE){
            alert.setContentText("El asunto de la reuni??n ya se encuentra registrado en otra reuni??n");
        }
        
        if(typeError == TypeError.DATEANDTIMEDUPLICATE){
            alert.setContentText("La fecha y hora de la reuni??n no se encuentran disponibles debido a que ya existe una reuni??n registrada con dicha fecha y hora");
        }
        
        if(typeError == TypeError.MANYROLES){
            alert.setContentText("Un integrante solo puede tener un rol, modifica los roles para poder guardar");
        }
        
        if(typeError == TypeError.DUPLICATEROLE){
            alert.setContentText("Rol duplicado. Un rol solo puede pertenecer a un integrante, corrija para poder guardar");
        }
        
        if(typeError == TypeError.INCORRETDATE){
            alert.setContentText("La fecha de la reuni??n debe ser una fecha posterior a la fecha actual");
        }
        
        if(typeError == TypeError.MISSINGSELECTION){
            alert.setContentText("Selecci??n faltante, selecciona la informaci??n para poder A??adir");
        }
        
        if(typeError == TypeError.MINORHOUR){
            alert.setContentText("La hora de inicio de un punto de agenda debe ser igual o mayor a la hora de la reuni??n");
        }
        
        if(typeError == TypeError.BUSYTIME){
            alert.setContentText("La hora de inicio o fin del punto de agenda no se encuentra disponible debido a que otro punto de agenda ya tiene esa hora");
        }
        
        if(typeError == TypeError.WRONGTIMEAGENDAPOINT){
            alert.setContentText("La hora de fin no puede ser menor o igual a la hora de inicio de un punto de agenda. Corrije para poder a??adir");
        }
        
        if(typeError == TypeError.COLUMNMISSINGSELECTION){
            alert.setContentText("Selecci??n de fila faltante. Selecciona una fila para poder eliminar");
        }
        
        if(typeError == TypeError.EMPTYTABLE){
            alert.setContentText("La reuni??n debe tener por lo menos un punto de agenda y un prerequisito. A??ade para poder guardar");
        }
        
        if(typeError == TypeError.MISSINGROLE){
            alert.setContentText("Falta asignar rol, por favor asigna el rol a un integrante para poder guardar");
        }
        
        if(typeError == TypeError.DUPLICATEVALUE){
            alert.setContentText("El texto que deseas a??adir ya se encuentra registrado, verifica por favor");
        }
        
        if(typeError == TypeError.INVALIDLENGTH){
            alert.setContentText("El n??mero de car??cteres excede el l??mite permitido (255 caracteres), corrige los campos para poder guardar");
        }   
         
        alert.showAndWait();
    }
    
    /**
     * M??todo que muestra alerta de confirmaci??n de guardado en la base de datos
     */
    
    private void showConfirmationAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmaci??n de guardado");
        alert.setContentText("La informaci??n fue guardada con ??xito");
        alert.showAndWait();
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
        ActionEvent event = null;
        closeNewMeeting(event);
    }
  
}
