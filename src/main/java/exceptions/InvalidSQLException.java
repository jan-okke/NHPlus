package exceptions;

/**
 * Invalid SQL Exception for malformed SQL.
 */
public class InvalidSQLException extends Exception{

    /**
     * Constructor with message.
     * @param message The message.
     */
    public InvalidSQLException(String message) {
        super(message);
    }

    /**
     * Constructor without message (default message)
     */
    public InvalidSQLException() {
        super("Invalid SQL.");
    }
}
