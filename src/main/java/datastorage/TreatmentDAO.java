package datastorage;

import exceptions.InvalidSQLException;
import model.Treatment;
import utils.DateConverter;
import utils.Validation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access methods for Treatment.
 */
public class TreatmentDAO extends DAOimp<Treatment> {

    /**
     * Creates a new TreatmentDAO with an existing connection.
     * @param conn The connection.
     */
    public TreatmentDAO(Connection conn) {
        super(conn);
    }

    /**
     * Creates a SQL string for creating a treatment.
     * @param treatment The treatment to get the creation SQL for.
     * @return The SQL string for creating the treatment.
     * @throws InvalidSQLException
     */
    @Override
    protected String getCreateStatementString(Treatment treatment) throws InvalidSQLException {
        Validation.validateTreatment(treatment);
        return String.format("INSERT INTO treatment (pid, treatment_date, begin, end, description, remarks) VALUES " +
                "(%d, '%s', '%s', '%s', '%s', '%s')", treatment.getPid(), treatment.getDate(),
                treatment.getBegin(), treatment.getEnd(), treatment.getDescription(),
                treatment.getRemarks());
    }

    /**
     * Creates a SQL string for creating an archive entry.
     * @param treatment The treatment to archive.
     * @return The SQL string for creating an archive entry.
     * @throws InvalidSQLException
     */
    protected String getCreateArchiveStatementString(Treatment treatment) throws InvalidSQLException {
        Validation.validateTreatment(treatment);
        return String.format("INSERT INTO treatment_archive (pid, treatment_date, begin, end, description, remarks, treatment_finished_date) VALUES " +
                        "(%d, '%s', '%s', '%s', '%s', '%s', '%s')", treatment.getPid(), treatment.getDate(),
                treatment.getBegin(), treatment.getEnd(), treatment.getDescription(),
                treatment.getRemarks(), DateConverter.convertStringToLocalDate(LocalDate.now().toString()));
    }

    /**
     * Creates a SQL string for selecting from treatments at the given key.
     * @param key The primary key from the treatments table.
     * @return the SQL string for selecting from treatments at the given key.
     * @throws InvalidSQLException
     */
    @Override
    protected String getReadByIDStatementString(long key) throws InvalidSQLException {
        Validation.validateLong(key);
        return String.format("SELECT * FROM treatment WHERE tid = %d", key);
    }

    /**
     * Parses the result into a Treatment object.
     * @param result The result from the select statement.
     * @return The Treatment object parsed from the result.
     * @throws SQLException
     */
    @Override
    protected Treatment getInstanceFromResultSet(ResultSet result) throws SQLException {
        LocalDate date = DateConverter.convertStringToLocalDate(result.getString(3));
        LocalTime begin = DateConverter.convertStringToLocalTime(result.getString(4));
        LocalTime end = DateConverter.convertStringToLocalTime(result.getString(5));
        Treatment m = new Treatment(result.getLong(1), result.getLong(2),
                date, begin, end, result.getString(6), result.getString(7));
        return m;
    }

    /**
     * Creates the select all SQL statement.
     * @return The select all SQL statement.
     */
    @Override
    protected String getReadAllStatementString() {
        return "SELECT * FROM treatment";
    }

