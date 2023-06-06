package datastorage;

import model.LoginData;
import model.Patient;
import utils.DateConverter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class LoginDataDAO extends DAOimp<LoginData> {

    public LoginDataDAO(Connection conn) {
        super(conn);
    }

    @Override
    protected String getCreateStatementString(LoginData loginData) {
        return String.format("INSERT INTO logindata (username, salt, hash) VALUES ('%s', '%s', '%s')", loginData.getUsername(), loginData.getSalt(), loginData.getHash());
    }

    @Override
    protected String getReadByIDStatementString(long key) {
        return String.format("SELECT * FROM logindata WHERE lid = %d", key);
    }

    @Override
    protected LoginData getInstanceFromResultSet(ResultSet set) throws SQLException {
        return new LoginData(set.getLong(1), set.getString(2), set.getString(3), set.getString(4));
    }

    @Override
    protected String getReadAllStatementString() {
        return "SELECT * FROM logindata";
    }

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

    @Override
    protected String getUpdateStatementString(LoginData loginData) {
        return String.format("UPDATE logindata SET username = '%s', salt = '%s', hash = '%s' WHERE lid = '%d'", loginData.getUsername(), loginData.getSalt(), loginData.getHash(), loginData.getLid());
    }

    @Override
    protected String getDeleteStatementString(long key) {
        return String.format("DELETE FROM logindata WHERE lid = '%d'", key);
    }
}
