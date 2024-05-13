package com.valletta.fintech.controller;

import com.valletta.fintech.dto.ResponseDto;
import com.valletta.fintech.dto.ResultObject;

public abstract class AbstractController {

    protected <T> ResponseDto<T> ok() {
        return ok(null, ResultObject.getSuccess());
    }

    protected <T> ResponseDto<T> ok(T data) {
        return ok(data, ResultObject.getSuccess());
    }

    protected <T> ResponseDto<T> ok(T data, ResultObject result) {
        ResponseDto<T> obj = new ResponseDto<>();

        obj.setResult(result);
        obj.setData(data);

        return obj;
    }
}
