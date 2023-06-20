package model;

/**
 * A base person class with firstname and surname.
 */
public abstract class Person {
    protected String firstName;
    protected String surname;

    /**
     * Constructor with firstname and surname.
     * @param firstName The firstname of the person.
     * @param surname The surname of the person.
     */
    public Person(String firstName, String surname) {
        this.firstName = firstName;
        this.surname = surname;
    }

    /**
     * Gets the firstname.
     * @return The firstname.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the firstname.
     * @param firstName The new firstname.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the surname.
     * @return The surname.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname.
     * @param surname The new surname.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }
}
