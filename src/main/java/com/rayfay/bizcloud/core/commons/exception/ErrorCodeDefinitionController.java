package com.rayfay.bizcloud.core.commons.exception;

import com.google.common.collect.Lists;
import com.rayfay.bizcloud.core.commons.json.JsonDataResult;
import org.apache.commons.lang.enums.EnumUtils;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by stzhang on 2017/4/13.
 */
@Configuration
@AutoConfigureOrder(ErrorCodeDefinitionController.ORDER)
@ConditionalOnClass({ErrorCodeValuedEnum.class, ErrorCodeTypes.class})
@RestController
@RequestMapping("/api")
public class ErrorCodeDefinitionController {
    public static final int ORDER = -100;
    @RequestMapping(value = "/api-error-codes", method = RequestMethod.GET)
    public JsonDataResult listErrorCode() {
        JsonDataResult jsonDataResult = new JsonDataResult();
        jsonDataResult.setSuccess(true);
        List<ErrorCodeValuedEnum> vEnums = EnumUtils.getEnumList(ErrorCodeValuedEnum.class);
        List<VErrorCode> list = Lists.newArrayList();
        for (ErrorCodeValuedEnum vEnum : vEnums) {
            VErrorCode vErrorCode = new VErrorCode();
            vErrorCode.setErrorCode(vEnum.getValue()); //异常的编号； ErrorCode 1002，1003,1004
            vErrorCode.setErrorMessage(vEnum.getName());//对于的异常信息，带格式化参数。
            vErrorCode.setDescription(vEnum.getDescription());
            list.add(vErrorCode);
        }
        jsonDataResult.setData(list);
        return jsonDataResult;
    }

    /**
     * ErrorCode 输出对象。
     */
    protected class VErrorCode{
        private int errorCode;
        private String errorMessage;
        private String description;

        public int getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
