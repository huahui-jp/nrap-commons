package com.rayfay.bizcloud.core.commons.exception;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * Created by stzhang on 2017/4/13.
 */
public class ErrorCodeValuedEnum extends ValuedEnum {

    private String description = null;
    public ErrorCodeValuedEnum(String name, int value) {
        super(name, value);
    }

    public ErrorCodeValuedEnum(int value, String name) {
        super(name, value);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


