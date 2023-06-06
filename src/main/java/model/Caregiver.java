package model;

public class Caregiver extends Person {

    private long cid;
    private String phoneNumber;

    public Caregiver(long cid, String firstName, String surname, String phoneNumber) {
        super(firstName, surname);

        this.cid = cid;
        this.phoneNumber = phoneNumber;
    }

    public Caregiver(String firstName, String surname, String phoneNumber) {
        super(firstName, surname);

        this.phoneNumber = phoneNumber;
    }

    public long getCid() { return this.cid; }
    public String getPhoneNumber() { return this.phoneNumber; }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
