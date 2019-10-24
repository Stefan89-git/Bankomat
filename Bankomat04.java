package rs.itcentart;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Bankomat04 {
    
    private static Map<String, Racun> racuni = new HashMap<>();
    private static Racun racun;

    public enum Akcija {
        STANJE_RACUNA(1), ISPLATA(2), UPLATA(3), UNKNOWN(4);

        private int akcija;

        private Akcija(int akcija) {
            this.akcija = akcija;
        }

        public int getAkcija() {
            return akcija;
        }

        public static Akcija fromIntValue(int akcija) {
            switch (akcija) {
                case 1:
                    return Akcija.STANJE_RACUNA;
                case 2:
                    return Akcija.ISPLATA;
                case 3:
                    return Akcija.UPLATA;
                default:
                    return Akcija.UNKNOWN;
            }
        }
    }

    public static void main(String[] args) {
        racuni.put("1003-1234-5678", new Racun("1003-1234-5678", 1234, 1000.45));
        racuni.put("1003-5678-1234", new Racun("1003-5678-1234", 5312, 100000.68));
        racuni.put("1004-4321-8765", new Racun("1004-4321-8765", 5678, 1000000.00));
        
        // ubacujemo karticu (unosimo br kartice i posle pin)
        System.out.print("Molimo Vas \"ubacite\" platnu karticu: ");
        Scanner input = new Scanner(System.in);
        String unesenaKartica = input.next();
        // proveri da li postoji kartica sa tim brojem
        try {
            if (proveriValidnostKartice(unesenaKartica) == true) {
                // unos pina
                System.out.print("Unesite Vas pin: ");
                int unesenPin = input.nextInt();

                // vadimo racun na osnovu broja racuna
                racun = racuni.get(unesenaKartica);
                if (racun == null) {
                    System.err.println("Pogresan broj kartice i/ili pina!");
                } else {
                    if (proveriPinove(racun.getPin(), unesenPin)) {
                        // meni da izberem akciju (stanje, isplata, uplata)
                        System.out.println("Odaberite neku od opcija: ");
                        System.out.println("    1.- Stanje");
                        System.out.println("    2.- Isplata");
                        System.out.println("    3.- Uplata");
                        // biram akciju
                        int opcija = input.nextInt();
                        Akcija akcija = Akcija.fromIntValue(opcija);
                        System.out.println(String.format("Odabrana akcija: %s", akcija));
                        switch (akcija) {
                            case STANJE_RACUNA:// 1.- Stanje
                                // ispisem trenutno stanje na racunu
                                stampajStanje();
                                break;
                            case ISPLATA:// 2.- Isplata
                                // unosim koliko zelim da podignem para
                                System.out.print("Unesite iznos za isplatu: ");
                                double novacZaPodizanje = input.nextDouble();
                                izvrsiIsplatu(novacZaPodizanje);
                                break;
                            case UPLATA:// 3.- Uplata
                                // unosim koliko para zelim da uplatim na racun
                                System.out.print("Unesite iznos za uplatu: ");
                                double novacZaUplatu = input.nextDouble();
                                izvrsiUplatu(novacZaUplatu);
                                break;
                            default:
                                throw new InvalidActionExcpetion(akcija, String.format("Nepostojeca akcija %s", akcija));
                        }
                    } else {
                        System.err.println("Pogresan broj kartice i/ili pina!");
                    }
                }
            }
        } catch (InvalidCardException ex) {
            System.err.println(ex.getMessage());
        } catch (CardPinNotMatchException ex) {
            System.err.println(ex.getMessage());
        } catch (MaxAllowanceExceededExcpetion ex) {
            System.err.println(ex.getMessage());
        } catch (InvalidSumForWithdrawalExcpetion ex) {
            System.err.println(ex.getMessage());
        } catch (InsufficientFundsExcpetion ex) {
            System.err.println(ex.getMessage());
        } catch (InvalidActionExcpetion ex) {
            System.err.println(ex.getMessage());
        }
    }

    // metoda za proveru validnosti broja kartice
    public static boolean proveriValidnostKartice(String unesenaKartica) throws InvalidCardException {
        Racun r = racuni.get(unesenaKartica);
        if(r == null) {
            throw new InvalidCardException(unesenaKartica, String.format("Nevalidan broj kartice %s", unesenaKartica));
        }
        return true;
    }

    public static void stampajStanje() {
        System.out.println("Stanje na vasem racunu je: " + racun.getStanje());
    }

    public static void izvrsiIsplatu(double novacZaPodizanje)
            throws MaxAllowanceExceededExcpetion, InvalidSumForWithdrawalExcpetion, InsufficientFundsExcpetion {
        // odjednom ne smem da podignem vise od 60000 din.
        if (novacZaPodizanje <= 0) {
            throw new InvalidSumForWithdrawalExcpetion(String.format("Nevalidna suma za isplatu %f", novacZaPodizanje));
        }
        if (novacZaPodizanje > Konstante.MAX_ZA_ISPLATU) {// ako se dize vise od 60k onda ispisuje gresku
            throw new MaxAllowanceExceededExcpetion(
                    String.format("Nije dozvoljeno podizati %f. Maksimalno dozvoljeno je %f",
                            novacZaPodizanje, Konstante.MAX_ZA_ISPLATU));
        }
        // ako nemam dovoljno para na racunu ispisujem gresku
        double trenutnoStanje = racun.getStanje();
        if (trenutnoStanje < novacZaPodizanje) {
            throw new InsufficientFundsExcpetion(novacZaPodizanje,
                    String.format("Nedovoljno sredstava na racunu za podizanje %f", novacZaPodizanje));
        }

        // umanjuje se stanje na racunu za tu sumu
        trenutnoStanje -= novacZaPodizanje;
        racun.setStanje(trenutnoStanje);

        // ako je isplata uspela ispisuje novo stanje na racunu
        System.out.println("Novo stanje Vaseg racuna je: " + trenutnoStanje);
    }

    public static void izvrsiUplatu(double zaUplatu) throws MaxAllowanceExceededExcpetion, InvalidSumForWithdrawalExcpetion {
        // odjednom ne smem da uplatim vise od 100000 din.
        if (zaUplatu <= 0) {
            throw new InvalidSumForWithdrawalExcpetion(String.format("Nevalidna suma za uplatu %f", zaUplatu));
        }
        if (zaUplatu > Konstante.MAX_ZA_UPLATU) {
            throw new MaxAllowanceExceededExcpetion(
                    String.format("Nije dozvoljeno uplatiti %f. Maksimalno dozvoljeno je %f",
                            zaUplatu, Konstante.MAX_ZA_UPLATU));
        }

        // stanje na racunu se uvecava za tu sumu
        double trenutnoStanje = racun.getStanje();
        trenutnoStanje += zaUplatu;
        racun.setStanje(trenutnoStanje);

        // ako je uplata uspela ispisuje novo stanje na racunu
        System.out.println("Novo stanje Vaseg racuna je: " + trenutnoStanje);
    }

    public static boolean proveriPinove(int pin1, int pin2) throws CardPinNotMatchException {
        if (pin1 != pin2) {
            throw new CardPinNotMatchException("Pinovi se ne pokalpaju");
        }
        return true;
    }
}
