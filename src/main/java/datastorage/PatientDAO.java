package datastorage;

import exceptions.InvalidSQLException;
import model.Patient;
import model.Treatment;
import utils.DateConverter;
import utils.Encryption;
import utils.Validation;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Implements the Interface <code>DAOImp</code>. Overrides methods to generate specific patient-SQL-queries.
 */
public class PatientDAO extends DAOimp<Patient> {

    /**
     * Encrypts a string for the patient.
     * @param input The string to encrypt.
     * @return The encrypted string.
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws InvalidKeyException
     */
    protected String encryptPatient(String input) throws InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return Encryption.encryptString(input);
    }

    /**
     * constructs Object. Calls the Constructor from <code>DAOImp</code> to store the connection.
     * @param conn
     */
    public PatientDAO(Connection conn) {
        super(conn);
    }

    /**
     * generates a <code>INSERT INTO</code>-Statement for a given patient
     * @param patient for which a specific INSERT INTO is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getCreateStatementString(Patient patient) throws InvalidSQLException {
        Validation.validatePatient(patient);
        return String.format("INSERT INTO patient (firstname, surname, dateOfBirth, carelevel, roomnumber) VALUES ('%s', '%s', '%s', '%s', '%s')",
                patient.getFirstName(), patient.getSurname(), patient.getDateOfBirth(), patient.getCareLevel(), patient.getRoomnumber());
    }

    /**
     * Gets the SQL string for creating an archive.
     * @param patient The patient to archive.
     * @return The SQL string.
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws InvalidKeyException
     * @throws InvalidSQLException
     */
    protected String getCreateArchiveStatementString(Patient patient) throws InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidSQLException {
        Validation.validatePatient(patient);
        System.out.println(encryptPatient(patient.getDateOfBirth()));
        return String.format("INSERT INTO patient_archive (pid, firstname, surname, dateOfBirth, carelevel, roomnumber, archival_date) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s')",
               patient.getPid(), encryptPatient(patient.getFirstName()), encryptPatient(patient.getSurname()),
                encryptPatient(patient.getDateOfBirth()), encryptPatient(patient.getCareLevel()),
                encryptPatient(patient.getRoomnumber()), LocalDate.now());
    }

    /**
     * generates a <code>select</code>-Statement for a given key
     * @param key for which a specific SELECTis to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getReadByIDStatementString(long key) throws InvalidSQLException {
        Validation.validateLong(key);
        return String.format("SELECT * FROM patient WHERE pid = %d", key);
    }

    /**
     * maps a <code>ResultSet</code> to a <code>Patient</code>
     * @param result ResultSet with a single row. Columns will be mapped to a patient-object.
     * @return patient with the data from the resultSet.
     */
    @Override
    protected Patient getInstanceFromResultSet(ResultSet result) throws SQLException {
        Patient p = null;
        LocalDate date = DateConverter.convertStringToLocalDate(result.getString(4));
        p = new Patient(result.getInt(1), result.getString(2),
                result.getString(3), date, result.getString(5),
                result.getString(6));
        return p;
    }

    /**
     * generates a <code>SELECT</code>-Statement for all patients.
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getReadAllStatementString() {
        return "SELECT * FROM patient";
    }

    /**
     * maps a <code>ResultSet</code> to a <code>Patient-List</code>
     * @param result ResultSet with a multiple rows. Data will be mapped to patient-object.
     * @return ArrayList with patients from the resultSet.
     */
    @Override
    protected ArrayList<Patient> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<Patient> list = new ArrayList<Patient>();
        Patient p = null;
        while (result.next()) {
            LocalDate date = DateConverter.convertStringToLocalDate(result.getString(4));
            p = new Patient(result.getInt(1), result.getString(2),
                    result.getString(3), date,
                    result.getString(5), result.getString(6));
            list.add(p);
        }
        return list;
    }

    /**
     * generates a <code>UPDATE</code>-Statement for a given patient
     * @param patient for which a specific update is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getUpdateStatementString(Patient patient) throws InvalidSQLException {
        Validation.validatePatient(patient);
        return String.format("UPDATE patient SET firstname = '%s', surname = '%s', dateOfBirth = '%s', carelevel = '%s', " +
                "roomnumber = '%s' WHERE pid = %d", patient.getFirstName(), patient.getSurname(), patient.getDateOfBirth(),
                patient.getCareLevel(), patient.getRoomnumber(), patient.getPid());
    }

    /**
     * generates a <code>delete</code>-Statement for a given key
     * @param key for which a specific DELETE is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getDeleteStatementString(long key) throws InvalidSQLException {
        Validation.validateLong(key);
        return String.format("Delete FROM patient WHERE pid=%d", key);
    }

    /**
     * Archives a patient by its primary key.
     * @param key The primary key of the patient.
     * @throws SQLException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws InvalidKeyException
     * @throws InvalidSQLException
     */
    public void archiveByPid(long key) throws SQLException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidSQLException {
        Validation.validateLong(key);
        Statement st = conn.createStatement();
        //Liest aus Patient einen Eintrag aus, konvertiert ihn in ein Patient objekt und schreibt dieses in die Patient_Archive Tabelle.
        ResultSet result = st.executeQuery(getReadByIDStatementString(key));
        if(result.next()){
            Patient patientArchive = getInstanceFromResultSet(result);
            st.executeUpdate(getCreateArchiveStatementString(patientArchive));
            st.executeUpdate(String.format("Delete FROM patient WHERE pid= %d", key));
        }

    }
}
