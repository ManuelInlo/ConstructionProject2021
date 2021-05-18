/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.fei.ca.presentation;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mx.fei.ca.businesslogic.ReceptionWorkDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.ReceptionWork;

public class SGPCA extends Application {
    
    @Override
    public void start(Stage stage) throws IOException, BusinessConnectionException {
        //Solo es de prueba para ver que funcione la ventana
        //ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        //ReceptionWork receptionWork = receptionWorkDAO.findReceptionWorkByTitle("Impacto de la Inteligencia Artificial en el diseño de software");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowMemberProduction.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setResizable(false);
       //WindowReceptionWorkDataController windowReceptionWorkDataController = (WindowReceptionWorkDataController) fxmlLoader.getController();
        //windowReceptionWorkDataController.setReceptionWork(receptionWork);
        //windowReceptionWorkDataController.showReceptionWorkData(receptionWork);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
