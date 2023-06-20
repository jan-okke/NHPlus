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
import java.util.ArrayList;

/**
 * The controller for the treatments.
 */
public class TreatmentController {
    @FXML
    private Label lblPatientName;
    @FXML
    private ComboBox<String> caregiverComboInTreat;
    @FXML
    private Label lblCarelevel;
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
    private AllTreatmentController controller;
    private Stage stage;
    private Patient patient;
    private Caregiver caregiver;
    private Treatment treatment;
    private TreatmentDAO dao;
    private ArrayList<Caregiver> caregiverList;
    private ObservableList<String> myComboBoxData =
            FXCollections.observableArrayList();

    /**
     * Initializes the controller.
     * @param controller The treatment controller.
     * @param stage The stage.
     * @param treatment The treatment.
     */
    public void initializeController(AllTreatmentController controller, Stage stage, Treatment treatment) {
        this.stage = stage;
        this.controller= controller;
        createCareComboBoxData();
        PatientDAO pDao = DAOFactory.getDAOFactory().createPatientDAO();
        CaregiverDAO cDao = DAOFactory.getDAOFactory().createCaregiverDAO();
        try {
            this.patient = pDao.read((int) treatment.getPid());
            this.caregiver = cDao.read((int) treatment.getCid());
            this.treatment = treatment;
            showData();
        } catch (SQLException | InvalidSQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the combobox data.
     */
    private void createCareComboBoxData(){
        this.caregiverComboInTreat.setItems(myComboBoxData);
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
    public void handleCareComboBoxInTreat(){
        String c = this.caregiverComboInTreat.getSelectionModel().getSelectedItem();
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
     * Displays the data of the treatment.
     */
    private void showData(){
        this.lblPatientName.setText(patient.getSurname()+", "+patient.getFirstName());
        this.caregiverComboInTreat.getSelectionModel().select(caregiver.getSurname());
        this.lblCarelevel.setText(patient.getCareLevel());
        LocalDate date = DateConverter.convertStringToLocalDate(treatment.getDate());
        this.datepicker.setValue(date);
        this.txtBegin.setText(this.treatment.getBegin());
        this.txtEnd.setText(this.treatment.getEnd());
        this.txtDescription.setText(this.treatment.getDescription());
        this.taRemarks.setText(this.treatment.getRemarks());
    }

    /**
     * Handles change of values.
     */
    @FXML
    public void handleChange(){
        this.treatment.setDate(this.datepicker.getValue().toString());
        this.treatment.setCid(this.caregiver.getCid());
        this.treatment.setBegin(txtBegin.getText());
        this.treatment.setEnd(txtEnd.getText());
        this.treatment.setDescription(txtDescription.getText());
        this.treatment.setRemarks(taRemarks.getText());
        doUpdate();
        controller.readAllAndShowInTableView();
        stage.close();
    }

    /**
     * Handles update calls on the database.
     */
    private void doUpdate(){
        TreatmentDAO dao = DAOFactory.getDAOFactory().createTreatmentDAO();
        try {
            dao.update(treatment);
        } catch (SQLException | InvalidSQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles clicking cancel.
     */
    @FXML
    public void handleCancel(){
        stage.close();
    }
}