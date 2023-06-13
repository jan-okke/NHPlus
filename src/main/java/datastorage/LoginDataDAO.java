package datastorage;

import model.LoginData;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Data Access Methods for LoginData.
 */
public class LoginDataDAO extends DAOimp<LoginData> {

    /**
     * Creates a new LoginDataDAO with an existing connection.
     * @param conn The connection.
     */
    public LoginDataDAO(Connection conn) {
        super(conn);
    }

    /**
     * Gets a SQL statement for adding a new LoginData.
     * @param loginData The LoginData to add an entry for.
     * @return The SQL statement for adding a new LoginData.
     */
    @Override
    protected String getCreateStatementString(LoginData loginData) {
        return String.format("INSERT INTO logindata (username, salt, hash) VALUES ('%s', '%s', '%s')", loginData.getUsername(), loginData.getSalt(), loginData.getHash());
    }

    /**
     * Gets a SQL statement to select from LoginData at a given key.
     * @param key The LoginData primary key to read from.
     * @return The SQL statement for selecting from a given LoginData primary key.
     */
    @Override
    protected String getReadByIDStatementString(long key) {
        return String.format("SELECT * FROM logindata WHERE lid = %d", key);
    }

    /**
     * Parses a ResultSet into a LoginData object.
     * @param set The result to parse.
     * @return The parsed LoginData from the result.
     * @throws SQLException
     */
    @Override
    protected LoginData getInstanceFromResultSet(ResultSet set) throws SQLException {
        return new LoginData(set.getLong(1), set.getString(2), set.getString(3), set.getString(4));
    }

    /**
     * Gets a SQL statement to select all LoginData.
     * @return A SQL statement to select all LoginData.
     */
    @Override
    protected String getReadAllStatementString() {
        return "SELECT * FROM logindata";
    }

    /**
     * Parses the result into multiple LoginData objects.
     * @param set The result from the select statement.
     * @return All LoginData objects found in the result.
     * @throws SQLException
     */
    @Override
    protected ArrayList<LoginData> getListFromResultSet(ResultSet set) throws SQLException {
        ArrayList<LoginData> list = new ArrayList<LoginData>();
        LoginData l = null;
        while (set.next()) {
            l = new LoginData(set.getLong(1), set.getString(2), set.getString(3), set.getString(4));
            list.add(l);
        }
        return list;
    }

    /**
     * Gets the SQL statement for updating a LoginData object.
     * @param loginData The LoginData that has been modified.
     * @return The update SQL statement for the LoginData.
     */
    @Override
    protected String getUpdateStatementString(LoginData loginData) {
        return String.format("UPDATE logindata SET username = '%s', salt = '%s', hash = '%s' WHERE lid = '%d'", loginData.getUsername(), loginData.getSalt(), loginData.getHash(), loginData.getLid());
    }

    /**
     * Gets a SQL delete statement for deleting at the given key.
     * @param key The LoginData primary key
     * @return The SQL delete statement for the given key.
     */
    @Override
    protected String getDeleteStatementString(long key) {
        return String.format("DELETE FROM logindata WHERE lid = '%d'", key);
    }
}
