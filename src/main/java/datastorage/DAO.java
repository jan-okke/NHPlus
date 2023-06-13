package datastorage;

import exceptions.InvalidSQLException;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface for defining SQL generating and object receiving methods.
 * @param <T> The type of object.
 */
public interface DAO<T> {
    /**
     * Creates an object.
     * @param t The type of the object.
     * @throws SQLException
     * @throws InvalidSQLException
     */
    void create(T t) throws SQLException, InvalidSQLException;

    /**
     * Returns an object with the given primary key.
     * @param key The primary key.
     * @return The object.
     * @throws SQLException
     * @throws InvalidSQLException
     */
    T read(long key) throws SQLException, InvalidSQLException;

    /**
     * Returns all objects in the table.
     * @return All objects in the table.
     * @throws SQLException
     */
    List<T> readAll() throws SQLException;

    /**
     * Updates an object.
     * @param t The object to edit.
     * @throws SQLException
     * @throws InvalidSQLException
     */
    void update(T t) throws SQLException, InvalidSQLException;

    /**
     * Deletes an object.
     * @param key The primary key of the object to delete.
     * @throws SQLException
     * @throws InvalidSQLException
     */
    void deleteById(long key) throws SQLException, InvalidSQLException;
}
