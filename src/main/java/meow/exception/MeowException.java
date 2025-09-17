package meow.exception;

public class MeowException extends Exception {
    public MeowException(String message) {
        super("Huh !?" + message);
    }
}
