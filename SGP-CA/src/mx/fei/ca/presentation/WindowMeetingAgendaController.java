
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
    private Label lbResponsibleMeeting;
    @FXML
    private Label lbUser;
    private Meeting meeting;
    private Integrant integrant;
    private String curpResponsible;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }  
    
    /**
     * M??todo que establece el integrante loggeado al sistema, permitiendo proyectar su nombre en la GUI
     * @param integrant Define el integrante a establecer a la GUI
     */
    
    public void setIntegrant(Integrant integrant){
        this.integrant = integrant;
        lbUser.setText(integrant.getNameIntegrant());
    }
    
    /**
     * M??todo que muestra la informaci??n de la reuni??n en la GUI
     * @param meeting Define la reuni??n de la cual se mostrar?? la informaci??n
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
            MeetingDAO meetingDAO = new MeetingDAO();
            this.curpResponsible = meetingDAO.getCurpOfResponsibleMeeting(meeting.getIdMeeting());
            IntegrantDAO integrantDAO = new IntegrantDAO();
            lbResponsibleMeeting.setText(integrantDAO.findIntegrantByCurp(curpResponsible).getNameIntegrant());
        } catch (BusinessConnectionException ex) {
            showLostConnectionAlert();
        }
        this.meeting = meeting;   
    }
    
    /**
     * M??todo que llena la tabla de asistentes de reuni??n de la GUI
     * @param meetingAssistants Define la lista de asistentes de reuni??n a mostrar
     */
    
    private void fillMeetingAssistantsTable(ArrayList<MeetingAssistant> meetingAssistants){
        columnIntegrant.setCellValueFactory(new PropertyValueFactory("nameAssistant"));
        columnRole.setCellValueFactory(new PropertyValueFactory("role"));
        ObservableList<MeetingAssistant> listMeetingAssistants = FXCollections.observableArrayList(meetingAssistants);
        tbIntegrants.setItems(listMeetingAssistants);
    }
    
    /**
     * M??todo que llena la tabla de prerequisitos de la GUI
     * @param prerequisites Define la lista de prerequisitos a mostrar
     */
    
    private void fillPrerequisitesTable(ArrayList<Prerequisite> prerequisites){
        columnDescription.setCellValueFactory(new PropertyValueFactory("description"));
        columnPrerequisiteManager.setCellValueFactory(new PropertyValueFactory("prerequisiteManager"));
        ObservableList<Prerequisite> listPrerequisites = FXCollections.observableArrayList(prerequisites);
        tbPrerequisites.setItems(listPrerequisites);
    }
    
    /**
     * M??todo que llena la tabla de puntos de agenda de la GUI
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
     * M??todo que convierte una variable de tipo Date hacia String
     * @param date Define la fecha a converit de Date a String
     * @return String con la fecha convertida
     */
    
    private String convertDateToString(Date date){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String stringDate = dateFormat.format(date);
        return stringDate;
    }
    
    /**
     * M??todo que convierte una variable de tipo Time hacia String
     * @param time Define la hora a convertir de Time a String
     * @return String con la hora convertida
     */
    
    private String convertTimeToString(Time time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String stringTime = simpleDateFormat.format(time);
        return stringTime;
    }
    
    /**
     * M??todo que manda a abrir la ventana de modificaci??n de reuni??n
     * @param event Define el evento generado
     * @throws BusinessConnectionException 
     */

    @FXML
    private void openModifyMeeting(ActionEvent event) throws BusinessConnectionException{
        if(this.meeting.getState().equals("Pendiente") && curpResponsible.equals(integrant.getCurp())){ 
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
     * M??todo que manda a abrir la ventana de minuta de reuni??n
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
     * M??todo que verifica si la reuni??n tiene estado de Finalizada
     * @return Boolean con el resultado de la verificaci??n, devuelve true si la verificaci??n es correcta, de lo contrario, devuelve false
     */
    
    private boolean endedMeeting(){
        boolean endedMeeting = false;
        if(this.meeting.getState().equals("Finalizada")){
            endedMeeting = true;
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Acci??n inv??lida");
            alert.setContentText("Verifica que el estado de la reuni??n sea FINALIZADA, de lo contrario no podr??s ver la minuta");
            alert.showAndWait();
        }
        return endedMeeting;
    }
    
    /** 
     * M??todo que manda a abrir la ventana de reuni??n para iniciar una reuni??n
     * @param event Define el evento generado
     * @throws BusinessConnectionException 
     */
    
    @FXML
    private void openStartMeeting(ActionEvent event) throws BusinessConnectionException{
        if (this.meeting.getState().equals("Pendiente") && curpResponsible.equals(integrant.getCurp())){ 
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
     * M??todo que cierra la ventana actual agenda de reuni??n
     * @param event Define el evento generado
     */
    
    @FXML
    private void closeMeetingAgenda(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    /**
     * M??todo que muestra la alerta de perdida de conexi??n con la basde de datos
     */
    
    private void showLostConnectionAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Perdida de conexi??n");
        alert.setContentText("Perdida de conexi??n con la base de datos, no se pudo guardar. Intente m??s tarde");
        alert.showAndWait();
    }
    
    /**
     * M??todo que muestra la alerta de acci??n inv??lida en la GUI
     */
    
    private void showInvalidActionAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Acci??n inv??lida");
        alert.setContentText("Verifica que el estado de la reuni??n sea PENDIENTE y verifica que eres la persona que agend?? la reuni??n, de lo contrario "
                            + "no podr??s realizar la acci??n correspondiente");
        alert.showAndWait();
    }
}
