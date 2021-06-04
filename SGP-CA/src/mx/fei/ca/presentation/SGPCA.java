/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.fei.ca.presentation;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
public class SGPCA extends Application {
    
    @Override
    public void start(Stage stage) throws IOException, BusinessConnectionException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowAddIntegrant.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowMeetingHistory.fxml"));  //WindowMeetingHistory
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
