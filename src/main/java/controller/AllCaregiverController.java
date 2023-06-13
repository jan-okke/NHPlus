package controller;

import datastorage.CaregiverDAO;
import datastorage.DAOFactory;
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

import java.sql.SQLException;
import java.util.List;

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

    private void clearTextfields() {
        this.txfFirstname.clear();
        this.txfSurname.clear();
        this.txfTelephone.clear();
    }

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

    public void handleDeleteCaregiver(ActionEvent e) {
        CaregiverDAO dao = DAOFactory.getDAOFactory().createCaregiverDAO();
        Caregiver selectedItem = this.tableView.getSelectionModel().getSelectedItem();
        try {
            dao.deleteById(selectedItem.getCid());
        } catch (SQLException | InvalidSQLException ex) {
            throw new RuntimeException(ex);
        }
        readAllAndShowInTableView();
        clearTextfields();
    }
}
