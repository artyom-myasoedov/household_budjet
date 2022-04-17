package ru.vsu.hb.dto.error;

public class BadRequestError extends HBError{
    public BadRequestError(String message) {
        super("bad_request", message);
    }
}
