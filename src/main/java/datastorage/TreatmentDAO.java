package datastorage;

import model.Treatment;
import utils.DateConverter;
import utils.Encryption;


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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TreatmentDAO extends DAOimp<Treatment> {

    protected String encryptTreatment(String input) throws InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return Encryption.encryptString(input);
    }

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

    protected String getCreateArchiveStatementString(Treatment treatment) throws InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return String.format("INSERT INTO treatment_archive (tid, pid, treatment_date, begin, end, description, remarks, treatment_finished_date) VALUES " +
                        "(%d, %d, '%s', '%s', '%s', '%s', '%s', '%s')",
                treatment.getTid(), treatment.getPid(), encryptTreatment(treatment.getDate()),
                encryptTreatment(treatment.getBegin()), encryptTreatment(treatment.getEnd()), encryptTreatment(treatment.getDescription()),
                encryptTreatment(treatment.getRemarks()), DateConverter.convertStringToLocalDate(LocalDate.now().toString()));
    }

    @Override
    protected String getReadByIDStatementString(long key) {
        return String.format("SELECT * FROM treatment WHERE tid = %d", key);
    }

    protected String getReadByPIDStatementString(long key) {
        return String.format("SELECT * FROM treatment WHERE pid = %d", key);
    }

    protected String getReadByCIDStatementString(long key) {
        return String.format("SELECT * FROM treatment WHERE cid = %d", key);
    }

    @Override
    protected Treatment getInstanceFromResultSet(ResultSet result) throws SQLException {
        LocalDate date = DateConverter.convertStringToLocalDate(result.getString(4));
        LocalTime begin = DateConverter.convertStringToLocalTime(result.getString(5));
        LocalTime end = DateConverter.convertStringToLocalTime(result.getString(6));
        Treatment m = new Treatment(result.getLong(1), result.getLong(2), result.getLong(3),
                date, begin, end, result.getString(7), result.getString(8));
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
            LocalDate date = DateConverter.convertStringToLocalDate(result.getString(4));
            LocalTime begin = DateConverter.convertStringToLocalTime(result.getString(5));
            LocalTime end = DateConverter.convertStringToLocalTime(result.getString(6));
            t = new Treatment(result.getLong(1), result.getLong(2), result.getLong(3),
                    date, begin, end, result.getString(7), result.getString(8));
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

    public void archiveByPid(long key) throws SQLException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Statement st = conn.createStatement();
        //Liest aus Treatment einen Eintrag aus, konvertiert ihn in ein Treatment objekt und schreibt dieses in die Treatment_Archive Tabelle.
        ResultSet result = st.executeQuery(getReadByPIDStatementString(key));
        if(result.next()){
            Treatment treatmentArchive = getInstanceFromResultSet(result);
            st.executeUpdate(getCreateArchiveStatementString(treatmentArchive));
            st.executeUpdate(String.format("Delete FROM treatment WHERE pid= %d", key));
        }

    }

    public void archiveByTid(long key) throws SQLException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Statement st = conn.createStatement();
        //Liest aus Treatment einen Eintrag aus, konvertiert ihn in ein Treatment objekt und schreibt dieses in die Treatment_Archive Tabelle.
        ResultSet result = st.executeQuery(getReadByIDStatementString(key));
        if(result.next()){
            Treatment treatmentArchive = getInstanceFromResultSet(result);
            st.executeUpdate(getCreateArchiveStatementString(treatmentArchive));
            st.executeUpdate(String.format("Delete FROM treatment WHERE tid= %d", key));
        }

    }

    public void archiveByCid(long key) throws SQLException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Statement st = conn.createStatement();
        //Liest aus Treatment einen Eintrag aus, konvertiert ihn in ein Treatment objekt und schreibt dieses in die Treatment_Archive Tabelle.
        ResultSet result = st.executeQuery(getReadByCIDStatementString(key));
        if(result.next()){
            Treatment treatmentArchive = getInstanceFromResultSet(result);
            st.executeUpdate(getCreateArchiveStatementString(treatmentArchive));
            st.executeUpdate(String.format("Delete FROM treatment WHERE cid= %d", key));
        }

    }
}