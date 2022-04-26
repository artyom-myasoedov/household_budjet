package ru.vsu.hb.controller.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.vsu.hb.utils.ControllerUtils;

@ControllerAdvice
public class HBErrorHandler extends ResponseEntityExceptionHandler {

    private static Logger LOG = LoggerFactory.getLogger(HBErrorHandler.class);

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<?> handleConflict(Exception ex, WebRequest request) {
        LOG.error("[SYSTEM ERROR] Unexpected error handled", ex);
        return handleExceptionInternal(ex, ControllerUtils.fromException(ex), new HttpHeaders(),
                "Доступ запрещен".equals(ex.getMessage()) ? HttpStatus.FORBIDDEN : HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