    /**
     * Parses a result into multiple treatments.
     * @param result The result from the select statement.
     * @return A list of all treatments found in the result.
     * @throws SQLException
     */
    @Override
    protected ArrayList<Treatment> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<Treatment> list = new ArrayList<Treatment>();
        Treatment t = null;
        while (result.next()) {
            LocalDate date = DateConverter.convertStringToLocalDate(result.getString(3));
            LocalTime begin = DateConverter.convertStringToLocalTime(result.getString(4));
            LocalTime end = DateConverter.convertStringToLocalTime(result.getString(5));
            t = new Treatment(result.getLong(1), result.getLong(2),
                    date, begin, end, result.getString(6), result.getString(7));
            list.add(t);
        }
        return list;
    }

    /**
     * Creates a SQL string for updating a treatment object.
     * @param treatment The adjusted treatment.
     * @return The SQL string for updating the treatment object.
     * @throws InvalidSQLException
     */
    @Override
    protected String getUpdateStatementString(Treatment treatment) throws InvalidSQLException {
        Validation.validateTreatment(treatment);
        return String.format("UPDATE treatment SET pid = %d, treatment_date ='%s', begin = '%s', end = '%s'," +
                "description = '%s', remarks = '%s' WHERE tid = %d", treatment.getPid(), treatment.getDate(),
                treatment.getBegin(), treatment.getEnd(), treatment.getDescription(), treatment.getRemarks(),
                treatment.getTid());
    }

    /**
     * Creates a SQL statement for deleting from treatments at the given key.
     * @param key The TID primary key.
     * @return The SQL statement for deleting at the given key.
     * @throws InvalidSQLException
     */
    @Override
    protected String getDeleteStatementString(long key) throws InvalidSQLException {
        Validation.validateLong(key);
        return String.format("Delete FROM treatment WHERE tid= %d", key);
    }

    /**
     * Gets all treatments from a given patient by its ID.
     * @param pid The Patient ID primary key.
     * @return A list of treatments the patient with the given ID has.
     * @throws SQLException
     * @throws InvalidSQLException
     */
    public List<Treatment> readTreatmentsByPid(long pid) throws SQLException, InvalidSQLException {
        Validation.validateLong(pid);
        ArrayList<Treatment> list = new ArrayList<Treatment>();
        //Treatment object = null;
        Statement st = conn.createStatement();
        ResultSet result = st.executeQuery(getReadAllTreatmentsOfOnePatientByPid(pid));
        list = getListFromResultSet(result);
        return list;
    }

    /**
     * Gets a SQL statement string to get all treatments for a given patient.
     * @param pid The Patient ID primary key.
     * @return The SQL string to get all treatments for a given patient.
     * @throws InvalidSQLException
     */
    private String getReadAllTreatmentsOfOnePatientByPid(long pid) throws InvalidSQLException {
        Validation.validateLong(pid);
        return String.format("SELECT * FROM treatment WHERE pid = %d", pid);
    }

    /**
     * Archives a treatment by its primary key.
     * @param key The Treatment ID primary key to archive.
     * @throws SQLException
     * @throws InvalidSQLException
     */
    public void archiveByTid(long key) throws SQLException, InvalidSQLException {
        Validation.validateLong(key);
        Statement st = conn.createStatement();
        //Liest aus Treatment einen Eintrag aus, konvertiert ihn in ein Treatment objekt und schreibt dieses in die Treatment_Archive Tabelle.
        ResultSet result = st.executeQuery(getReadByIDStatementString(key));
        if(result.next()){
            Treatment treatmentArchive = getInstanceFromResultSet(result);
            st.executeUpdate(getCreateArchiveStatementString(treatmentArchive));
            st.executeUpdate(String.format("Delete FROM treatment WHERE tid= %d", key));
        }
    }

    /**
     * Automatically deletes entries if they are older than a given amount of years.
     * @param years The years after which an entry should be deleted.
     * @throws SQLException
     */
    public void deleteArchivedTreatmentsAfterYears(int years) throws SQLException {
        Statement st = conn.createStatement();
        ResultSet result = st.executeQuery("SELECT * FROM treatment_archive");
        if (result.next()) {
            LocalDate date = DateConverter.convertStringToLocalDate(result.getString(8));
            if (date.plusYears(years).isBefore(LocalDate.now())) {
                long tid = result.getLong(1);
                Statement deleteStatement = conn.createStatement();
                deleteStatement.executeUpdate(String.format("DELETE FROM treatment_archive WHERE tid = %d", tid));
            }
        }
    }
}