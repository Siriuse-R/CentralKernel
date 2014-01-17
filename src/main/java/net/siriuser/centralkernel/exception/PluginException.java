package net.siriuser.centralkernel.exception;

public class PluginException extends Exception{
    private static final long serialVersionUID = -1L;

    public PluginException(String message) {
        super(message);
    }

    public PluginException(Throwable cause) {
        super(cause);
    }

    public PluginException(String message, Throwable cause) {
        super(message, cause);
    }
}
