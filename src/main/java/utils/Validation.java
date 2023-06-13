package utils;

import exceptions.InvalidSQLException;
import model.Caregiver;
import model.Patient;
import model.Treatment;

public class Validation {

    // die müssen alle lowercase angegeben werden, da bei der Überprüfung erst in lowercase umgewandelt wird um z.B.
    // unterschiedliche Fälle von or OR Or oR abzudecken
    private static final String[] invalidSigns = new String[] {";", "\\", "or"};

    /**
     * Validates a long, and throws an exception if malformed.
     * @param l The long to validate.
     * @throws InvalidSQLException
     */
    public static void validateLong(long l) throws InvalidSQLException {
        if (l < 0) {
            throw new InvalidSQLException();
        }

    }

    /**
     * Validates a string, and throws an exception if malformed.
     * @param s The string to validate.
     * @throws InvalidSQLException
     */
    public static void validateString(String s) throws InvalidSQLException {
        for (String invalid : invalidSigns) {
            if (s.toLowerCase().contains(invalid)) {
                throw new InvalidSQLException();
            }
        }
    }

    /**
     * Validates a treatment, and throws an exception if malformed.
     * @param treatment The treatment to validate.
     * @throws InvalidSQLException
     */
    public static void validateTreatment(Treatment treatment) throws InvalidSQLException {
        validateLong(treatment.getPid());
        validateLong(treatment.getTid());
        validateString(treatment.getRemarks());
        validateString(treatment.getBegin());
        validateString(treatment.getDescription());
        validateString(treatment.getEnd());
        validateString(treatment.getDate());
    }

    /**
     * Validates a patient, and throws an exception if malformed.
     * @param patient The patient to validate.
     * @throws InvalidSQLException
     */
    public static void validatePatient(Patient patient) throws InvalidSQLException {
        validateLong(patient.getPid());
        validateString(patient.getCareLevel());
        validateString(patient.getDateOfBirth());
        validateString(patient.getRoomnumber());
        validateString(patient.getFirstName());
        validateString(patient.getSurname());
    }

    /**
     * Validates a caregiver, and throws an exception if malformed.
     * @param caregiver The caregiver to validate.
     * @throws InvalidSQLException
     */
    public static void validateCaregiver(Caregiver caregiver) throws InvalidSQLException {
        validateLong(caregiver.getCid());
        validateString(caregiver.getPhoneNumber());
        validateString(caregiver.getFirstName());
        validateString(caregiver.getSurname());
    }
}
