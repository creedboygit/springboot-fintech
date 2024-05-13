package com.valletta.fintech.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.valletta.fintech.exception.BaseException;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude
public class ResponseDto<T> implements Serializable {

    private ResultObject result;
    private T data;

    public ResponseDto(ResultObject result) {
        this.result = result;
    }

    public ResponseDto(T data) {
        this.data = data;
    }

    public static <T> ResponseDto<T> ok() {
        return new ResponseDto<>(ResultObject.getSuccess());
    }

    public static <T> ResponseDto<T> ok(T data) {
        return new ResponseDto<>(ResultObject.getSuccess(), data);
    }

    public static <T> ResponseDto<T> response(T data) {
        return new ResponseDto<>(ResultObject.getSuccess(), data);
    }

    public ResponseDto(BaseException ex) {
        this.result = new ResultObject(ex);
    }
}
