package ru.vsu.hb.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "Ответ HBService")
public class HBResponseData<T> {

    @ApiModelProperty(value = "Тело ответа")
    private final T data;
    @ApiModelProperty(value = "Код ответа")
    private final String code;
    @ApiModelProperty(value = "Ошибка")
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
