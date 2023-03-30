package concurrency;

public class RejectedExecutionException extends RuntimeException {

    public RejectedExecutionException(String message) {
        super(message);
    }
}