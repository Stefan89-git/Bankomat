package rs.itcentart;

public class CardNotFoundException extends Exception {
    private String cardNumber;

    public CardNotFoundException(String cardNumber, String message) {
        super(message);
        this.cardNumber = cardNumber;
    }

    public CardNotFoundException(String cardNumber, String message, Throwable cause) {
        super(message, cause);
        this.cardNumber = cardNumber;
    }
}
