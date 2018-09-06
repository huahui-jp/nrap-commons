package com.rayfay.bizcloud.core.commons.model;


import javax.persistence.*;
import java.util.Date;

/**
 * Created by wzhang on 2018/2/5.
 */
public class OperateLog {

	public static final int C_OPER_RESULT_SUCC= 1;

	public static final int C_OPER_RESULT_FAIL= 2;

	public static final int C_TYPE_EXCEPTION= 1;

	public static final int C_TYPE_AUDIT= 2;

	public static final int C_TYPE_OTHER= 3;

	/*
	 * 系统名称
	 */
	private String appName;

	/*
	 * ClientId
	 */
	private String clientId;

	/*
	 * 实例名称
	 */
	private String springApplicationName;

	/*
	 * 日志类型
	 */
	private int type;

	/*
	 * 业务模块名称
	 */
	private String businessName;

	/*
	 * 操作人ID
	 */
	private String operatorAccount;

	/*
	 * 操作人姓名
	 */
	private String operatorName;

	/*
	 * 操作人请求IP
	 */
	private String operatorIp;

	/*
	 * 发生时间
	 */
	private long operateTime;

	/*
	 * 操作明细
	 */
	private String operateDetail;

	/*
	 * 操作结果(1:成功;2:失败)
	 */
	private int operateResult;
	
	/*
	 * 备注
	 */
	private String logComment;

	public String getOperatorIp() {
		return operatorIp;
	}

	public void setOperatorIp(String operatorIp) {
		this.operatorIp = operatorIp;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getOperatorAccount() {
		return operatorAccount;
	}

	public void setOperatorAccount(String operatorAccount) {
		this.operatorAccount = operatorAccount;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public long getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(long operateTime) {
		this.operateTime = operateTime;
	}

	public String getOperateDetail() {
		return operateDetail;
	}

	public void setOperateDetail(String operateDetail) {
		this.operateDetail = operateDetail;
	}

	public int getOperateResult() {
		return operateResult;
	}

	public void setOperateResult(int operateResult) {
		this.operateResult = operateResult;
	}

	public String getLogComment() {
		return logComment;
	}

	public void setLogComment(String logComment) {
		this.logComment = logComment;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getSpringApplicationName() {
		return springApplicationName;
	}

	public void setSpringApplicationName(String springApplicationName) {
		this.springApplicationName = springApplicationName;
	}
}
