package ru.vsu.hb.utils;

import com.leakyabstractions.result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.vsu.hb.dto.error.HBError;
import ru.vsu.hb.dto.response.HBResponseData;

import static ru.vsu.hb.security.SecurityConstants.HEADER_STRING;

public class HBResponseBuilder<T> {

    private ResponseEntity<? super HBResponseData<? super T>> response;

    public HBResponseBuilder(ResponseEntity<? super HBResponseData<? super T>> response) {
        this.response = response;
    }

    public static <T> HBResponseBuilder<T> fromHBResult(Result<T, HBError> result) {
        return new HBResponseBuilder<>(ControllerUtils.toHBResult(result));
    }

    public HBResponseBuilder<T> withAuthToken(String token) {
        response = ResponseEntity.status(response.getStatusCode())
                .header(HEADER_STRING, token)
                .body(response.getBody());
        return this;
    }

    public HBResponseBuilder<T> withStatus(HttpStatus status) {
        response = ResponseEntity.status(status)
                .body(response.getBody());
        return this;
    }

    public ResponseEntity<? super HBResponseData<? super T>> build() {
        return response;
    }
}
