package datastorage;

import model.Treatment;
import utils.DateConverter;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TreatmentDAO extends DAOimp<Treatment> {

    public TreatmentDAO(Connection conn) {
        super(conn);
    }

    @Override
    protected String getCreateStatementString(Treatment treatment) {
        return String.format("INSERT INTO treatment (pid, treatment_date, begin, end, description, remarks) VALUES " +
                "(%d, '%s', '%s', '%s', '%s', '%s')", treatment.getPid(), treatment.getDate(),
                treatment.getBegin(), treatment.getEnd(), treatment.getDescription(),
                treatment.getRemarks());
    }

    protected String getCreateArchiveStatementString(Treatment treatment) {
        return String.format("INSERT INTO treatment_archive (pid, treatment_date, begin, end, description, remarks, treatment_finished_date) VALUES " +
                        "(%d, '%s', '%s', '%s', '%s', '%s', '%s')", treatment.getPid(), treatment.getDate(),
                treatment.getBegin(), treatment.getEnd(), treatment.getDescription(),
                treatment.getRemarks(), DateConverter.convertStringToLocalDate(LocalDate.now().toString()));
    }

    @Override
    protected String getReadByIDStatementString(long key) {
        return String.format("SELECT * FROM treatment WHERE tid = %d", key);
    }

    @Override
    protected Treatment getInstanceFromResultSet(ResultSet result) throws SQLException {
        LocalDate date = DateConverter.convertStringToLocalDate(result.getString(3));
        LocalTime begin = DateConverter.convertStringToLocalTime(result.getString(4));
        LocalTime end = DateConverter.convertStringToLocalTime(result.getString(5));
        Treatment m = new Treatment(result.getLong(1), result.getLong(2),
                date, begin, end, result.getString(6), result.getString(7));
        return m;
    }

    @Override
    protected String getReadAllStatementString() {
        return "SELECT * FROM treatment";
    }

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

    @Override
    protected String getUpdateStatementString(Treatment treatment) {
        return String.format("UPDATE treatment SET pid = %d, treatment_date ='%s', begin = '%s', end = '%s'," +
                "description = '%s', remarks = '%s' WHERE tid = %d", treatment.getPid(), treatment.getDate(),
                treatment.getBegin(), treatment.getEnd(), treatment.getDescription(), treatment.getRemarks(),
                treatment.getTid());
    }

    @Override
    protected String getDeleteStatementString(long key) {
        return String.format("Delete FROM treatment WHERE tid= %d", key);
    }

    public List<Treatment> readTreatmentsByPid(long pid) throws SQLException {
        ArrayList<Treatment> list = new ArrayList<Treatment>();
        //Treatment object = null;
        Statement st = conn.createStatement();
        ResultSet result = st.executeQuery(getReadAllTreatmentsOfOnePatientByPid(pid));
        list = getListFromResultSet(result);
        return list;
    }

    private String getReadAllTreatmentsOfOnePatientByPid(long pid){
        return String.format("SELECT * FROM treatment WHERE pid = %d", pid);
    }

    /*
    public void deleteByPid(long key) throws SQLException {
        Statement st = conn.createStatement();
        st.executeUpdate(String.format("Delete FROM treatment WHERE pid= %d", key));
    }
     */
    public void archiveByTid(long key) throws SQLException {
        Statement st = conn.createStatement();
        //Liest aus Treatment einen Eintrag aus, konvertiert ihn in ein Treatment objekt und schreibt dieses in die Treatment_Archive Tabelle.
        ResultSet result = st.executeQuery(getReadByIDStatementString(key));
        if(result.next()){
            Treatment treatmentArchive = getInstanceFromResultSet(result);
            st.executeUpdate(getCreateArchiveStatementString(treatmentArchive));
            st.executeUpdate(String.format("Delete FROM treatment WHERE tid= %d", key));
        }
    }

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