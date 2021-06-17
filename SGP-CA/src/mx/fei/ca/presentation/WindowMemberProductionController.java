package mx.fei.ca.presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mx.fei.ca.businesslogic.ReceptionWorkDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Article;
import mx.fei.ca.domain.Book;
import mx.fei.ca.domain.ChapterBook;
import mx.fei.ca.domain.Integrant;
import mx.fei.ca.domain.ReceptionWork;

/**
 * Clase para representar el controlador del FXML WindowMemberProduction
 * @author David Alexander Mijangos Paredes
 * @version 17-06-2021
 */

public class WindowMemberProductionController implements Initializable {

    @FXML
    private TextField tfEvidenceName;
    @FXML
    private TableView<ReceptionWork> tbReceptionWorks;
    @FXML
    private TableView<?> tbArticles;
    @FXML
    private TableView<?> tbBooks;
    @FXML
    private TableView<?> tbChapterBooks;
    @FXML
    private TableColumn<ReceptionWork, String> columnImpactCAReceptionWork;
    @FXML
    private TableColumn<ReceptionWork, String> columnNameReceptionWork;
    @FXML
    private TableColumn<?, ?> columnImpactCAArticle;
    @FXML
    private TableColumn<?, ?> columnNameArticle;
    @FXML
    private TableColumn<?, ?> columnImpactCABook;
    @FXML
    private TableColumn<?, ?> columnNameBook;
    @FXML
    private TableColumn<?, ?> columnImpactCAChapterBook;
    @FXML
    private TableColumn<?, ?> columnNameChapterBook;
    @FXML
    private Label lbUser;
    private Integrant integrant;
    
    /**
     * Enumerado que representa los tipos de errores específicos al usar el campo de texto para buscar una evidencia
     */
    
