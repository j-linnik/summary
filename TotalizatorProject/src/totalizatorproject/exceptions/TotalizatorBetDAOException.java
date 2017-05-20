package totalizatorproject.exceptions;

public class TotalizatorBetDAOException extends TotalizatorDAOException{

    public TotalizatorBetDAOException() {
    }

    public TotalizatorBetDAOException(String message) {
        super(message);
    }

    public TotalizatorBetDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public TotalizatorBetDAOException(Throwable cause) {
        super(cause);
    }

    public TotalizatorBetDAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}