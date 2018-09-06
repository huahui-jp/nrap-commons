package com.rayfay.bizcloud.core.commons.clientinfo;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by shenfu on 2017/7/12.
 */
@ConfigurationProperties(prefix = "clientInfo")
public class ClientInfo {
    @JSONField(name = "clientId")
    private String clientId;
    @JSONField(name = "clientKey")
    private String clientKey;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    @Override
    public String toString() {
        return "ClientInfo{" +
                "clientId='" + clientId + '\'' +
                ", clientKey='" + clientKey + '\'' +
                '}';
    }
}
