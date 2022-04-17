package ru.vsu.hb.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HBErrorResponse extends HBResponseData<Void>{
    public HBErrorResponse(String code, String errorMessage) {
        super(null, code, errorMessage);
    }
}