    private enum TypeError{
        EMPTYFIELD, INVALIDSTRING;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            openReceptionWorkData();
        } catch (BusinessConnectionException ex) {
            showLostConnectionAlert();
        }
    } 
    
    /**
     * Método que establece el integrante loggeado al sistema, permitiendo proyectar su nombre en la GUI
     * @param integrant Define el integrante a establecer en la GUI
     */
    
    public void setIntegrant(Integrant integrant){
        this.integrant = integrant;
        lbUser.setText(integrant.getNameIntegrant());
        try {  
            recoverEvidences();
        } catch (BusinessConnectionException ex) {
            showLostConnectionAlert();
        }    
    }
    
    /**
     * Método que manda a recuperar las evidencias del integrante loggeado en el sistema y las muestra en la GUI
     * El método invoca a otro clase y método de la capa lógica para la obtención de las evidencias en la base de datos
     * @throws BusinessConnectionException 
     */
    
    public void recoverEvidences() throws BusinessConnectionException{
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        ArrayList<ReceptionWork> receptionWorks = receptionWorkDAO.findLastTwoReceptionWorksByCurpIntegrant(integrant.getCurp()); 
        fillReceptionWorkTable(receptionWorks);
    }
    
    /**
     * Método que llena la tabla de trabajos recepcionales del integrante
     * @param receptionWorks Define la lista de trabajos recepcionales a mostrar en la GUI
     */
    
    private void fillReceptionWorkTable(ArrayList<ReceptionWork> receptionWorks){
        columnImpactCAReceptionWork.setCellValueFactory(new PropertyValueFactory("impactCA"));
        columnNameReceptionWork.setCellValueFactory(new PropertyValueFactory("titleReceptionWork"));
        ObservableList<ReceptionWork> listReceptionWorks = FXCollections.observableArrayList(receptionWorks);
        tbReceptionWorks.setItems(listReceptionWorks);
    }
    
    /**
     * Método que llena la tabla de artículos del integrante
     * @param articles Define la lista de artículos a mostrar en la GUI
     */
    
    private void fillArticlesTable(ArrayList<Article> articles){
        
    }
    
    /**
     * Método que llena la tabla de libros del integrante
     * @param books Define la lista de libros a mostrar en la GUI
     */
    
    private void fillBooksTable(ArrayList<Book> books){
        
    }
    
    /**
     * Método que llena la tabla de capítulos de libro del integrante
     * @param chaptersBook Define la lista de capítulos de libro a mostrar en la GUI
     */
    
    private void fillChaptersBookTable(ArrayList<ChapterBook> chaptersBook){
        
    }
    
    /**
     * Método que manda a abrir la ventana datos de trabajo recepcional de acuerdo al trabajo recepcional seleccionado de la tabla
     * @throws BusinessConnectionException 
     */
    
    private void openReceptionWorkData() throws BusinessConnectionException{
        tbReceptionWorks.setOnMouseClicked((MouseEvent event) -> {
            ReceptionWork receptionWork = tbReceptionWorks.getItems().get(tbReceptionWorks.getSelectionModel().getSelectedIndex());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowReceptionWorkData.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException ex) {
                Logger.getLogger(WindowMemberProductionController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Stage stage = new Stage();
            stage.setScene(scene);
            WindowReceptionWorkDataController windowReceptionWorkDataController = (WindowReceptionWorkDataController) fxmlLoader.getController();
            windowReceptionWorkDataController.setIntegrant(integrant);
            windowReceptionWorkDataController.showReceptionWorkData(receptionWork);
            stage.showAndWait();
            try {
                recoverEvidences();
            } catch (BusinessConnectionException ex) {
                showLostConnectionAlert();
            }
        });      
    }
    
    /**
     * Método que manda a abrir la ventana datos de artículo de acuerdo al artículo seleccionado de la tabla
     * @throws BusinessConnectionException 
     */
    
    private void openArticleData() throws BusinessConnectionException{
        
    }
    
    /**
     * Método que manda a abrir la ventana datos de libro de acuerdo al libro seleccionado de la tabla
     * @throws BusinessConnectionException 
     */
    
    private void openBookData() throws BusinessConnectionException{
        
    }
    
    /**
     * Método que manda a abrir la ventana datos de capítulo de libro de acuerdo al capítulo de libro seleccionado de la tabla
     * @throws BusinessConnectionException 
     */
    
    private void openChapterBookData() throws BusinessConnectionException{
        
    }
    
    /**
     * Método que manda a buscar evidencia de acuerdo a las iniciales del título ingresado en el campo de la GUI
     * @param event Define el evento generado
     */

    @FXML
    private void searchEvidence(ActionEvent event){
        if(!existsInvalidField()){
            try {
                //Mandar a recuperar listas de las evidencias que coincidan con el texto ->QUITAR ESTE COMENTARIO DESPUES
                ArrayList<ReceptionWork> receptionWorks = recoverReceptionWorks();
                
                if(receptionWorks.isEmpty()){  //DEBE PREGUNTAR SI TODAS LAS LISTAS RECUPERADAS ESTAN VACIAS
                    showNoMatchAlert();
                }
            } catch (BusinessConnectionException ex) {
                showLostConnectionAlert();
                closeMemberProduction(event);
            }
  
        }
    }
    
    /**
     * Método que manda a recuperar específicamente los trabajos recepcionales de acuerdo a las iniciales del título ingresado en la GUI
     * @return ArrayList con los trabajos recepcionales que coincidieron con el texto
     * @throws BusinessConnectionException 
     */
    
    private ArrayList<ReceptionWork> recoverReceptionWorks() throws BusinessConnectionException{
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        String titleReceptionWork = tfEvidenceName.getText();
        ArrayList<ReceptionWork> receptionWorks = receptionWorkDAO.findReceptionWorkByInitialesOfTitle(titleReceptionWork, integrant.getCurp()); //Acá debe pasar la curp del que está loggeado
        if(!receptionWorks.isEmpty()){
            fillReceptionWorkTable(receptionWorks);
        }
        return receptionWorks;
    }
    
    /**
     * Método que manda a recuperar específicamente los artículos de acuerdo a las iniciales del título ingresado en la GUI
     * @return ArrayList con los artículos que coincidieron con el texto
     * @throws BusinessConnectionException 
     */
    
    //private ArrayList<Article> recoverArticles() throws BusinessConnectionException{
        
    //}
    
    /**
     * Método que manda a recuperar específicamente los libros de acuerdo a las iniciales del título ingresado en la GUI
     * @return ArrayList con los libros que coincidieron con el texto
     * @throws BusinessConnectionException 
     */
    
   // private ArrayList<Book> recoverBooks() throws BusinessConnectionException{
        
   // }
    
    /**
     * Método que manda a recuperar específicamente los capítulos de libro de acuerdo a las iniciales del título ingresado en la GUI
     * @return ArrayList con los capítulos de libro que coincidieron con el texto
     * @throws BusinessConnectionException 
     */
    
   // private ArrayList<ChapterBook> chaptersBook() throws BusinessConnectionException{
        
   // }
    
    /**
     * Método que manda a abrir la ventana para registrar un nuevo artículo
     * @param event Define el evento generado
     */

    @FXML
    private void openArticleRegistration(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowAddArticle.fxml"));
            Parent root = fxmlLoader.load();
            WindowAddArticleController windowAddArticleController = fxmlLoader.getController();
            //windowAddArticleController.setIntegrant(integrant);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(WindowMemberProductionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Método que manda a abrir la ventana para registrar un nuevo libro
     * @param event Define el evento generado
     * @throws IOException 
     */

    @FXML
    private void openBookRegistration(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowAddBook.fxml"));
        Parent root = fxmlLoader.load();
        WindowAddBookController windowAddBookController = fxmlLoader.getController();
        //windowAddBookController.setIntegrant(integrant);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
    }
    
    /**
     * Método que manda a abrir la ventana para registrar un nuevo capítulo de libro
     * @param event Define el evento generado
     * @throws IOException 
     */

    @FXML
    private void openChapterBookRegistration(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowAddChapterBook.fxml"));
        Parent root = fxmlLoader.load();
        WindowAddChapterBookController windowAddChapterBookController = fxmlLoader.getController();
        //windowAddChapterBookController.setIntegrant(integrant);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
    }
    
    /**
     * Método que manda a abrir la ventana para registrar un nuevo trabajo recepcional
     * @param event Define el evento generado
     * @throws IOException 
     */

    @FXML
    private void openReceptionWorkRegistration(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowAddReceptionWork.fxml"));
        Parent root = fxmlLoader.load();
        WindowAddReceptionWorkController windowAddReceptionWorkController = fxmlLoader.getController();
        windowAddReceptionWorkController.setIntegrant(integrant);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
    }
    
    /**
     * Método que cierra la ventana actual producción del integrante
     * @param event 
     */

    @FXML
    private void closeMemberProduction(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Método que verifica si existen campos inválidos
     * Este método invoca a otros métodos de verificación más específicos
     * @return Booleano con el resultado de la verificación, devuelve true si existen campos inválidos, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidField(){
        boolean invalidField = false;
        if(existsEmptyField() || existsInvalidString()){
            invalidField = true;
        }
        return invalidField;
    }
    
    /**
     * Método que verifica si existen campos de la GUI que estén vacíos
     * @return Booleano con el resultado de la verificación, devuelve true si existen vacíos, de lo contrario, devuelve false
     */
    
    private boolean existsEmptyField(){
        boolean emptyField = false;
        if(tfEvidenceName.getText().isEmpty()){
            emptyField = true;
            TypeError typeError = TypeError.EMPTYFIELD;
            showInvalidFieldAlert(typeError);
        }
        return emptyField;
    }
    
    /**
     * Método que verifica si existen cadenas inválidas en el texto del campo de la GUI
     * @return Booleano con el resultado de verificación, devuelve true si existen inválidas, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidString(){
        boolean invalidString = false;
        Pattern pattern = Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúñÑ\\s\\.,:]+$");
        Matcher matcher = pattern.matcher(tfEvidenceName.getText());
        if(!matcher.find()){
           invalidString = true;
           TypeError typeError = TypeError.INVALIDSTRING;
           showInvalidFieldAlert(typeError);
        }
        return invalidString;
    }
    
    /**
     * Método que muestra alerta de campo inválido de acuerdo al tipo de error
     * @param typeError Define el tipo de error que encontró
     */
    
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
    
    /**
     * Método que muestra alerta de perdida de conexión con la base de datos
     */
    
    private void showLostConnectionAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Perdida de conexión");
        alert.setContentText("Perdida de conexión con la base de datos, no se pudo guardar. Intente más tarde");
        alert.showAndWait();
    }
    
    /**
     * Método que muestra alerta sin coincidencias
     */
    
    private void showNoMatchAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Sin coincidencias");
        alert.setContentText("No se encontró ningún tipo de evidencia que coincida con el texto ingresado");
        alert.showAndWait();
    }
    
}
