package ru.vsu.hb.utils;

import com.leakyabstractions.result.Result;
import org.springframework.http.ResponseEntity;
import ru.vsu.hb.dto.error.BadRequestError;
import ru.vsu.hb.dto.error.HBError;
import ru.vsu.hb.dto.response.HBErrorResponse;
import ru.vsu.hb.dto.response.HBResponse;
import ru.vsu.hb.dto.response.HBResponseData;

public class ControllerUtils {

    public static <T> ResponseEntity<? super HBResponseData<? super T>> toHBResult(Result<T, HBError> result) {
        if (result.isSuccess()) {
            return ResponseEntity.ok(new HBResponse<>(result.optional()
                    .orElseThrow(() -> new IllegalStateException("result is success but value in result is null"))));
        } else {
            return fromError(result.optionalFailure()
                    .orElse(new HBError("unknown_error", "Impossible situation!!! result is not success and failure is null at the same time!")));
        }
    }

    public static ResponseEntity<HBResponseData<?>> fromError(HBError error) {
        if (error instanceof BadRequestError) {
            return ResponseEntity.badRequest().body(new HBErrorResponse(error.getCode(), error.getMessage()));
        } else {
            return ResponseEntity.ok(new HBErrorResponse(error.getCode(), error.getMessage()));
        }
    }

    public static HBErrorResponse fromException(Exception e) {
        return new HBErrorResponse("unknown_error", e.getMessage());
    }
}
