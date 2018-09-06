package com.rayfay.bizcloud.core.commons.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by stzhang on 2016/2/26.
 */
public class JsonDataResult<T>{
    protected boolean success;
    protected String message;
    protected int errorCode = 0 ;
    @JsonProperty("result")
    protected T data;
    public JsonDataResult(){
        super();
        this.success = true;
    }
    public JsonDataResult(boolean f){
        this.success = f;
    }
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
