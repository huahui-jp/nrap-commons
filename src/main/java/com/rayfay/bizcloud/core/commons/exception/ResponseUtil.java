//package com.nrap.apps.commons.exception;
//
//import com.microservice.message.MessageCode;
//
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by shenfu on 2017/4/10.
// */
//public class ResponseUtil {
//
//    /**
//     * 分页结果
//     * @param rowsTotal 总件数
//     * @param rows 结果列表
//     * @param <T> 继承了Collection的对象
//     * @return 返回结果
//     */
//	public static <T extends Collection> Map<String, Object> makeSuccessResponse(long rowsTotal, T rows) {
//		Map<String, Object> responseMap = new HashMap<>();
//
//		responseMap.put("success", true);
//		responseMap.put("rowsTotal", rowsTotal);
//		responseMap.put("rows", rows);
//
//		return responseMap;
//	}
//
//    /**
//     * 不分页结果
//     * @param rows 结果列表
//     * @param <T> 继承了Collection的对象
//     * @return 返回结果
//     */
//	public static <T extends Collection> Map<String, Object> makeSuccessResponse(T rows) {
//		Map<String, Object> responseMap = new HashMap<>();
//
//		responseMap.put("success", true);
//		responseMap.put("rowsTotal", rows != null ? rows.size() : 0);
//		responseMap.put("rows", rows);
//
//		return responseMap;
//	}
//
//	public static Map<String, Object> makeSuccessResponse() {
//		Map<String, Object> responseMap = new HashMap<>();
//
//		responseMap.put("success", true);
//
//		return responseMap;
//	}
//
//    /**
//     * 包含错误信息的结果
//     * @param errorCode 错误代码
//     * @param errorMessage 错误内容
//     * @return 错误信息结果
//     */
//	public static Map<String, Object> makeErrorResponse(String errorCode, String errorMessage) {
//		Map<String, Object> responseMap = new HashMap<>();
//
//		responseMap.put("success", false);
//		responseMap.put("errorCode", errorCode);
//		responseMap.put("errorMessage", errorMessage);
//
//		return responseMap;
//	}
//
//    /**
//     * 包含错误信息的结果
//     * @param messageCode MessageCode
//     * @return 错误信息结果
//     */
//	public static Map<String, Object> makeErrorResponse(ExceptionCode messageCode) {
//		Map<String, Object> responseMap = new HashMap<>();
//
//		responseMap.put("success", false);
//		responseMap.put("errorCode", messageCode.getCode());
//		responseMap.put("errorMessage", messageCode.getMessage());
//
//		return responseMap;
//	}
//
//    /**
//     * 包含错误信息的结果
//     * @param messageCode MessageCode
//     * @param params 替换参数
//     * @return 错误信息结果
//     */
//	public static Map<String, Object> makeErrorResponse(ExceptionCode messageCode, String... params) {
//		Map<String, Object> responseMap = new HashMap<>();
//
//		responseMap.put("success", false);
//		responseMap.put("errorCode", messageCode.getCode());
//		responseMap.put("errorMessage", messageCode.getMessage(params));
//
//		return responseMap;
//	}
//}
