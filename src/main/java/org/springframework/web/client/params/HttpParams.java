package org.springframework.web.client.params;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stzhang on 2015/12/24.
 */
public class HttpParams implements Serializable {
    //parameters from user
    private HashMap<String, Object> param = new HashMap<String, Object>();

    public static HttpParams param() {
        return new HttpParams();
    }

    public static HttpParams param(String key, Object value) {
        HttpParams p = param();
        return p.putParam(key, value);
    }

    public HttpParams putParam(String key, Object value) {
        param.put(key, value);
        return this;
    }


    public HttpParams removeParam(String key) {
        param.remove(key);
        return this;
    }

    public HttpParams clear() {
        param.clear();
        return this;
    }

    public Map<String, Object> getParams(){
        return param;
    }

    @Deprecated
    public Map<String, Object> getSingleParams(){
        return param ;
    }
}
