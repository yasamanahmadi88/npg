package ix.portal.npg.exception;

import java.util.Objects;

public class DBException extends RuntimeException {

    private String message;
    private String code;

    public DBException(String code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public DBException(String code, String message, Throwable cause, String message1) {
        super(message, cause);
        this.message = message1;
        this.code = code;
    }

    public DBException(String code, String message, Throwable cause) {
        super(cause);
        this.message = message;
        this.code = code;
    }

    public DBException(
        String code,
        String message,
        Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace,
        String message1
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
        if (!(o instanceof DBException)) return false;
        DBException that = (DBException) o;
        return getMessage().equals(that.getMessage()) && getCode().equals(that.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMessage(), getCode());
    }
}


