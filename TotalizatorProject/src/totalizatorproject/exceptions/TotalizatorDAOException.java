package totalizatorproject.exceptions;

public class TotalizatorDAOException extends Exception{

    public TotalizatorDAOException() {
    }

    public TotalizatorDAOException(String message) {
        super(message);
    }

    public TotalizatorDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public TotalizatorDAOException(Throwable cause) {
        super(cause);
    }

    public TotalizatorDAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
