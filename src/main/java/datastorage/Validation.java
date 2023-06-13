package datastorage;

import exceptions.InvalidSQLException;
import model.Caregiver;
import model.Patient;
import model.Treatment;

public class Validation {

    // die müssen alle lowercase angegeben werden, da bei der Überprüfung erst in lowercase umgewandelt wird um z.B.
    // unterschiedliche Fälle von or OR Or oR abzudecken
    private static final String[] invalidSigns = new String[] {";", "\\", "or"};

    public static void validateLong(long l) throws InvalidSQLException {
        if (l < 0) {
            throw new InvalidSQLException();
        }

    }

    public static void validateString(String s) throws InvalidSQLException {
        for (String invalid : invalidSigns) {
            if (s.toLowerCase().contains(invalid)) {
                throw new InvalidSQLException();
            }
        }
    }

    public static void validateTreatment(Treatment t) throws InvalidSQLException {
        validateLong(t.getPid());
        validateLong(t.getTid());
        validateString(t.getRemarks());
        validateString(t.getBegin());
        validateString(t.getDescription());
        validateString(t.getEnd());
        validateString(t.getDate());
    }

    public static void validatePatient(Patient p) throws InvalidSQLException {
        validateLong(p.getPid());
        validateString(p.getCareLevel());
        validateString(p.getDateOfBirth());
        validateString(p.getRoomnumber());
        validateString(p.getFirstName());
        validateString(p.getSurname());
    }

    public static void validateCaregiver(Caregiver c) throws InvalidSQLException {
        validateLong(c.getCid());
        validateString(c.getPhoneNumber());
        validateString(c.getFirstName());
        validateString(c.getSurname());
    }
}
