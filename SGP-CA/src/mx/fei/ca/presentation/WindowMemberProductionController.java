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
import mx.fei.ca.businesslogic.ArticleDAO;
import mx.fei.ca.businesslogic.BookDAO;
import mx.fei.ca.businesslogic.ChapterBookDAO;
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
    private TableView<Article> tbArticles;
    @FXML
    private TableView<Book> tbBooks;
    @FXML
    private TableView<ChapterBook> tbChapterBooks;
    @FXML
    private TableColumn<ReceptionWork, String> columnImpactCAReceptionWork;
    @FXML
    private TableColumn<ReceptionWork, String> columnNameReceptionWork;
    @FXML
    private TableColumn<Article, String> columnImpactCAArticle;
    @FXML
    private TableColumn<Article, String> columnNameArticle;
    @FXML
    private TableColumn<Book, String> columnImpactCABook;
    @FXML
    private TableColumn<Book, String> columnNameBook;
    @FXML
    private TableColumn<ChapterBook, String> columnImpactCAChapterBook;
    @FXML
    private TableColumn<ChapterBook, String> columnNameChapterBook;
    @FXML
    private Label lbUser;
    private Integrant integrant;
    
    /**
     * Enumerado que representa los tipos de errores espec??ficos al usar el campo de texto para buscar una evidencia
     */
    
    private enum TypeError{
        EMPTYFIELD, INVALIDSTRING;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            openReceptionWorkData();
            openArticleData();
        } catch (BusinessConnectionException ex) {
            showLostConnectionAlert();
        }
    } 
    
    /**
     * M??todo que establece el integrante loggeado al sistema, permitiendo proyectar su nombre en la GUI
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
     * M??todo que manda a recuperar las evidencias del integrante loggeado en el sistema y las muestra en la GUI
     * El m??todo invoca a otro clase y m??todo de la capa l??gica para la obtenci??n de las evidencias en la base de datos
     * @throws BusinessConnectionException 
     */
    
    public void recoverEvidences() throws BusinessConnectionException{
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        ArrayList<ReceptionWork> receptionWorks = receptionWorkDAO.findLastTwoReceptionWorksByCurpIntegrant(integrant.getCurp()); 
        ArticleDAO articleDAO = new ArticleDAO();
        ArrayList<Article> articles = articleDAO.findLastTwoArticlesByCurpIntegrant(integrant.getCurp());
        BookDAO bookDAO = new BookDAO();
        ArrayList<Book> books = bookDAO.findLastTwoBooksByCurpIntegrant(integrant.getCurp());
        ChapterBookDAO chapterBookDAO = new ChapterBookDAO();
        ArrayList<ChapterBook> chaptersBook = chapterBookDAO.findLastTwoChapterBooksByCurpIntegrant(integrant.getCurp());
        fillReceptionWorkTable(receptionWorks);
        fillArticlesTable(articles);
        fillBooksTable(books);
        fillChaptersBookTable(chaptersBook);        
    }
    
    /**
     * M??todo que llena la tabla de trabajos recepcionales del integrante
     * @param receptionWorks Define la lista de trabajos recepcionales a mostrar en la GUI
     */
    
    private void fillReceptionWorkTable(ArrayList<ReceptionWork> receptionWorks){
        columnImpactCAReceptionWork.setCellValueFactory(new PropertyValueFactory("impactCA"));
        columnNameReceptionWork.setCellValueFactory(new PropertyValueFactory("titleReceptionWork"));
        ObservableList<ReceptionWork> listReceptionWorks = FXCollections.observableArrayList(receptionWorks);
        tbReceptionWorks.setItems(listReceptionWorks);
    }
    
    /**
     * M??todo que llena la tabla de art??culos del integrante
     * @param articles Define la lista de art??culos a mostrar en la GUI
     */
    
    private void fillArticlesTable(ArrayList<Article> articles){
        columnImpactCAArticle.setCellValueFactory(new PropertyValueFactory("impactCA"));
        columnNameArticle.setCellValueFactory(new PropertyValueFactory("titleEvidence"));
        ObservableList<Article> listArticles = FXCollections.observableArrayList(articles);
        tbArticles.setItems(listArticles);
    }
    
    /**
     * M??todo que llena la tabla de libros del integrante
     * @param books Define la lista de libros a mostrar en la GUI
     */
    
    private void fillBooksTable(ArrayList<Book> books){
        columnImpactCABook.setCellValueFactory(new PropertyValueFactory("impactCA"));
        columnNameBook.setCellValueFactory(new PropertyValueFactory("titleEvidence"));
        ObservableList<Book> listBooks = FXCollections.observableArrayList(books);
        tbBooks.setItems(listBooks);
    }
    
    /**
     * M??todo que llena la tabla de cap??tulos de libro del integrante
     * @param chaptersBook Define la lista de cap??tulos de libro a mostrar en la GUI
     */
    
    private void fillChaptersBookTable(ArrayList<ChapterBook> chaptersBook){
        columnImpactCAChapterBook.setCellValueFactory(new PropertyValueFactory("impactCA"));
        columnNameChapterBook.setCellValueFactory(new PropertyValueFactory("titleEvidence"));
        ObservableList<ChapterBook> listChaptersBook = FXCollections.observableArrayList(chaptersBook);
        tbChapterBooks.setItems(listChaptersBook);
    }
    
    /**
     * M??todo que manda a abrir la ventana datos de trabajo recepcional de acuerdo al trabajo recepcional seleccionado de la tabla
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
            stage.show();
            try {
                recoverEvidences();
            } catch (BusinessConnectionException ex) {
                showLostConnectionAlert();
            }
        });      
    }
    
    /**
     * M??todo que manda a abrir la ventana datos de art??culo de acuerdo al art??culo seleccionado de la tabla
     * @throws BusinessConnectionException 
     */
    
    private void openArticleData() throws BusinessConnectionException{
        tbArticles.setOnMouseClicked((MouseEvent event) -> {
            Article article = tbArticles.getItems().get(tbArticles.getSelectionModel().getSelectedIndex());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowArticleData.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException ex) {
                Logger.getLogger(WindowMemberProductionController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Stage stage = new Stage();
            stage.setScene(scene);
            WindowArticleDataController windowArticleDataController = (WindowArticleDataController) fxmlLoader.getController();
            windowArticleDataController.setIntegrant(integrant);
            windowArticleDataController.showArticleData(article);
            stage.show();
            try {
                recoverEvidences();
            } catch (BusinessConnectionException ex) {
                showLostConnectionAlert();
            }
        });  
    }
    
    /**
     * M??todo que manda a abrir la ventana datos de libro de acuerdo al libro seleccionado de la tabla
     * @throws BusinessConnectionException 
     */
    
    private void openBookData() throws BusinessConnectionException{
        tbBooks.setOnMouseClicked((MouseEvent event) -> {
            Book book = tbBooks.getItems().get(tbBooks.getSelectionModel().getSelectedIndex());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowBookData.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException ex) {
                Logger.getLogger(WindowMemberProductionController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Stage stage = new Stage();
            stage.setScene(scene);
            WindowBookDataController windowBookDataController = (WindowBookDataController) fxmlLoader.getController();
            windowBookDataController.setIntegrant(integrant);
            windowBookDataController.showBookData(book);
            stage.show();
            try {
                recoverEvidences();
            } catch (BusinessConnectionException ex) {
                showLostConnectionAlert();
            }
        }); 
    }
    
    /**
     * M??todo que manda a abrir la ventana datos de cap??tulo de libro de acuerdo al cap??tulo de libro seleccionado de la tabla
     * @throws BusinessConnectionException 
     */
    
    private void openChapterBookData() throws BusinessConnectionException{
        tbChapterBooks.setOnMouseClicked((MouseEvent event) -> {
            ChapterBook chapterBook = tbChapterBooks.getItems().get(tbChapterBooks.getSelectionModel().getSelectedIndex());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowChapterBookData.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException ex) {
                Logger.getLogger(WindowMemberProductionController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Stage stage = new Stage();
            stage.setScene(scene);
            WindowChapterBookDataController windowChapterBookDataController = (WindowChapterBookDataController) fxmlLoader.getController();
            windowChapterBookDataController.setIntegrant(integrant);
            windowChapterBookDataController.showChapterBookData(chapterBook);
            stage.show();
            try {
                recoverEvidences();
            } catch (BusinessConnectionException ex) {
                showLostConnectionAlert();
            }
        }); 
    }
    
    /**
     * M??todo que manda a buscar evidencia de acuerdo a las iniciales del t??tulo ingresado en el campo de la GUI
     * @param event Define el evento generado
     */

    @FXML
    private void searchEvidence(ActionEvent event){
        if(!existsInvalidField()){
            try {
                ArrayList<ReceptionWork> receptionWorks = recoverReceptionWorks();
                ArrayList<Article> articles = recoverArticles();
                ArrayList<Book> books = recoverBooks();
                ArrayList<ChapterBook> chaptersBook = recoverChaptersBook();
                if(receptionWorks.isEmpty() && articles.isEmpty() && books.isEmpty() && chaptersBook.isEmpty()){  
                    showNoMatchAlert();
                }
            } catch (BusinessConnectionException ex) {
                showLostConnectionAlert();
                closeMemberProduction(event);
            }
        }
    }
    
    /**
     * M??todo que manda a recuperar espec??ficamente los trabajos recepcionales de acuerdo a las iniciales del t??tulo ingresado en la GUI
     * @return ArrayList con los trabajos recepcionales que coincidieron con el texto
     * @throws BusinessConnectionException 
     */
    
    private ArrayList<ReceptionWork> recoverReceptionWorks() throws BusinessConnectionException{
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        String titleReceptionWork = tfEvidenceName.getText();
        ArrayList<ReceptionWork> receptionWorks = receptionWorkDAO.findReceptionWorkByInitialesOfTitle(titleReceptionWork, integrant.getCurp()); 
        if(!receptionWorks.isEmpty()){
            fillReceptionWorkTable(receptionWorks);
        }
        return receptionWorks;
    }
    
    /**
     * M??todo que manda a recuperar espec??ficamente los art??culos de acuerdo a las iniciales del t??tulo ingresado en la GUI
     * @return ArrayList con los art??culos que coincidieron con el texto
     * @throws BusinessConnectionException 
     */
    
    private ArrayList<Article> recoverArticles() throws BusinessConnectionException{
        ArticleDAO articleDAO = new ArticleDAO();
        String titleArticle = tfEvidenceName.getText();
        ArrayList<Article> articles = articleDAO.findArticleByInitialesOfTitle(titleArticle, integrant.getCurp());
        if(!articles.isEmpty()){
            fillArticlesTable(articles);
        }
        return articles;
    }
    
    /**
     * M??todo que manda a recuperar espec??ficamente los libros de acuerdo a las iniciales del t??tulo ingresado en la GUI
     * @return ArrayList con los libros que coincidieron con el texto
     * @throws BusinessConnectionException 
     */
    
    private ArrayList<Book> recoverBooks() throws BusinessConnectionException{
        BookDAO bookDAO = new BookDAO();
        String titleBook = tfEvidenceName.getText();
        ArrayList<Book> books = bookDAO.findBookByInitialesOfTitle(titleBook, integrant.getCurp());
        if(!books.isEmpty()){
            fillBooksTable(books);
        }
        return books;
    }
    
    /**
     * M??todo que manda a recuperar espec??ficamente los cap??tulos de libro de acuerdo a las iniciales del t??tulo ingresado en la GUI
     * @return ArrayList con los cap??tulos de libro que coincidieron con el texto
     * @throws BusinessConnectionException 
     */
    
    private ArrayList<ChapterBook> recoverChaptersBook() throws BusinessConnectionException{
        ChapterBookDAO chapterBookDAO = new ChapterBookDAO();
        String titleChapterBook = tfEvidenceName.getText();
        ArrayList<ChapterBook> chaptersBook = chapterBookDAO.findChapterBookByCurpIntegrantInitialesOfTitle(titleChapterBook, integrant.getCurp());
        if(!chaptersBook.isEmpty()){
            fillChaptersBookTable(chaptersBook);
        }
        return chaptersBook;
    }
    
    /**
     * M??todo que manda a abrir la ventana para registrar un nuevo art??culo
     * @param event Define el evento generado
     */

    @FXML
    private void openArticleRegistration(ActionEvent event){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowAddArticle.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException ex) {
            Logger.getLogger(WindowMemberProductionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        WindowAddArticleController windowAddArticleController = (WindowAddArticleController) fxmlLoader.getController();
        windowAddArticleController.setIntegrant(integrant);
        stage.showAndWait();
        closeMemberProduction(event);
    }
    
    /**
     * M??todo que manda a abrir la ventana para registrar un nuevo libro
     * @param event Define el evento generado
     */

    @FXML
    private void openBookRegistration(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowAddBook.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException ex) {
            Logger.getLogger(WindowMemberProductionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        WindowAddBookController windowAddBookController = (WindowAddBookController) fxmlLoader.getController();
        windowAddBookController.setIntegrant(integrant);
        stage.showAndWait();
        closeMemberProduction(event);
    }
    
    /**
     * M??todo que manda a abrir la ventana para registrar un nuevo cap??tulo de libro
     * @param event Define el evento generado
     */

    @FXML
    private void openChapterBookRegistration (ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowAddChapterBook.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException ex) {
            Logger.getLogger(WindowMemberProductionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        WindowAddChapterBookController windowAddChapterBookController = (WindowAddChapterBookController) fxmlLoader.getController();
        windowAddChapterBookController.setIntegrant(integrant);
        stage.showAndWait();
        closeMemberProduction(event);
    }
    
    /**
     * M??todo que manda a abrir la ventana para registrar un nuevo trabajo recepcional
     * @param event Define el evento generado
     */

    @FXML
    private void openReceptionWorkRegistration(ActionEvent event){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowAddReceptionWork.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException ex) {
            Logger.getLogger(WindowMemberProductionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        WindowAddReceptionWorkController windowAddReceptionWorkController = (WindowAddReceptionWorkController) fxmlLoader.getController();
        windowAddReceptionWorkController.setIntegrant(integrant);
        stage.showAndWait();
        closeMemberProduction(event);
    }
    
    /**
     * M??todo que cierra la ventana actual producci??n del integrante
     * @param event 
     */

    @FXML
    private void closeMemberProduction(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    /**
     * M??todo que verifica si existen campos inv??lidos
     * Este m??todo invoca a otros m??todos de verificaci??n m??s espec??ficos
     * @return Booleano con el resultado de la verificaci??n, devuelve true si existen campos inv??lidos, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidField(){
        boolean invalidField = false;
        if(existsEmptyField() || existsInvalidString()){
            invalidField = true;
        }
        return invalidField;
    }
    
    /**
     * M??todo que verifica si existen campos de la GUI que est??n vac??os
     * @return Booleano con el resultado de la verificaci??n, devuelve true si existen vac??os, de lo contrario, devuelve false
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
     * M??todo que verifica si existen cadenas inv??lidas en el texto del campo de la GUI
     * @return Booleano con el resultado de verificaci??n, devuelve true si existen inv??lidas, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidString(){
        boolean invalidString = false;
        Pattern pattern = Pattern.compile("^[A-Za-z????????????????????????\\s\\.,:]+$");
        Matcher matcher = pattern.matcher(tfEvidenceName.getText());
        if(!matcher.find()){
           invalidString = true;
           TypeError typeError = TypeError.INVALIDSTRING;
           showInvalidFieldAlert(typeError);
        }
        return invalidString;
    }
    
    /**
     * M??todo que muestra alerta de campo inv??lido de acuerdo al tipo de error
     * @param typeError Define el tipo de error que encontr??
     */
    
    private void showInvalidFieldAlert(TypeError typeError){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Campo inv??lido");
        if(typeError == TypeError.EMPTYFIELD){
            alert.setContentText("Existe campo vac??o, llena el campo para poder buscar reuni??n");
        }
        if(typeError == TypeError.INVALIDSTRING){
            alert.setContentText("Existe caracter inv??lido, revisa el texto para poder buscar reuni??n");
        }
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
    }
    
    /**
     * M??todo que muestra alerta sin coincidencias
     */
    
    private void showNoMatchAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Sin coincidencias");
        alert.setContentText("No se encontr?? ning??n tipo de evidencia que coincida con el texto ingresado");
        alert.showAndWait();
    }
    
}
