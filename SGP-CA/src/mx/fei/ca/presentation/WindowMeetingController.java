
package mx.fei.ca.presentation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author david
 */
public class WindowMeetingController implements Initializable {

    @FXML
    private TableView<?> tbIntegrants;
    @FXML
    private TableColumn<?, ?> columnIntegrant;
    @FXML
    private TableColumn<?, ?> columnLeader;
    @FXML
    private TableColumn<?, ?> columnTimeTaker;
    @FXML
    private TableColumn<?, ?> columnSecretary;
    @FXML
    private TableView<?> tbPrerequisites;
    @FXML
    private TableColumn<?, ?> columnDescription;
    @FXML
    private TableColumn<?, ?> tfPrerequisiteManager;
    @FXML
    private TableView<?> tbAgendaPoints;
    @FXML
    private TableColumn<?, ?> columnTimeStart;
    @FXML
    private TableColumn<?, ?> columnTimeEnd;
    @FXML
    private TableColumn<?, ?> columnTopic;
    @FXML
    private TableColumn<?, ?> columnLeaderDiscussion;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void openModifyMeeting(ActionEvent event) {
    }

    @FXML
    private void openMemorandum(ActionEvent event) {
    }

    @FXML
    private void openStartMeeting(ActionEvent event) {
    }
    
}
