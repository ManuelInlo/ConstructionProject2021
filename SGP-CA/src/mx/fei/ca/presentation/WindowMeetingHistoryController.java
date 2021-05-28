
package mx.fei.ca.presentation;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mx.fei.ca.businesslogic.MeetingDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Integrant;
import mx.fei.ca.domain.Meeting;

/**
 * FXML Controller class
 *
 * @author david
 */
public class WindowMeetingHistoryController implements Initializable {

    @FXML
    private TextField tfMeetingProject;
    @FXML
    private DatePicker dpMeetingDate;
    @FXML
    private TableView<Meeting> tbMeetingHistory;
    @FXML
    private TableColumn<Meeting, String> columnProject;
    @FXML
    private TableColumn<Meeting, Date> columnDate;
    @FXML
    private TableColumn<Meeting, Time> columnTime;
    
    private enum TypeError{
        EMPTYFIELD, INVALIDSTRING;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        try{
            MeetingDAO meetingDAO = new MeetingDAO();
            ArrayList<Meeting> listMeetings = meetingDAO.findLastFiveMeetings();
            fillMeetingHistory(listMeetings);
        } catch (BusinessConnectionException ex) {
            showLostConnectionAlert();
            ActionEvent event = null;
            exitMeetingHistory(event);
        }
        openMeetingAgenda();
    }  
    
    private void fillMeetingHistory(ArrayList<Meeting> listMeetings) throws BusinessConnectionException{
        columnProject.setCellValueFactory(new PropertyValueFactory("projectName"));
        columnDate.setCellValueFactory(new PropertyValueFactory("meetingDate"));
        columnTime.setCellValueFactory(new PropertyValueFactory("meetingTime"));
        ObservableList<Meeting> meetings = FXCollections.observableArrayList(listMeetings);
        tbMeetingHistory.setItems(meetings);
    }
    
    @FXML
    private void searchMeeting(ActionEvent event) throws BusinessConnectionException{
        MeetingDAO meetingDAO = new MeetingDAO();
        ArrayList<Meeting> listMeetings;
        if(!existsInvalidField()){
            if(dpMeetingDate.getValue() == null){
                listMeetings = meetingDAO.findMeetingsByProjectName(tfMeetingProject.getText());
            }else{
                Date meetingDate = parseToSqlDate(java.util.Date.from(dpMeetingDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                listMeetings = meetingDAO.findMeetingsByProjectNameAndDate(tfMeetingProject.getText(), meetingDate);
            }
            if(listMeetings.isEmpty()){
                showNoMatchAlert();
            }else{
                fillMeetingHistory(listMeetings);
            }
            cleanFields();
        }
    }
    
    public void openMeetingAgenda(){
        tbMeetingHistory.setOnMouseClicked((MouseEvent event) -> {
            Meeting meeting = tbMeetingHistory.getItems().get(tbMeetingHistory.getSelectionModel().getSelectedIndex());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowMeetingAgenda.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException ex) {
                Logger.getLogger(WindowMeetingHistoryController.class.getName()).log(Level.SEVERE, null, ex);
            }   Stage stage = new Stage();
            stage.setScene(scene);
            WindowMeetingAgendaController windowMeetingAgendaController = (WindowMeetingAgendaController) fxmlLoader.getController();
            windowMeetingAgendaController.showMeetingData(meeting);
            stage.show();
        });
    }
    
    private java.sql.Date parseToSqlDate(java.util.Date date){
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        return sqlDate;
    }

    @FXML
    private void scheduleMeeting(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowNewMeeting.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void exitMeetingHistory(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    private boolean existsInvalidField(){
        boolean invalidField = false;
        if(existsEmptyField() || existsInvalidString()){
            invalidField = true;
        }
        return invalidField;
    }
    
    private boolean existsEmptyField(){
        boolean emptyField = false;
        if(tfMeetingProject.getText().isEmpty()){
            emptyField = true;
            TypeError typeError = TypeError.EMPTYFIELD;
            showInvalidFieldAlert(typeError);
        }
        return emptyField;
    }
    
    private boolean existsInvalidString(){
        boolean invalidString = false;
        Pattern pattern = Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúñÑ\\s]+$");
        Matcher matcher = pattern.matcher(tfMeetingProject.getText());
        if(!matcher.find()){
           invalidString = true;
           TypeError typeError = TypeError.INVALIDSTRING;
           showInvalidFieldAlert(typeError);
        }
        return invalidString;
    }
    
    private void showInvalidFieldAlert(TypeError typeError){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Campo inválido");
        if(typeError == TypeError.EMPTYFIELD){
            alert.setContentText("Existe campo vacío, llena el campo para poder buscar reunión");
        }
        if(typeError == TypeError.INVALIDSTRING){
            alert.setContentText("Existe caracter inválido, revisa el texto para poder buscar reunión");
        }
        alert.showAndWait();
    }
    
    private void showNoMatchAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Sin coincidencias");
        alert.setContentText("No se encontrarón coincidencias con la reunión buscada");
        alert.showAndWait();
    }
    
    private void cleanFields(){
        tfMeetingProject.clear();
        dpMeetingDate.getEditor().clear();
    }
    
    private void showLostConnectionAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Perdida de conexión");
        alert.setContentText("Perdida de conexión con la base de datos, no se pudo guardar. Intente más tarde");
        alert.showAndWait();
    }
    
}