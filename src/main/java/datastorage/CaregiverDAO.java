package datastorage;

import exceptions.InvalidSQLException;
import model.Caregiver;
import model.Patient;
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
 * The DAO for the caregiver object.
 */
public class CaregiverDAO extends DAOimp<Caregiver> {

    /**
     * Creates a new Caregiver DAO with an existing connection.
     * @param conn The connection.
     */
    public CaregiverDAO(Connection conn) {
        super(conn);
    }

    /**
     * Creates a SQL for adding a caregiver.
     * @param caregiver The caregiver to create a SQL statement for.
     * @return The SQL statement string for adding a caregiver.
     * @throws InvalidSQLException
     */
    @Override
    protected String getCreateStatementString(Caregiver caregiver) throws InvalidSQLException {
        Validation.validateCaregiver(caregiver);
        return String.format("INSERT INTO caregiver (firstname, surname, phonenumber) VALUES ('%s', '%s', '%s')", caregiver.getFirstName(), caregiver.getSurname(), caregiver.getPhoneNumber());
    }

    /**
     * Creates a SQL statement for selecting a specific caregiver.
     * @param key The Caregiver primary key.
     * @return The SQL string for selecting a specific caregiver.
     * @throws InvalidSQLException
     */
    @Override
    protected String getReadByIDStatementString(long key) throws InvalidSQLException {
        Validation.validateLong(key);
        return String.format("SELECT * FROM caregiver WHERE cid = '%d'", key);
    }

    /**
     * Parses a ResultSet into a Caregiver object.
     * @param set The result of a select statement.
     * @return The parsed caregiver object.
     * @throws SQLException
     */
    @Override
    protected Caregiver getInstanceFromResultSet(ResultSet set) throws SQLException {
        return new Caregiver(set.getLong(1), set.getString(2), set.getString(3), set.getString(4));
    }

    /**
     * Creates a select all SQL statement.
     * @return The select all SQL statement.
     */
    @Override
    protected String getReadAllStatementString() {
        return "SELECT * FROM caregiver";
    }

    /**
     * Parses the result into multiple caregivers.
     * @param set The result of the select statement.
     * @return A list of caregiver objects parsed.
     * @throws SQLException
     */
    @Override
    protected ArrayList<Caregiver> getListFromResultSet(ResultSet set) throws SQLException {
        ArrayList<Caregiver> list = new ArrayList<Caregiver>();
        Caregiver c = null;
        while (set.next()) {
            c = new Caregiver(set.getLong(1), set.getString(2), set.getString(3), set.getString(4));
            list.add(c);
        }
        return list;
    }

    /**
     * Creates a SQL string for updating the caregiver.
     * @param caregiver The changed caregiver object.
     * @return The SQL string for updating the caregiver.
     * @throws InvalidSQLException
     */
    @Override
    protected String getUpdateStatementString(Caregiver caregiver) throws InvalidSQLException {
        Validation.validateCaregiver(caregiver);
        return String.format("UPDATE caregiver SET firstname = '%s', surname = '%s', phonenumber = '%s' WHERE cid = '%d'", caregiver.getFirstName(), caregiver.getSurname(), caregiver.getPhoneNumber(), caregiver.getCid());
    }

    /**
     * Creates a SQL string for deleting a specific caregiver.
     * @param key The primary key of the caregiver to delete.
     * @return The SQL string for deleting the specific caregiver.
     * @throws InvalidSQLException
     */
    @Override
    protected String getDeleteStatementString(long key) throws InvalidSQLException {
        Validation.validateLong(key);
        return String.format("DELETE FROM caregiver WHERE cid = '%d'", key);
    }

    protected String getCreateArchiveStatementString(Caregiver caregiver) throws InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return String.format("INSERT INTO caregiver_archive (cid, firstname, surname, phonenumber, archival_date) VALUES ('%s', '%s', '%s', '%s', '%s')",
                caregiver.getCid(), encryptCaregiver(caregiver.getFirstName()), encryptCaregiver(caregiver.getSurname()),
                encryptCaregiver(caregiver.getPhoneNumber()), LocalDate.now());
    }

    public void archiveByCid(long key) throws SQLException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidSQLException {
            Statement st = conn.createStatement();
            ResultSet result = st.executeQuery(getReadByIDStatementString(key));
            if(result.next()){
                Caregiver caregiverArchive = getInstanceFromResultSet(result);
                st.executeUpdate(getCreateArchiveStatementString(caregiverArchive));
                st.executeUpdate(getDeleteStatementString(key));
            }
    }

    private String encryptCaregiver(String input) throws InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
            return Encryption.encryptString(input);

    }
}
