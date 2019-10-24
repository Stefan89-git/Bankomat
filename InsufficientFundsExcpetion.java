package rs.itcentart;

public class InsufficientFundsExcpetion extends Exception {

    private double novacZaPodizanje;

    public InsufficientFundsExcpetion(double novacZaPodizanje, String message) {
        super(message);
        this.novacZaPodizanje = novacZaPodizanje;
    }

    public InsufficientFundsExcpetion(double novacZaPodizanje, String message, Throwable cause) {
        super(message, cause);
        this.novacZaPodizanje = novacZaPodizanje;
    }

    public double getNovacZaPodizanje() {
        return novacZaPodizanje;
    }
}
