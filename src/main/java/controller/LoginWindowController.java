package controller;

import datastorage.ConnectionBuilder;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class LoginWindowController
{
    @FXML
    private javafx.scene.control.TextField UsernameField;

    @FXML
    private javafx.scene.control.PasswordField PasswordField;
    @FXML
    private void handleCancel(javafx.event.ActionEvent e) {
        ConnectionBuilder.closeConnection();
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void handleLogin(javafx.event.ActionEvent e) {
        String username = UsernameField.getText();
        String password = PasswordField.getText();

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
        mainWindow();
    }

    private void mainWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/MainWindowView.fxml"));
            javafx.scene.layout.BorderPane pane = loader.load();

            Stage stage = new Stage();

            Scene scene = new Scene(pane);
            stage.setTitle("NHPlus");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent e) {
                    ConnectionBuilder.closeConnection();
                    Platform.exit();
                    System.exit(0);
                }
            });
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
