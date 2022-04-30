package ru.vsu.hb_front.dto.response;


public class HBResponse<T> extends HBResponseData<T>{
    public HBResponse(T data) {
        super(data, "success", null);
    }
}
