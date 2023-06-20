package controller;

import datastorage.CaregiverDAO;
import datastorage.DAOFactory;
import datastorage.TreatmentDAO;
import exceptions.InvalidSQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import model.Caregiver;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

/**
 * The controller for the caregivers.
 */
public class AllCaregiverController {

    public TextField txfFirstname;
    public TextField txfSurname;
    public TextField txfTelephone;
    public TableView<Caregiver> tableView;
    public Button btnAdd;
    public Button btnDelete;
    public TableColumn colID;
    public TableColumn colSurname;
    public TableColumn colFirstName;
    public TableColumn colTelephone;

    private ObservableList<Caregiver> tableviewContent = FXCollections.observableArrayList();
    private CaregiverDAO dao;

    /**
     * Initialization of the window.
     */
    public void initialize() {
        readAllAndShowInTableView();

        this.colID.setCellValueFactory(new PropertyValueFactory<Caregiver, Integer>("cid"));

        this.colFirstName.setCellValueFactory(new PropertyValueFactory<Caregiver, String>("firstName"));
        //CellFactory zum Schreiben innerhalb der Tabelle
        this.colFirstName.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colSurname.setCellValueFactory(new PropertyValueFactory<Caregiver, String>("surname"));
        this.colSurname.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colTelephone.setCellValueFactory(new PropertyValueFactory<Caregiver, String>("phoneNumber"));
        this.colTelephone.setCellFactory(TextFieldTableCell.forTableColumn());

        this.tableView.setItems(this.tableviewContent);
    }

    /**
     * Handling adding a caregiver.
     * @param e The event.
     */
    public void handleAddCaregiver(ActionEvent e) {
        CaregiverDAO dao = DAOFactory.getDAOFactory().createCaregiverDAO();
        try {
            dao.create(new Caregiver(txfFirstname.getText(), txfSurname.getText(), txfTelephone.getText()));
        } catch (SQLException | InvalidSQLException ex) {
            throw new RuntimeException(ex);
        }
        readAllAndShowInTableView();
        clearTextfields();
    }

    /**
     * Clearing the text fields.
     */
    private void clearTextfields() {
        this.txfFirstname.clear();
        this.txfSurname.clear();
        this.txfTelephone.clear();
    }

    /**
     * Handling reading and displaying all Caregiver in the table.
     */
    private void readAllAndShowInTableView() {
        this.tableviewContent.clear();
        this.dao = DAOFactory.getDAOFactory().createCaregiverDAO();
        List<Caregiver> allCaregiver;
        try {
            allCaregiver = dao.readAll();
            for (Caregiver c : allCaregiver) {
                this.tableviewContent.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * The handling of deleting a caregiver.
     * @param e The event.
     */
    public void handleDeleteCaregiver(ActionEvent e) {
        CaregiverDAO dao = DAOFactory.getDAOFactory().createCaregiverDAO();
        TreatmentDAO tDao = DAOFactory.getDAOFactory().createTreatmentDAO();
        Caregiver selectedItem = this.tableView.getSelectionModel().getSelectedItem();
        try {
            //Archiviren der Treatments die mit diesem Caregiver verknüpft sind
            tDao.archiveByCid(selectedItem.getCid());
            dao.archiveByCid(selectedItem.getCid());
        } catch (SQLException | InvalidSQLException | InvalidAlgorithmParameterException | IllegalBlockSizeException |
                 NoSuchPaddingException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException ex) {
            throw new RuntimeException(ex);
        }
        readAllAndShowInTableView();
        clearTextfields();
    }
}
