package ru.vsu.hb.dto.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HBError {
    private final static Logger LOG = LoggerFactory.getLogger(HBError.class);

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
