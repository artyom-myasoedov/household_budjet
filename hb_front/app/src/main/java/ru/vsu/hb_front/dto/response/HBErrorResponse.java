package ru.vsu.hb_front.dto.response;


public class HBErrorResponse extends HBResponseData{
    public HBErrorResponse(String code, String errorMessage) {
        super(null, code, errorMessage);
    }
}
