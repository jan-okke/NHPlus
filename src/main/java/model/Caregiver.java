package model;

/**
 * A caregiver class.
 */
public class Caregiver extends Person {

    private long cid;
    private String phoneNumber;

    /**
     * Creates a new caregiver with primary key.
     * @param cid The primary key of the caregiver.
     * @param firstName The firstname of the caregiver.
     * @param surname The surname of the caregiver.
     * @param phoneNumber The phone number of the caregiver.
     */
    public Caregiver(long cid, String firstName, String surname, String phoneNumber) {
        super(firstName, surname);

        this.cid = cid;
        this.phoneNumber = phoneNumber;
        this.surname = surname;
        this.firstName = firstName;
    }

    /**
     * Creates a new caregiver without the primary key.
     * @param firstName The firstname of the caregiver.
     * @param surname The surname of the caregiver.
     * @param phoneNumber The phone number of the caregiver.
     */
    public Caregiver(String firstName, String surname, String phoneNumber) {
        super(firstName, surname);

        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the primary key.
     * @return The primary key.
     */
    public long getCid() { return this.cid; }

    /**
     * Gets the phone number.
     * @return The phone number.
     */
    public String getPhoneNumber() { return this.phoneNumber; }

    @Override
    public String getFirstName() {
        return this.firstName;
    }

    @Override
    public String getSurname() {
        return this.surname;
    }


    /**
     * Sets the phone number.
     * @param phoneNumber The new phone number.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
