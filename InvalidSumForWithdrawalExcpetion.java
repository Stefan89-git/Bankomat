package rs.itcentart;

public class InvalidSumForWithdrawalExcpetion extends Exception {

    public InvalidSumForWithdrawalExcpetion(String message) {
        super(message);
    }

    public InvalidSumForWithdrawalExcpetion(String message, Throwable cause) {
        super(message, cause);
    }
    
}
