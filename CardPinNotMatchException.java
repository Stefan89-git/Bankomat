package rs.itcentart;

public class CardPinNotMatchException extends Exception {

    public CardPinNotMatchException(String message) {
        super(message);
    }

    public CardPinNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
