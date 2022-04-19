package ru.vsu.hb.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HBResponse<T> extends HBResponseData<T>{
    public HBResponse(T data) {
        super(data, "success", null);
    }
}
