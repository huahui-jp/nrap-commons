//package com.nrap.apps.commons.exception;
//
//import java.text.MessageFormat;
//
///**
// * Created by shenfu on 2017/4/11.
// */
//public enum ExceptionCode {
//	/**
//	 * 该应用({0})已存在
//	 */
//	@Message(msg = "该应用({0})已存在！")
//	ERROR_0001,
//	/**
//	 * 指定的空间不存在
//	 */
//	@Message(msg = "指定的空间不存在！")
//	ERROR_0002,
//	/**
//	 * 应用创建失败
//	 */
//	@Message(msg = "应用创建失败！")
//	ERROR_0003,
//    /**
//     * 获取数据失败
//     */
//	@Message(msg = "获取数据失败！")
//    ERROR_0004,
//    /**
//     * 指定的应用不存在
//     */
//	@Message(msg = "指定的应用不存在！")
//	ERROR_0005,
//
//	/**
//	 * 请求操作失败
//	 */
//	@Message(msg = "请求操作失败！")
//	ERROR_0006,
//    /**
//     * 该应用下有{0}个应用实例正在运行，无法删除！
//     */
//    @Message(msg = "该应用下有{0}个应用实例正在运行，无法删除！")
//    ERROR_0007;
//
//	public String getCode() {
//		return this.name();
//	}
//
//	public String getMessage() {
//		Message message;
//		try {
//			message = this.getClass().getField(this.getCode()).getAnnotation(Message.class);
//		} catch (Exception e) {
//			return null;
//		}
//
//		return message.msg();
//	}
//
//	public String getMessage(String... params) {
//		Message message;
//		try {
//			message = this.getClass().getField(this.getCode()).getAnnotation(Message.class);
//		} catch (Exception e) {
//			return null;
//		}
//
//		return MessageFormat.format(message.msg(), (Object[]) params);
//	}
//}
