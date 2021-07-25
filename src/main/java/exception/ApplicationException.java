package exception;

public class ApplicationException extends RuntimeException {

    private String message;

    public ApplicationException(String message) {
        this.message = message;
    }
}
