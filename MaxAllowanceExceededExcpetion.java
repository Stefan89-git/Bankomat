package rs.itcentart;

public class MaxAllowanceExceededExcpetion extends Exception {

    public MaxAllowanceExceededExcpetion(String message) {
        super(message);
    }

    public MaxAllowanceExceededExcpetion(String message, Throwable cause) {
        super(message, cause);
    }
}
