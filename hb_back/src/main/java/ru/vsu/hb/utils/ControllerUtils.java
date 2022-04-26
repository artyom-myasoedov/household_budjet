package ru.vsu.hb.utils;

import com.leakyabstractions.result.Result;
import org.springframework.http.ResponseEntity;
import ru.vsu.hb.dto.error.BadRequestError;
import ru.vsu.hb.dto.error.HBError;
import ru.vsu.hb.dto.response.HBErrorResponse;
import ru.vsu.hb.dto.response.HBResponse;
import ru.vsu.hb.dto.response.HBResponseData;

import static org.springframework.web.servlet.function.ServerResponse.badRequest;

public class ControllerUtils {

    public static <T> ResponseEntity<? extends HBResponseData<T>> toHBResult(Result<T, HBError> result) {
        if (result.isSuccess()) {
            return ResponseEntity.ok(new HBResponse<>(result.optional()
                    .orElseThrow(() -> new IllegalStateException("result is success but value in result is null"))));
        } else {
            var error = result.optionalFailure()
                    .orElse(new HBError("unknown_error", "Impossible situation!!! result is not success and failure is null at the same time!"));
            if (error instanceof BadRequestError) {
                return ResponseEntity.badRequest().body((HBResponseData<T>) new HBErrorResponse(error.getCode(), error.getMessage()));
            } else {
                return ResponseEntity.ok((HBResponseData<T>) new HBErrorResponse(error.getCode(), error.getMessage()));
            }
    }
    }

    public static HBErrorResponse fromException(Exception e) {
        if ("Доступ запрещен".equals(e.getMessage())) {
            return new HBErrorResponse("forbidden", e.getMessage());
        }
        return new HBErrorResponse("unknown_error", e.getMessage());
    }

}
