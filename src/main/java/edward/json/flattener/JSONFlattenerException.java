package edward.json.flattener;

/**
 * This exception provides 1 exception to wrap other exceptions that may be thrown on the flattening of JSON.
 */
public class JSONFlattenerException extends Exception {
    /**
     * Constructs an exception to display the provided message and also the provided causing throwable
     * @param message the message to display
     * @param throwable the throwable that caused this exception
     */
    public JSONFlattenerException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
