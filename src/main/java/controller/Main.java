package controller;

import datastorage.ConnectionBuilder;
import datastorage.DAOFactory;
import datastorage.TreatmentDAO;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.sql.SQLException;

/**
 * The main class for the application.
 */
public class Main extends Application {


    private Stage primaryStage;


    /**
     * The start of the GUI.
     * @param primaryStage The first stage.
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        checkAndDeleteArchives();
        loginWindow();
    }

    /**
     * The method for checking and deleting archives on startup.
     */
    private void checkAndDeleteArchives() {
        DAOFactory dao = DAOFactory.getDAOFactory();
        try {
            dao.createTreatmentDAO().deleteArchivedTreatmentsAfterYears(10);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The method to open the loginWindow.
     */
    public void loginWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/LoginView.fxml"));
            GridPane pane = loader.load();

            Scene scene = new Scene(pane);
            this.primaryStage.setTitle("NHPlus");
            this.primaryStage.setScene(scene);
            this.primaryStage.setResizable(false);
            this.primaryStage.show();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * The entry point.
     * @param args Startup args.
     */
    public static void main(String[] args) {
        launch(args);
    }
}