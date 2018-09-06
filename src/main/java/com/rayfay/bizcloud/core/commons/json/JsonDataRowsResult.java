package com.rayfay.bizcloud.core.commons.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by STZHANG on 2017/5/17.
 */
public class JsonDataRowsResult<T> {
    protected boolean success;
    protected String message;
    protected int errorCode = 0 ;
    @JsonProperty("result")
    protected JsonDataRows<T> data;

    public JsonDataRowsResult(){
        super();
        this.success = true;
    }
    public JsonDataRowsResult(boolean f){
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

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public JsonDataRows<T> getData() {
        return data;
    }

    public void setData(List<T> dataList) {
        JsonDataRows<T> jsonDataRows = new JsonDataRows<>();
        jsonDataRows.setRows(dataList);
        this.data = jsonDataRows;
    }

    public void setData(List<? extends T> dataList, long totalNumber) {
        JsonDataRows<T> jsonDataRows = new JsonDataRows<>();
        jsonDataRows.setRows(dataList);
        jsonDataRows.setTotalNumber(totalNumber);
        this.data = jsonDataRows;
    }

    protected class JsonDataRows<W>{
        @JsonProperty("rows")
        private List<? extends W> rows;

        @JsonProperty("totalNum")
        private long totalNumber;

        public long getTotalNumber() {
            return totalNumber;
        }

        public void setTotalNumber(long totalNumber) {
            this.totalNumber = totalNumber;
        }

        public List<? extends W> getRows() {
            return rows;
        }

        public void setRows(List<? extends W> rows) {
            this.rows = rows;
        }
    }
}
