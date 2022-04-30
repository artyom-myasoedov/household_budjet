package ru.vsu.hb_front.dto.response;


public class HBResponseData<T> {

    private final T data;
    private final String code;
    private final String errorMessage;

    public HBResponseData(T data, String code, String errorMessage) {
        this.data = data;
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public T getData() {
        return data;
    }

    public String getCode() {
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
