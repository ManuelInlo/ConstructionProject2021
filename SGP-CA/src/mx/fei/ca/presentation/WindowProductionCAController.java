
package mx.fei.ca.presentation;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
 * Clase para representar el controlador del FXML WindowProductionCA
 * @author Gloria Mendoza González
 * @version 17-06-2021
 */

public class WindowProductionCAController implements Initializable {

    @FXML
    private Label lbUser;
    @FXML
    private TableView<ReceptionWork> tbReceptionWorks;
    @FXML
    private TableColumn<ReceptionWork, String> columnTitleReceptionWork;
    @FXML
    private TableColumn<ReceptionWork, String> columnActualStateReceptionWork;
    @FXML
    private TableView<Article> tbArticles;
    @FXML
    private TableColumn<Article, String> columnTitleArticle;
    @FXML
    private TableColumn<Article, String> columnActualStateArticle;
    @FXML
    private TableColumn<Article, String> columnaAuthorArticle;
    @FXML
    private TableView<Book> tbBooks;
    @FXML
    private TableColumn<Book, String> columnTitleBook;
    @FXML
    private TableColumn<Book, String> columnActualStateBook;
    @FXML
    private TableColumn<Book, String> columnAuthorBook;
    @FXML
    private TableView<ChapterBook> tbChapterBooks;
    @FXML
    private TableColumn<ChapterBook, String> columnTitleChapterBook;
    @FXML
    private TableColumn<ChapterBook, String> columnNumberChapterBook;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    /**
     * Método que proyecta el nombre del integrante loggeado en el sistema y recupera las evidencias
     * @param integrant Define el integrante a establecer en la GUI
     */
    
    public void setIntegrant(Integrant integrant){
        lbUser.setText(integrant.getNameIntegrant());
        try {  
            recoverEvidences();
        } catch (BusinessConnectionException ex) {
            showLostConnectionAlert();
        }    
    }
     
    /**
      * Método que manda a recuperar las evidencias que impactan al cuerpo académico
      * @throws BusinessConnectionException 
     */
     
    public void recoverEvidences() throws BusinessConnectionException{
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        ArrayList<ReceptionWork> receptionWorks = receptionWorkDAO.findReceptionWorksByPositiveImpactCA(); 
        ArticleDAO articleDAO = new ArticleDAO();
        ArrayList<Article> articles = articleDAO.findArticlesByPositiveImpactCA();
        BookDAO bookDAO = new BookDAO();
        ArrayList<Book> books = bookDAO.findBooksByPositiveImpactCA();
        ChapterBookDAO chapterBookDAO = new ChapterBookDAO();
        ArrayList<ChapterBook> chaptersBook = chapterBookDAO.findChapterBooksByPositiveImpactCA();
        fillReceptionWorkTable(receptionWorks);
        fillArticlesTable(articles);
        fillBooksTable(books);
        fillChaptersBookTable(chaptersBook);        
    }  
    
    /**
     * Método que llena la tabla de trabajos recepcionales 
     * @param receptionWorks Define la lista de trabajos recepcionales a mostrar en la GUI
     */
    
    private void fillReceptionWorkTable(ArrayList<ReceptionWork> receptionWorks){
        columnTitleReceptionWork.setCellValueFactory(new PropertyValueFactory("titleReceptionWork"));
        columnActualStateReceptionWork.setCellValueFactory(new PropertyValueFactory("actualState"));
        ObservableList<ReceptionWork> listReceptionWorks = FXCollections.observableArrayList(receptionWorks);
        tbReceptionWorks.setItems(listReceptionWorks);
    }
    
    /**
     * Método que llena la tabla de artículos 
     * @param articles Define la lista de artículos a mostrar en la GUI
     */
    
    private void fillArticlesTable(ArrayList<Article> articles){
        columnTitleArticle.setCellValueFactory(new PropertyValueFactory("titleEvidence"));
        columnActualStateArticle.setCellValueFactory(new PropertyValueFactory("actualState"));
        columnaAuthorArticle.setCellValueFactory(new PropertyValueFactory("author"));
        ObservableList<Article> listArticles = FXCollections.observableArrayList(articles);
        tbArticles.setItems(listArticles);
    }
    
    /**
     * Método que llena la tabla de libros 
     * @param books Define la lista de libros a mostrar en la GUI
     */
    
    private void fillBooksTable(ArrayList<Book> books){
        columnTitleBook.setCellValueFactory(new PropertyValueFactory("titleEvidence"));
        columnActualStateBook.setCellValueFactory(new PropertyValueFactory("actualState"));
        columnAuthorBook.setCellValueFactory(new PropertyValueFactory("author"));
        ObservableList<Book> listBooks = FXCollections.observableArrayList(books);
        tbBooks.setItems(listBooks);
    }
    
    /**
     * Método que llena la tabla de capítulos de libro 
     * @param chaptersBook Define la lista de capítulos de libro a mostrar en la GUI
     */
    
    private void fillChaptersBookTable(ArrayList<ChapterBook> chaptersBook){
        columnTitleChapterBook.setCellValueFactory(new PropertyValueFactory("titleEvidence"));
        columnNumberChapterBook.setCellValueFactory(new PropertyValueFactory("chapterNumber"));
        ObservableList<ChapterBook> listChaptersBook = FXCollections.observableArrayList(chaptersBook);
        tbChapterBooks.setItems(listChaptersBook);
    }
    
     
    /**
     * Método que cierra la ventana actual
     * @param event Define el evento generado
     */ 
     
    @FXML
    private void closeProductionCA(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
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
    
}
