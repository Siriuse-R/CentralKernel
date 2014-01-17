package net.siriuser.centralkernel.exception;

public class NotSupportedException extends Exception{
    private static final long serialVersionUID = -1151594657374375702L;

    public NotSupportedException(String message) {
        super(message);
    }

    public NotSupportedException(Throwable cause) {
        super(cause);
    }

    public NotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }
}
