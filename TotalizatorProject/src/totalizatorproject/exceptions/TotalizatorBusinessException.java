package totalizatorproject.exceptions;

public class TotalizatorBusinessException extends TotalizatorDAOException{

    public TotalizatorBusinessException() {
    }

    public TotalizatorBusinessException(String message) {
        super(message);
    }

    public TotalizatorBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public TotalizatorBusinessException(Throwable cause) {
        super(cause);
    }

    public TotalizatorBusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
