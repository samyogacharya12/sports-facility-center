package org.sports.facility.center.exception;

public class Invalid extends RuntimeException {

    private String message;

    private Object detail;

    public Invalid(String message, Object detail) {
        this.message = message;
        this.detail=detail;
    }
}
