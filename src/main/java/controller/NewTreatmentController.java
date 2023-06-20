package controller;

import datastorage.CaregiverDAO;
import datastorage.DAOFactory;
import datastorage.PatientDAO;
import datastorage.TreatmentDAO;
import exceptions.InvalidSQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Caregiver;
import model.Patient;
import model.Treatment;
import utils.DateConverter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.parseLong;

/**
 * The controller for adding new treatments.
 */
public class NewTreatmentController {
    @FXML
    private Label lblSurname;
    @FXML
    private Label lblFirstname;
    @FXML
    private TextField txtBegin;
    @FXML
    private TextField txtEnd;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextArea taRemarks;
    @FXML
    private DatePicker datepicker;
    @FXML
    private ComboBox<String> careComboBox;

    private AllTreatmentController controller;
    private Patient patient;
    private Caregiver caregiver;
    private Stage stage;
    private ArrayList<Caregiver> caregiverList;
    private ObservableList<String> myComboBoxData =
            FXCollections.observableArrayList();
    private ObservableList<Treatment> tableviewContent =
            FXCollections.observableArrayList();

    private ArrayList<Patient> patientList;
    private TreatmentDAO dao;

    /**
     * Initialization.
     * @param controller The controller.
     * @param stage The stage.
     * @param patient The patient.
     */
    public void initialize(AllTreatmentController controller, Stage stage, Patient patient) {
        this.controller= controller;
        this.patient = patient;
        this.caregiver = null;
        this.stage = stage;
        careComboBox.setItems(myComboBoxData);
        careComboBox.getSelectionModel().select(0);
        createCareComboBoxData();
        showPatientData();
    }

    /**
     * Displays the patient data.
     */
    private void showPatientData(){
        this.lblFirstname.setText(patient.getFirstName());
        this.lblSurname.setText(patient.getSurname());
    }

    /**
     * Handles adding a treatment.
     */
    @FXML
    public void handleAdd(){
        if (this.caregiver == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Caregiver für die Behandlung fehlt!");
            alert.setContentText("Wählen Sie über die Combobox einen Caregiver aus!");
            alert.showAndWait();
            return;
        }
        long cid = this.caregiver.getCid();
        LocalDate date = this.datepicker.getValue();
        String s_begin = txtBegin.getText();
        LocalTime begin = DateConverter.convertStringToLocalTime(txtBegin.getText());
        LocalTime end = DateConverter.convertStringToLocalTime(txtEnd.getText());
        String description = txtDescription.getText();
        String remarks = taRemarks.getText();
        Treatment treatment = new Treatment(patient.getPid(), cid, date,
                begin, end, description, remarks);
        createTreatment(treatment);
        controller.readAllAndShowInTableView();
        stage.close();
    }

    /**
     * Handles inserting a treatment in the database.
     * @param treatment The treatment to create
     */
    private void createTreatment(Treatment treatment) {
        TreatmentDAO dao = DAOFactory.getDAOFactory().createTreatmentDAO();
        try {
            dao.create(treatment);
        } catch (SQLException | InvalidSQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the combobox data.
     */
    private void createCareComboBoxData(){
        CaregiverDAO dao = DAOFactory.getDAOFactory().createCaregiverDAO();
        try {
            caregiverList = (ArrayList<Caregiver>) dao.readAll();
            for (Caregiver caregiver: caregiverList) {
                this.myComboBoxData.add(caregiver.getSurname());
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Handles selecting an item from the combobox.
     */
    @FXML
    public void handleCareComboBox(){
        String c = this.careComboBox.getSelectionModel().getSelectedItem();
        this.dao = DAOFactory.getDAOFactory().createTreatmentDAO();
        Caregiver care = searchInList(c);
        this.caregiver = care;
    }

    /**
     * Searches in all caregivers and returns the caregiver with the given name.
     * @param surname The surname of the caregiver.
     * @return The caregiver to find or null if no caregiver with the given name exists.
     */
    private Caregiver searchInList(String surname){
        for (int i =0; i<this.caregiverList.size();i++){
            if(this.caregiverList.get(i).getSurname().equals(surname)){
                return this.caregiverList.get(i);
            }
        }
        return null;
    }

    /**
     * Handles clicking on Cancel.
     */
    @FXML
    public void handleCancel(){
        stage.close();
    }
}