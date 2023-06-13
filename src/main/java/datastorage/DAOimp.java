package datastorage;

import exceptions.InvalidSQLException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation class for the DAO.
 * @param <T> The object to implement DAO for.
 */
public abstract class DAOimp<T> implements DAO<T>{
    protected Connection conn;

    /**
     * Creates a new DAOimp with existing connection.
     * @param conn The connection.
     */
    public DAOimp(Connection conn) {
        this.conn = conn;
    }

    /**
     * Creates a new object.
     * @param t The type of the object.
     * @throws SQLException
     * @throws InvalidSQLException
     */
    @Override
    public void create(T t) throws SQLException, InvalidSQLException {
        Statement st = conn.createStatement();
        st.executeUpdate(getCreateStatementString(t));
    }

    /**
     * Gets the object with its primary key.
     * @param key The primary key.
     * @return
     * @throws SQLException
     * @throws InvalidSQLException
     */
    @Override
    public T read(long key) throws SQLException, InvalidSQLException {
        T object = null;
        Statement st = conn.createStatement();
        ResultSet result = st.executeQuery(getReadByIDStatementString(key));
        if (result.next()) {
            object = getInstanceFromResultSet(result);
        }
        return object;
    }

    /**
     * Gets all objects in the table.
     * @return The list of objects.
     * @throws SQLException
     */
    @Override
    public List<T> readAll() throws SQLException {
        ArrayList<T> list = new ArrayList<T>();
        T object = null;
        Statement st = conn.createStatement();
        ResultSet result = st.executeQuery(getReadAllStatementString());
        list = getListFromResultSet(result);
        return list;
    }

    /**
     * Updates the object.
     * @param t The object to edit.
     * @throws SQLException
     * @throws InvalidSQLException
     */
    @Override
    public void update(T t) throws SQLException, InvalidSQLException {
        Statement st = conn.createStatement();
        st.executeUpdate(getUpdateStatementString(t));
    }

    /**
     * Deletes the object.
     * @param key The primary key of the object to delete.
     * @throws SQLException
     * @throws InvalidSQLException
     */
    @Override
    public void deleteById(long key) throws SQLException, InvalidSQLException {
        Statement st = conn.createStatement();
        st.executeUpdate(getDeleteStatementString(key));
    }

    /**
     * Gets a creation string.
     * @param t The type of the object.
     * @return The SQL.
     * @throws InvalidSQLException
     */
    protected abstract String getCreateStatementString(T t) throws InvalidSQLException;
    /**
     * Gets the select string.
     * @param key The primary key of the object.
     * @return The SQL.
     * @throws InvalidSQLException
     */
    protected abstract String getReadByIDStatementString(long key) throws InvalidSQLException;

    /**
     * Gets an object from a result.
     * @param set The result from the select statement.
     * @return The parsed object.
     * @throws SQLException
     */
    protected abstract T getInstanceFromResultSet(ResultSet set) throws SQLException;

    /**
     * Gets a sql for selecting all objects.
     * @return The SQL.
     */
    protected abstract String getReadAllStatementString();
    /**
     * Parses multiple objects from one result.
     * @param set The result from the select statement.
     * @return The list of parsed objects.
     * @throws SQLException
     */
    protected abstract ArrayList<T> getListFromResultSet(ResultSet set) throws SQLException;
    /**
     * Gets the SQL for updating an object.
     * @param t The type of the object.
     * @return The SQL.
     * @throws InvalidSQLException
     */
    protected abstract String getUpdateStatementString(T t) throws InvalidSQLException;
    /**
     * Gets the SQL for deleting an object.
     * @param key The primary key of the object.
     * @return The SQL.
     * @throws InvalidSQLException
     */
    protected abstract String getDeleteStatementString(long key) throws InvalidSQLException;
}
