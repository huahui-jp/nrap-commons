package org.springframework.web.client;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jooq.tools.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by szhang54 on 2016/6/23.
 */
public class PltRestTemplate extends RestTemplate
{

  private ClientHttpRequestFactory defaultRequestFactory = new SimpleClientHttpRequestFactory();

  public PltRestTemplate(){
      super();
      CloseableHttpClient httpClient = new DefaultHttpClient();
      HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
      this.setRequestFactory(httpComponentsClientHttpRequestFactory);
  }

  //post map.
  public Map<String, Object> postForMap(String url, HttpEntity request, Map<String, ?> uriVariables){
    return super.postForObject(url, request, HashMap.class, uriVariables);
  }

  public Map<String, Object> postForMap(String url, HttpEntity request){
    return super.postForObject(url, request, HashMap.class);
  }

  public JSONObject postForJSON(String url, HttpEntity request, Map<String, ?> uriVariables){
    return super.postForObject(url, request, JSONObject.class, uriVariables);
  }

  public JSONObject postForJSON(String url, HttpEntity request){
    return super.postForObject(url, request, JSONObject.class);
  }
  //
  public Map<String, Object> getForMap(String url, Map<String, Object> request){
    return getForObject(url, request, HashMap.class );
  }

  public JSONObject getForJSON(String url, Map<String, Object> request){
    return getForObject(url, request, JSONObject.class);
  }

  //
  public Map<String, Object> getForMap(String url, MultiValueMap<String, String> request){
    return getForObject(url, request, HashMap.class );
  }

  public JSONObject getForJSON(String url, MultiValueMap<String, String> request){
    return getForObject(url, request, JSONObject.class);
  }

  //
  public Map<String, Object> getForMap(String url, Object request, Map<String, ?> uriVariables){
    return getForObject(url, HashMap.class, uriVariables );
  }

  public JSONObject getForJSON(String url, Object request, Map<String, ?> uriVariables){
    return getForObject(url, JSONObject.class, uriVariables);
  }

  public <T> T getForObject(String url, Map<String, Object> urlParameters, Class<T> responseType) throws RestClientException
  {
    if(urlParameters == null){
      return super.getForObject(url, responseType);
    }
    RequestCallback requestCallback = acceptHeaderRequestCallback(responseType);
    HttpMessageConverterExtractor<T> responseExtractor = new HttpMessageConverterExtractor<T>(responseType, getMessageConverters(), logger);
    URI expanded = escapeUrlPatternWithParameters(url, urlParameters);
    return doExecute(expanded, HttpMethod.GET, requestCallback, responseExtractor);
  }

  public <T> T getForObject(String url, MultiValueMap<String, String> urlParameters, Class<T> responseType) throws RestClientException
  {
    if(urlParameters == null){
      return super.getForObject(url, responseType);
    }
    RequestCallback requestCallback = acceptHeaderRequestCallback(responseType);
    HttpMessageConverterExtractor<T> responseExtractor = new HttpMessageConverterExtractor<T>(responseType, getMessageConverters(), logger);
    URI expanded = UriComponentsBuilder.fromHttpUrl(url).queryParams(urlParameters).build().toUri();
    return doExecute(expanded, HttpMethod.GET, requestCallback, responseExtractor);

  }

  public <T> T putForObject(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables)
          throws RestClientException {
    RequestCallback requestCallback = httpEntityCallback(request, responseType);
    HttpMessageConverterExtractor<T> responseExtractor =
            new HttpMessageConverterExtractor<T>(responseType, getMessageConverters(), logger);
    return execute(url, HttpMethod.PUT, requestCallback, responseExtractor, uriVariables);
  }


  public <T> ResponseEntity<T> putForEntity(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables)
          throws RestClientException {

    RequestCallback requestCallback = httpEntityCallback(request, responseType);
    ResponseExtractor<ResponseEntity<T>> responseExtractor = responseEntityExtractor(responseType);
    return execute(url, HttpMethod.PUT, requestCallback, responseExtractor, uriVariables);
  }


