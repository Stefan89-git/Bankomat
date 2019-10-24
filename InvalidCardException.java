package rs.itcentart;

public class InvalidCardException extends Exception {
    private String cardNumber;

    public InvalidCardException(String cardNumber, String message) {
        super(message);
        this.cardNumber = cardNumber;
    }

    public InvalidCardException(String cardNumber, String message, Throwable cause) {
        super(message, cause);
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }
}
