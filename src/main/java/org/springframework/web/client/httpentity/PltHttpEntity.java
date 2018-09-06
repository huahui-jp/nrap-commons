package org.springframework.web.client.httpentity;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.apache.http.HttpHeaders.CONTENT_TYPE;

/**
 * Created by szhang54 on 2016/6/30.
 */
public class PltHttpEntity
{
  final private Map<String, Object> httpParameters = new HashMap<String, Object>();
  final private MultiValueMap<String, Object> httpMultiValueParameters = new LinkedMultiValueMap<String, Object>();
  final private MultiValueMap<String, String> httpEntityHeaders = new LinkedMultiValueMap<>();
  private Object httpEntityBody = null;
  public PltHttpEntity(){
    super();
  }

  public PltHttpEntity(MediaType... mediaTypes){
    super();
    this.addMediaType(mediaTypes);
  }

  public PltHttpEntity putParam(String key, Object value) throws IllegalArgumentException{
    if(! httpMultiValueParameters.isEmpty()){
      throw new IllegalArgumentException("you can not use putParam and addMultiValueParam at the same time.");
    }
    httpParameters.put(key, value);
    return this;
  }

  public PltHttpEntity addMultiValueParam(String key, Object value) throws IllegalArgumentException{
    if( !httpParameters.isEmpty()){
        throw new IllegalArgumentException("you can not use putParam and addMultiValueParam at the same time.");
    }
    httpMultiValueParameters.add(key, value);
    return this;
  }

  public PltHttpEntity addMultiValueParam(String key, Object... values) throws IllegalArgumentException{
    if( !httpParameters.isEmpty()){
      throw new IllegalArgumentException("you can not use putParam and addMultiValueParam at the same time.");
    }
    if(values != null){
      for (Object value : values) {
        httpMultiValueParameters.add(key, value);
      }
    }
    return this;
  }

  public PltHttpEntity addMediaType(MediaType... mediaTypes){
    MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
    List<String> headerContentTypeValues = httpEntityHeaders.get(CONTENT_TYPE);
    if(headerContentTypeValues == null){
      headerContentTypeValues = new LinkedList<String>();
    }
    if(mediaTypes != null){
      for (MediaType mediaType : mediaTypes)
      {
        headerContentTypeValues.add(mediaType.toString());
      }
    }
    httpEntityHeaders.put(CONTENT_TYPE, headerContentTypeValues );
    return this;
  }

  public PltHttpEntity setHttpEntityBody(Object body){
     this.httpEntityBody = body;
     return this;
  }
  /**
   * create
   * @return
   */
  public HttpEntity build(){
     if(this.httpEntityBody == null && httpMultiValueParameters.isEmpty()){
         this.httpEntityBody = httpParameters;
     }else if(this.httpEntityBody == null && httpParameters.isEmpty()){
       this.httpEntityBody = httpMultiValueParameters;
     }
     return  new HttpEntity<>(httpEntityBody, httpEntityHeaders);
  }

}
