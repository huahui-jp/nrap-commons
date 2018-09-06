package com.rayfay.bizcloud.core.commons.displayed;

/**
 * Created by stzhang on 2017/4/17.
 */

/**
 * 暂时先定义这么几个属性， 后面可以添加。
 */
public class DisplayedApplication {
    private String appId;
    private String appName;
    private String runningApplicationId;
    private String runningApplicationName;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getRunningApplicationId() {
        return runningApplicationId;
    }

    public void setRunningApplicationId(String runningApplicationId) {
        this.runningApplicationId = runningApplicationId;
    }

    public String getRunningApplicationName() {
        return runningApplicationName;
    }

    public void setRunningApplicationName(String runningApplicationName) {
        this.runningApplicationName = runningApplicationName;
    }
}
