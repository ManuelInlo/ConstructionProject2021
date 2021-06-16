
package mx.fei.ca.presentation;

import java.net.URL;
import java.sql.Date;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
import mx.fei.ca.businesslogic.InvestigationProjectDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.InvestigationProject;

public class WindowAddArticleController implements Initializable {

    @FXML
    private TextField tfTitleEvidence;
    @FXML
    private TextField tfAuthor;
    @FXML
    private TextField tfEndPage;
    @FXML
    private TextField tfEditorial;
    @FXML
    private TextField tfCountry;
    @FXML
    private TextField tfFileRoute;
    @FXML
    private DatePicker dpPublicationDate;
    @FXML
    private TextField tfVolume;
    @FXML
    private ComboBox cbImpactCA;
    @FXML
    private ComboBox<InvestigationProject> cbInvestigationProject;
    @FXML
    private ComboBox cbActualState;
    @FXML
    private TextField tfDescription;
    @FXML
    private TextField tfHomePage;
    @FXML
    private TextField tfIssn;
    @FXML
    private TextField tfMagazineName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillComboBoxImpactCA();
        fillComboBoxActualState();
        try {
            fillComboBoxInvestigationProject();
        } catch (BusinessConnectionException ex) {
            showLostConnectionAlert();
        } 
    }    

    private void fillComboBoxImpactCA() {
        ObservableList<String> listImpactCA = FXCollections.observableArrayList("SI","NO");
        cbImpactCA.setItems(listImpactCA);
    }
    
    private void fillComboBoxActualState() {
        ObservableList<String> listActualState = FXCollections.observableArrayList("En proceso","Terminado", "Publicado");
        cbActualState.setItems(listActualState);
    }  

    private void fillComboBoxInvestigationProject() throws BusinessConnectionException{
        InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
        ArrayList<InvestigationProject> investigationProjects = investigationProjectDAO.findAllInvestigationProjects();
        ObservableList<InvestigationProject> listInvestigationProject = FXCollections.observableArrayList(investigationProjects);
        cbInvestigationProject.setItems(listInvestigationProject);
    }
    
    @FXML
    private void saveArticle(ActionEvent event) throws BusinessConnectionException {
        String impactCA = cbImpactCA.getSelectionModel().getSelectedItem().toString();
        String issn = tfIssn.getText();
        String magazineName = tfMagazineName.getText();
        int endPage = Integer.parseInt(tfEndPage.getText());   
        int homePages = Integer.parseInt(tfHomePage.getText()); 
        String actualState = cbActualState.getSelectionModel().getSelectedItem().toString();        
        String country = tfCountry.getText();   
        Date publicationDate = parseToSqlDate(java.util.Date.from(dpPublicationDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));      
        String editorial = tfEditorial.getText();           
        String volume = tfVolume.getText();     
        String description = tfDescription.getText();         
        String fileRoute = tfFileRoute.getText();
        String author = tfAuthor.getText(); 
        String titleBook = tfTitleEvidence.getText();        
        InvestigationProject investigationProject = cbInvestigationProject.getSelectionModel().getSelectedItem(); 
        int idProject = investigationProject.getIdProject();
    }

    @FXML
    private void closeArticleRegistration(ActionEvent event)  {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private java.sql.Date parseToSqlDate(java.util.Date date){
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        return sqlDate;
    }

    private void showConfirmationAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación de guardado");
        alert.setContentText("La información del artículo fue guardada con éxito");
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
