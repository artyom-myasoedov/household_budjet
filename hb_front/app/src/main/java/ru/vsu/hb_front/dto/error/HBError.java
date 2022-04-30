package ru.vsu.hb_front.dto.error;


public class HBError {

    private final String code;
    private final String message;

    public HBError(String code, String message) {
        this.code = code;
        this.message = message;
        LOG.debug(code + " - " + message);
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
