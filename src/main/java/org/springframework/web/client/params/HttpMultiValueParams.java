package org.springframework.web.client.params;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.Serializable;

/**
 * Created by szhang54 on 2016/7/8.
 */
public class HttpMultiValueParams implements Serializable {

    //parameters from user
    private MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();

    public static HttpMultiValueParams param() {
        return new HttpMultiValueParams();
    }

    public static HttpMultiValueParams param(String key, Object value) {
        HttpMultiValueParams p = param();
        return p.putParam(key, value);
    }

    public HttpMultiValueParams putParam(String key, Object value) {
        param.add(key, value);
        return this;
    }

    public HttpMultiValueParams removeParam(String key) {
        param.remove(key);
        return this;
    }

    public HttpMultiValueParams clear() {
        param.clear();
        return this;
    }

    public MultiValueMap<String, Object> getParams(){
        return param;
    }

}
