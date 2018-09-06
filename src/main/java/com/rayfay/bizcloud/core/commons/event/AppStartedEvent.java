package com.rayfay.bizcloud.core.commons.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by stzhang on 2017/3/13.
 */
public class AppStartedEvent extends ApplicationEvent {

    private String appName;

    public AppStartedEvent(String source, String appName) {
        super(source);
        this.appName = source;
    }

    public AppStartedEvent(String source) {
        super(source);
    }

    @Override
    public Object getSource() {
        return super.getSource();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
