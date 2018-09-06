package com.rayfay.bizcloud.core.commons.exception;

import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

/**
 * Created by stzhang on 2017/4/13.
 */
public class NRAPException extends RuntimeException{
    private int errorCode;
    private String messagePattern = null;
    public NRAPException(){
        super();
    }
    public NRAPException(ErrorCodeValuedEnum errorType, Object... params){
        super(ErrorCodeMessageFormatter.format(errorType.getName(), params));
        this.errorCode = errorType.getValue();
    }
    public NRAPException(int errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }


    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
    public static class ErrorCodeMessageFormatter {
        public static String format(String msg, Object... params){
            if(StringUtils.isNotEmpty(msg)) {
                String rsult = MessageFormat.format(msg, params);
                return rsult;
            }
            return null;
        }
    }

    public static NRAPException createNewInstance(ErrorCodeValuedEnum errorType, Object... params){
        NRAPException ex = new NRAPException(errorType, params);
        return ex;
    }
}
