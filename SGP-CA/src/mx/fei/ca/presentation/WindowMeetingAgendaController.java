
package mx.fei.ca.presentation;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mx.fei.ca.businesslogic.AgendaPointDAO;
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
 * Clase para representar el controlador del FXML WindowMeetingAgenda
 * @author David Alexander Mijangos Paredes
 * @version 17-06-2021
 */

public class WindowMeetingAgendaController implements Initializable {

    @FXML
    private TableView<MeetingAssistant> tbIntegrants;
    @FXML
    private TableColumn<MeetingAssistant, String> columnIntegrant;
    @FXML
    private TableColumn<MeetingAssistant, String> columnRole;
    @FXML
    private TableView<Prerequisite> tbPrerequisites;
    @FXML
    private TableColumn<Prerequisite, String> columnDescription;
    @FXML
    private TableColumn<Prerequisite, String> columnPrerequisiteManager;
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
    private Label lbNameProject;
    @FXML
    private Label lbMeetingPlace;
    @FXML
    private Label lbAffair;
    @FXML
    private Label lbMeetingDate;
    @FXML
    private Label lbMeetingTime;
    @FXML
    private Label lbState;
    @FXML
    private Label lbUser;
    private Meeting meeting;
    private Integrant integrant;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }  
    
    /**
     * Método que establece el integrante loggeado al sistema, permitiendo proyectar su nombre en la GUI
     * @param integrant Define el integrante a establecer a la GUI
     */
    
    public void setIntegrant(Integrant integrant){
        this.integrant = integrant;
        lbUser.setText(integrant.getNameIntegrant());
    }
    
    /**
     * Método que muestra la información de la reunión en la GUI
     * @param meeting Define la reunión de la cual se mostrará la información
     */
   
    public void showMeetingData(Meeting meeting){
        lbNameProject.setText(meeting.getProjectName());
        lbMeetingPlace.setText(meeting.getMeetingPlace());
        lbAffair.setText(meeting.getAffair());
        lbMeetingDate.setText(convertDateToString(meeting.getMeetingDate()));
        lbMeetingTime.setText(convertTimeToString(meeting.getMeetingTime()));
        lbState.setText(meeting.getState());
        try {
            MeetingAssistantDAO meetingAssistantDAO = new MeetingAssistantDAO();
            ArrayList<MeetingAssistant> meetingAssistants = meetingAssistantDAO.findMeetingAssistantsByIdMeeting(meeting.getIdMeeting());
            PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
            ArrayList<Prerequisite> prerequisites = prerequisiteDAO.findPrerequisitesByIdMeeting(meeting.getIdMeeting());
            AgendaPointDAO agendaPointDAO = new AgendaPointDAO();
            ArrayList<AgendaPoint> agendaPoints = agendaPointDAO.findAgendaPointsByIdMeeting(meeting.getIdMeeting());
            fillMeetingAssistantsTable(meetingAssistants);
            fillPrerequisitesTable(prerequisites);
            fillAgendaPointsTable(agendaPoints);
            meeting.setAssistants(meetingAssistants);
            meeting.setPrerequisites(prerequisites);
            meeting.setAgendaPoints(agendaPoints);
        } catch (BusinessConnectionException ex) {
            showLostConnectionAlert();
        }
        this.meeting = meeting;   
    }
    
    /**
     * Método que llena la tabla de asistentes de reunión de la GUI
     * @param meetingAssistants Define la lista de asistentes de reunión a mostrar
     */
    
    private void fillMeetingAssistantsTable(ArrayList<MeetingAssistant> meetingAssistants){
        columnIntegrant.setCellValueFactory(new PropertyValueFactory("nameAssistant"));
        columnRole.setCellValueFactory(new PropertyValueFactory("role"));
        ObservableList<MeetingAssistant> listMeetingAssistants = FXCollections.observableArrayList(meetingAssistants);
        tbIntegrants.setItems(listMeetingAssistants);
    }
    
    /**
     * Método que llena la tabla de prerequisitos de la GUI
     * @param prerequisites Define la lista de prerequisitos a mostrar
     */
    
    private void fillPrerequisitesTable(ArrayList<Prerequisite> prerequisites){
        columnDescription.setCellValueFactory(new PropertyValueFactory("description"));
        columnPrerequisiteManager.setCellValueFactory(new PropertyValueFactory("prerequisiteManager"));
        ObservableList<Prerequisite> listPrerequisites = FXCollections.observableArrayList(prerequisites);
        tbPrerequisites.setItems(listPrerequisites);
    }
    
    /**
     * Método que llena la tabla de puntos de agenda de la GUI
     * @param agendaPoints Define la lista de puntos de agenda a mostrar
     */
    
    private void fillAgendaPointsTable(ArrayList<AgendaPoint> agendaPoints){
        columnTimeStart.setCellValueFactory(new PropertyValueFactory("startTime"));
        columnTimeEnd.setCellValueFactory(new PropertyValueFactory("endTime"));
        columnTopic.setCellValueFactory(new PropertyValueFactory("topic"));
        columnLeaderDiscussion.setCellValueFactory(new PropertyValueFactory("leader"));
        ObservableList<AgendaPoint> listAgendaPoints = FXCollections.observableArrayList(agendaPoints);
        tbAgendaPoints.setItems(listAgendaPoints);
    }
    
    /**
     * Método que convierte una variable de tipo Date hacia String
     * @param date Define la fecha a converit de Date a String
     * @return String con la fecha convertida
     */
    
    private String convertDateToString(Date date){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String stringDate = dateFormat.format(date);
        return stringDate;
    }
    
    /**
     * Método que convierte una variable de tipo Time hacia String
     * @param time Define la hora a convertir de Time a String
     * @return String con la hora convertida
     */
    
    private String convertTimeToString(Time time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String stringTime = simpleDateFormat.format(time);
        return stringTime;
    }
    
    /**
     * Método que manda a abrir la ventana de modificación de reunión
     * @param event Define el evento generado
     * @throws BusinessConnectionException 
     */

    @FXML
    private void openModifyMeeting(ActionEvent event) throws BusinessConnectionException{
        MeetingDAO meetingDAO = new MeetingDAO();
        if(this.meeting.getState().equals("Pendiente") && meetingDAO.getCurpOfResponsibleMeeting(this.meeting.getIdMeeting()).equals(integrant.getCurp())){ 
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowModifyAgenda.fxml"));
            Parent root = null;
            try {
                root = fxmlLoader.load();
            } catch (IOException ex) {
                Logger.getLogger(WindowMeetingAgendaController.class.getName()).log(Level.SEVERE, null, ex);
            }
            WindowModifyAgendaController windowModifyAgendaController = fxmlLoader.getController();
            windowModifyAgendaController.setIntegrant(integrant);
            windowModifyAgendaController.fillMeetingData(meeting);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
            closeMeetingAgenda(event);
        }else{
            showInvalidActionAlert();
        }       
    }
    
    /**
     * Método que manda a abrir la ventana de minuta de reunión
     * @param event Define el evento generado
     */

    @FXML
    private void openMemorandum(ActionEvent event){
        if(endedMeeting()){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowMemorandum.fxml"));
            Parent root = null;
            try {
                root = fxmlLoader.load();
            } catch (IOException ex) {
                Logger.getLogger(WindowMeetingAgendaController.class.getName()).log(Level.SEVERE, null, ex);
            }
            WindowMemorandumController windowMemorandumController = fxmlLoader.getController();
            windowMemorandumController.setIntegrant(integrant);
            windowMemorandumController.showMeetingData(meeting);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
            closeMeetingAgenda(event);
        }
    }
    
    /**
     * Método que verifica si la reunión tiene estado de Finalizada
     * @return Boolean con el resultado de la verificación, devuelve true si la verificación es correcta, de lo contrario, devuelve false
     */
    
    private boolean endedMeeting(){
        boolean endedMeeting = false;
        if(this.meeting.getState().equals("Finalizada")){
            endedMeeting = true;
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Acción inválida");
            alert.setContentText("Verifica que el estado de la reunión sea FINALIZADA, de lo contrario no podrás ver la minuta");
            alert.showAndWait();
        }
        return endedMeeting;
    }
    
    /** 
     * Método que manda a abrir la ventana de reunión para iniciar una reunión
     * @param event Define el evento generado
     * @throws BusinessConnectionException 
     */
    
    @FXML
    private void openStartMeeting(ActionEvent event) throws BusinessConnectionException{
        MeetingDAO meetingDAO = new MeetingDAO();
        if (this.meeting.getState().equals("Pendiente") && meetingDAO.getCurpOfResponsibleMeeting(this.meeting.getIdMeeting()).equals(integrant.getCurp())){ 
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowMeeting.fxml"));
            Parent root = null;
            try {
                root = fxmlLoader.load();
            } catch (IOException ex) {
                Logger.getLogger(WindowMeetingAgendaController.class.getName()).log(Level.SEVERE, null, ex);
            }
            WindowMeetingController windowMeetingController = fxmlLoader.getController();
            windowMeetingController.setIntegrant(integrant);
            windowMeetingController.showAgendaPoints(this.meeting.getAgendaPoints(), this.meeting.getIdMeeting());
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
            closeMeetingAgenda(event);
        }else{
            showInvalidActionAlert();
        } 
    }
    
    /**
     * Método que cierra la ventana actual agenda de reunión
     * @param event Define el evento generado
     */
    
    @FXML
    private void closeMeetingAgenda(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Método que muestra la alerta de perdida de conexión con la basde de datos
     */
    
    private void showLostConnectionAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Perdida de conexión");
        alert.setContentText("Perdida de conexión con la base de datos, no se pudo guardar. Intente más tarde");
        alert.showAndWait();
    }
    
    /**
     * Método que muestra la alerta de acción inválida en la GUI
     */
    
    private void showInvalidActionAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Acción inválida");
        alert.setContentText("Verifica que el estado de la reunión sea PENDIENTE y verifica que eres la persona que agendó la reunión, de lo contrario "
                            + "no podrás realizar la acción correspondiente");
        alert.showAndWait();
    }
}
