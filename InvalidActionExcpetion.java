package rs.itcentart;

import rs.itcentart.Bankomat04.Akcija;

public class InvalidActionExcpetion extends Exception {
    private Akcija akcija;

    public InvalidActionExcpetion(Akcija akcija, String message) {
        super(message);
        this.akcija = akcija;
    }

    public InvalidActionExcpetion(Akcija akcija, String message, Throwable cause) {
        super(message, cause);
        this.akcija = akcija;
    }

    public Akcija getAkcija() {
        return akcija;
    }
}
