
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
import mx.fei.ca.businesslogic.BookDAO;
import mx.fei.ca.businesslogic.IntegrantDAO;
import mx.fei.ca.businesslogic.InvestigationProjectDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Book;
import mx.fei.ca.domain.Evidence;
import mx.fei.ca.domain.Integrant;
import mx.fei.ca.domain.InvestigationProject;

public class WindowAddBookController implements Initializable {

    @FXML
    private TextField tfTitleEvidence;
    @FXML
    private TextField tfAuthor;
    @FXML
    private ComboBox cbImpactCA;
    @FXML
    private ComboBox<InvestigationProject> cbInvestigationProject;
    @FXML
    private TextField tfIsbn;
    @FXML
    private TextField tfPrinting;
    @FXML
    private TextField tfNumPages;
    @FXML
    private TextField tfCountry;
    @FXML
    private TextField tfEdition;
    @FXML
    private TextField tfEditorial;
    @FXML
    private TextField tfFileRoute;
    @FXML
    private DatePicker dpPublicationDate;
    @FXML
    private ComboBox cbActualState;
    @FXML
    private ComboBox cbParticipationType;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillComboBoxImpactCA();
        fillComboBoxActualState();
        fillComboBoxParticipationType();       
        try {
            fillComboBoxInvestigationProject();
        } catch (BusinessConnectionException ex) {
            showLostConnectionAlert();
        }   
    }    

    @FXML    
    private void fillComboBoxImpactCA() {
        ObservableList<String> listImpactCA = FXCollections.observableArrayList("SI","NO");
        cbImpactCA.setItems(listImpactCA);
    }
    
    @FXML
    private void fillComboBoxActualState() {
        ObservableList<String> listActualState = FXCollections.observableArrayList("En proceso","Terminado", "Publicado");
        cbActualState.setItems(listActualState);
    }

    @FXML    
    private void fillComboBoxParticipationType() {
        ObservableList<String> listParticipationType = FXCollections.observableArrayList("Estudiante");
        cbParticipationType.setItems(listParticipationType);
    }    

    @FXML    
    private void fillComboBoxInvestigationProject() throws BusinessConnectionException{
        InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
        ArrayList<InvestigationProject> investigationProjects = investigationProjectDAO.findAllInvestigationProjects();
        ObservableList<InvestigationProject> listInvestigationProject = FXCollections.observableArrayList(investigationProjects);
        cbInvestigationProject.setItems(listInvestigationProject);
    }
    
    @FXML
    private void saveBook(ActionEvent event) throws BusinessConnectionException {
        String impactCA = cbImpactCA.getSelectionModel().getSelectedItem().toString();
        String isbn = tfIsbn.getText();
        int priting = Integer.parseInt(tfPrinting.getText());   
        int numPages = Integer.parseInt(tfNumPages.getText()); 
        String participationType = cbParticipationType.getSelectionModel().getSelectedItem().toString();
        String actualState = cbActualState.getSelectionModel().getSelectedItem().toString();        
        String country = tfCountry.getText();   
        Date publicationDate = parseToSqlDate(java.util.Date.from(dpPublicationDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));      
        String editorial = tfEditorial.getText();           
        String edition = tfEdition.getText();          
        String fileRoute = tfFileRoute.getText();
        String author = tfAuthor.getText(); 
        String titleBook = tfTitleEvidence.getText();        
        InvestigationProject investigationProject = cbInvestigationProject.getSelectionModel().getSelectedItem(); 
        int idProject = investigationProject.getIdProject();
        Evidence evidence = new Evidence(impactCA, titleBook, author);
        /*Book book = new Book(evidence, isbn, priting, numPages, participationType, actualState, country, publicationDate,
                            editorial, edition, fileRoute);
        book.setIdProject(idProject);
        IntegrantDAO integrantDAO = new IntegrantDAO();
        Integrant integrant = integrantDAO.findIntegrantByCurp("JCPA940514RDTREOP1"); 
        book.setIntegrant(integrant);
        BookDAO bookDAO = new BookDAO();
        boolean saveResult = bookDAO.savedBook(book);
        if(saveResult){
            showConfirmationAlert();
            closeBookRegistration(event);
        }else{
            showLostConnectionAlert(); 
            closeBookRegistration(event);
        }*/
    }

    @FXML
    private void closeBookRegistration(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private java.sql.Date parseToSqlDate(java.util.Date date){
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        return sqlDate;
    }

    @FXML
    private void showConfirmationAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación de guardado");
        alert.setContentText("La información del libro fue guardada con éxito");
        alert.showAndWait();
    }
    
    @FXML
    private void showLostConnectionAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Perdida de conexión");
        alert.setContentText("Perdida de conexión con la base de datos, no se pudo guardar. Intente más tarde");
        alert.showAndWait();
    }
}