  public <T> T putForObject(String url, Object request, Class<T> responseType)
          throws RestClientException {
    RequestCallback requestCallback = httpEntityCallback(request, responseType);
    HttpMessageConverterExtractor<T> responseExtractor =
            new HttpMessageConverterExtractor<T>(responseType, getMessageConverters(), logger);
    return execute(url, HttpMethod.PUT, requestCallback, responseExtractor);
  }


  public <T> ResponseEntity<T> putForEntity(String url, Object request, Class<T> responseType)
          throws RestClientException {

    RequestCallback requestCallback = httpEntityCallback(request, responseType);
    ResponseExtractor<ResponseEntity<T>> responseExtractor = responseEntityExtractor(responseType);
    return execute(url, HttpMethod.PUT, requestCallback, responseExtractor);
  }

  /** escape url with parameters.
   * @param url
   * @param parameters
   * @return
   */
  private URI escapeUrlPatternWithParameters(String url, Map<String, Object> parameters){
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
    if(parameters != null){
      Iterator<Map.Entry<String, Object>>  pa = parameters.entrySet().iterator();
      while (pa.hasNext()){
        Map.Entry<String, Object> entry = pa.next();
        String paramName = entry.getKey();
        Object paramValue = entry.getValue();
        builder.queryParam(paramName, paramValue);
      }
    }
    return builder.build().toUri();
  }

  public String appendUrlWithParameters(String url, MultiValueMap<String, String> parameters){
    return UriComponentsBuilder.fromHttpUrl(url).queryParams(parameters).build().toString();
  }

  /**
   * create httpRequest,
   * 1. when sessionCookie enabled. use HttpDefaultFactory.
   * 2. when sessionCookie disabled. use HttpComponentsClientHttpRequestFactory.
   * @param url
   * @param method
   * @return
   * @throws IOException
   */
  @Override
  protected ClientHttpRequest createRequest(URI url, HttpMethod method) throws IOException {
    ClientHttpRequestFactory requestFactory = getDefaultRequestFactory();
    if(HttpSessionContext.cookieEnabled() ){
      requestFactory = getRequestFactory();
    }
    ClientHttpRequest request = requestFactory.createRequest(url, method);
    request.getHeaders();
    if (logger.isDebugEnabled()) {
      logger.debug("Created " + method.name() + " request for \"" + url + "\"");
    }
    return request;
  }

//  private CloseableHttpClient getInnerHttpClient(ClientHttpRequest request){
//    try {
//      Field m = request.getClass().getDeclaredField("httpClient");
//      m.setAccessible(true);
//      Object result = m.get(request);
//      m.setAccessible(false);
//      return (CloseableHttpClient) result;
//    } catch (Exception e) {
//      return null;
//    }
//
//  }


  private ClientHttpRequestFactory getDefaultRequestFactory() {
    ClientHttpRequestFactory delegate = this.defaultRequestFactory;
    if (!CollectionUtils.isEmpty(getInterceptors())) {
      return new InterceptingClientHttpRequestFactory(delegate, getInterceptors());
    }
    else {
      return delegate;
    }
  }



  public static class HttpSessionContext
  {
      private final static ThreadLocal<Boolean> TL_COOKIES = new ThreadLocal<>();

    /**
     * enabled cooke in ThreadLocal scope.
     */
      public static void enabledCookie(){
        TL_COOKIES.set(Boolean.TRUE);
      }
    /**
     * disabled cooke in ThreadLocal scope.
     */
      public static void disabledCookie(){
        TL_COOKIES.set(Boolean.FALSE);
      }

      private static boolean cookieEnabled(){
         Boolean b = TL_COOKIES.get();
         if(b != null){
            return  b.booleanValue();
         }
         return true;
      }

  }

}
