package com.byultudy.socketserver.lineup.exception;

public class LineupFinishException extends RuntimeException{

    public LineupFinishException() {
    }

    public LineupFinishException(final String message) {
        super(message);
    }

    public LineupFinishException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public LineupFinishException(final Throwable cause) {
        super(cause);
    }

    public LineupFinishException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}