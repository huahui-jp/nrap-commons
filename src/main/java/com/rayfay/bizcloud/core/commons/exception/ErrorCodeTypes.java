package com.rayfay.bizcloud.core.commons.exception;

import org.springframework.stereotype.Component;

/**
 * Created by stzhang on 2017/4/13.
 */

@ErrorCodeDefinition(thousands = 1)
public class ErrorCodeTypes {

    @ErrorCode(code = 1, msg = "获取的对象不存在 {0}")
    @ErrorCodeDescription("获取的对象不存在，当用户输入参数有误时可能出现")
    public static ErrorCodeValuedEnum E_1001;

    @ErrorCode(code = 2, msg = "系统异常，无法保存 {0}")
    @ErrorCodeDescription("系统异常，联系管理员查看系统日志，会得到更准确的信息")
    public static ErrorCodeValuedEnum E_1002;

    @ErrorCode(code = 3, msg = "对象已经存在，无法保存 {0}")
    @ErrorCodeDescription("系统异常，联系管理员查看系统日志，会得到更准确的信息")
    public static ErrorCodeValuedEnum E_1003;

    @ErrorCode(code = 4, msg = "对象不能为空，无法处理 {0}")
    @ErrorCodeDescription("系统异常，联系管理员查看系统日志，会得到更准确的信息")
    public static ErrorCodeValuedEnum E_1004;


    @ErrorCode(code = 10, msg = "用户未登录，没有权限操作")
    @ErrorCodeDescription("系统异常，需要用户登录才能访问的操作")
    public static ErrorCodeValuedEnum E_1010;

    @ErrorCode(code = 11, msg = "数据验证错误 {0}")
    @ErrorCodeDescription("数据验证错误，当用户输入参数有误时可能出现")
    public static ErrorCodeValuedEnum E_1011;

}
