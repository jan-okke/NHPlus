package exceptions;

public class InvalidSQLException extends Exception{

    public InvalidSQLException(String message) {
        super(message);
    }

    public InvalidSQLException() {
        super("Invalid SQL.");
    }
}
