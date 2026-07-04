package ix.portal.npg.exception;

import java.util.Objects;

public class IxssException extends Exception {

    private String message;
    private String code;

    public IxssException(String message, String code) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public IxssException(String message, Throwable cause, String message1, String code) {
        super(message, cause);
        this.message = message1;
        this.code = code;
    }

    public IxssException(Throwable cause, String message, String code) {
        super(cause);
        this.message = message;
        this.code = code;
    }

    public IxssException(
        String message,
        Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace,
        String message1,
        String code
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
        message = message1;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IxssException)) return false;
        IxssException that = (IxssException) o;
        return getMessage().equals(that.getMessage()) && getCode().equals(that.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMessage(), getCode());
    }
}


