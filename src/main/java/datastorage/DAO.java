package datastorage;

import exceptions.InvalidSQLException;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
    void create(T t) throws SQLException, InvalidSQLException;

    T read(long key) throws SQLException, InvalidSQLException;

    List<T> readAll() throws SQLException;

    void update(T t) throws SQLException, InvalidSQLException;

    void deleteById(long key) throws SQLException, InvalidSQLException;
}
