
package mx.fei.ca.presentation;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.fei.ca.businesslogic.MeetingDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Meeting;

/**
 * FXML Controller class
 *
 * @author david
 */
public class WindowNewMeetingController implements Initializable {

   /* private TextField tfProjectName;
    private TextField tfMeetingPlace;
    private DatePicker dpMeetingDate;
    private ComboBox cbHours;
    private ComboBox cbMinutes;
    private TextField tfAffair;
   

    private enum TypeError{
        EMPTYFIELDS, INVALIDSTRINGS, MISSINGMEETINGTIME, MISSINGDATE, MEETINGAFFAIRDUPLICATE, DATEANDTIMEDUPLICATE;
    }
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //fillComboBoxHours();
        //fillComboBoxMinutes();
    }   
    /*
    private void scheduleMeeting(ActionEvent event){
        if(!existsInvalidFields()){
            String projectName = tfProjectName.getText();
            String meetingPlace = tfMeetingPlace.getText();
            String affair = tfAffair.getText();
            Date meetingDate = parseToSqlDate(java.util.Date.from(dpMeetingDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            Time meetingTime = parseToSqlTime(cbHours.getSelectionModel().getSelectedItem().toString(), cbMinutes.getSelectionModel().getSelectedItem().toString());
            
        }
    }

    private void closeNewMeeting(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    private void fillComboBoxHours(){
        ObservableList<String> listHours = FXCollections.observableArrayList("01","02","03","04","05","06","07","08","09","10",
                                                                             "11","12","13","14","15","16","17","18","19",
                                                                             "20","21","22","23","24");
        cbHours.setItems(listHours);
    }
    
    private void fillComboBoxMinutes(){
        ObservableList<String> listMinutes = FXCollections.observableArrayList("00","01","02","03","04","05","06","07","08","09","10",
                                                                             "11","12","13","14","15","16","17","18","19",
                                                                             "20","21","22","23","24","25","26","27","28","29",
                                                                             "30","31","32","33","34","35","36","37","38","39",
                                                                             "40","41","42","43","44","45","46","47","48","49",
                                                                             "50","51","52","53","54","55","56","57","58","59",
                                                                             "60");
        cbMinutes.setItems(listMinutes);
    }
    
    private java.sql.Date parseToSqlDate(java.util.Date date){
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        return sqlDate;
    }
    
    private java.sql.Time parseToSqlTime(String hours, String minutes){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
        java.sql.Time meetingTime = null;
        try {
            meetingTime = new java.sql.Time(simpleDateFormat.parse(hours+":"+minutes).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(WindowNewMeetingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return meetingTime;
    }
    private boolean existsInvalidFields(){
        boolean invalidFields = false;
        if(existsEmptyFields() || existsInvalidStrings() || existsMissingDate() || existsMissingMeetingTime()){
            invalidFields = true;
        }
        return invalidFields;
    }
    
    private boolean existsEmptyFields(){
        boolean emptyFields = false;
        if(tfProjectName.getText().isEmpty() || tfMeetingPlace.getText().isEmpty() || tfAffair.getText().isEmpty()){
            emptyFields = true;
            TypeError typeError = TypeError.EMPTYFIELDS;
            showInvalidFieldAlert(typeError);     
        }
        return emptyFields;
    }
    
    private boolean existsInvalidStrings(){
        boolean invalidStrings = false;
        if(existsInvalidCharactersForName(tfProjectName.getText()) || existsInvalidCharacters(tfMeetingPlace.getText()) || 
           existsInvalidCharacters(tfAffair.getText())){
            invalidStrings = true;
            TypeError typeError = TypeError.INVALIDSTRINGS;
            showInvalidFieldAlert(typeError);
        }
        return invalidStrings;
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
    
    private boolean existsMissingMeetingTime(){
        boolean missingMeetingTime = false;
        if(cbHours.getSelectionModel().getSelectedIndex() < 0 || cbMinutes.getSelectionModel().getSelectedIndex() < 0){
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
        }
        return invalidCharactersForName;
    }
    
    private boolean existsInvalidCharacters(String textToValidate){
        boolean invalidCharacters = false;
        Pattern pattern = Pattern.compile("^[0-9A-Za-zÁÉÍÓÚáéíóúñÑ\\s\\.,]+$");
        Matcher matcher = pattern.matcher(textToValidate);
        if(!matcher.find()){
           invalidCharacters = true; 
        }
        return invalidCharacters;
    }
    
    private boolean existsDuplicateValues(Meeting meeting) throws BusinessConnectionException{
        MeetingDAO meetingDAO = new MeetingDAO();
        boolean meetingAffairDuplicate = false;
        if(meetingDAO.existsMeetingAffair(meeting.getAffair())){
            meetingAffairDuplicate = true;
            TypeError typeError = TypeError.MEETINGAFFAIRDUPLICATE;
            showInvalidFieldAlert(typeError);
        }
        
        boolean dateAndTimeDuplicate = false;
        if(!meetingDAO.existsDateAndTimeAvailable(meeting.getMeetingDate(), meeting.getMeetingTime())){
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
    
    private void showInvalidFieldAlert(TypeError typeError){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Campo inválido");
        if(typeError == TypeError.EMPTYFIELDS){
          alert.setContentText("Existen campos vacíos, llena los campos para poder guardar");  
        }
        
        if(typeError == TypeError.INVALIDSTRINGS){
            alert.setContentText("Existen caracteres inválidos, revisa los textos para poder guardar");
        }
        
        if(typeError == TypeError.MISSINGDATE){
            alert.setContentText("Falta seleccionar la fecha de la reunión, selecciona la fecha para poder guardar");
        }
        
        if(typeError == TypeError.MISSINGMEETINGTIME){
            alert.setContentText("Falta seleccionar la hora de la reunión, selecciona la hora y minutos para poder guardar");
        }
        
        if(typeError == TypeError.MEETINGAFFAIRDUPLICATE){
            alert.setContentText("El asunto de la reunión ya se encuentra registrado en otra reunión");
        }
        
        if(typeError == TypeError.MEETINGAFFAIRDUPLICATE){
            alert.setContentText("La fecha y hora de la reunión no se encuentran disponibles debido a que ya existe una reunión registrada con dicha fecha y hora");
        }
        alert.showAndWait();
    }
    
    private void showConfirmationAlert(boolean saveResult){
        if(saveResult){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Confirmación de guardado");
            alert.setContentText("La información fue guardada con éxito");
            alert.showAndWait();
        }
        
    }
    */
}
