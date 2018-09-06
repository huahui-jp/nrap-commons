package com.rayfay.bizcloud.core.commons.json;

/**
 * Created by stzhang on 2016/2/26.
 */
public class JsonResult{
    protected boolean success;
    protected String message;
    protected int errorCode = 0;

    public JsonResult(){
        super();
    }
    public JsonResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public JsonResult(boolean success) {
        this(success, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static JsonResult result(boolean success){
        return new JsonResult(success);
    }

    public static JsonResult result(boolean success, String msg){
        return new JsonResult(success, msg);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}