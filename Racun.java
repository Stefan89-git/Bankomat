package rs.itcentart;

public class Racun {
    private String brKartice;
    private int pin;
    private double stanje;

    public Racun(String brKartice, int pin, double stanje) {
        this.brKartice = brKartice;
        this.pin = pin;
        this.stanje = stanje;
    }

    public String getBrKartice() {
        return brKartice;
    }

    public void setBrKartice(String brKartice) {
        this.brKartice = brKartice;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public double getStanje() {
        return stanje;
    }

    public void setStanje(double stanje) {
        this.stanje = stanje;
    }

    @Override
    public String toString() {
        return "Racun{" + "brKartice=" + brKartice + ", pin=" + pin + ", stanje=" + stanje + '}';
    }
}
