package org.springframework.web.client.httpentity;

import org.springframework.http.MediaType;

/**
 * Created by szhang54 on 2016/6/30.
 */
public class PltHttpEntityBuilder
{

  private final static MediaType contentType = MediaType.APPLICATION_JSON;

  public static PltHttpEntity create(){
    return new PltHttpEntity(MediaType.MULTIPART_FORM_DATA);
  }

  public static PltHttpEntity createDefault(){
    return new PltHttpEntity(contentType);
  }

  public static PltHttpEntity createApplicationJsonHttpEntity(){
     return new PltHttpEntity(MediaType.APPLICATION_JSON);
  }

  public static PltHttpEntity createAllHttpEntity(){
    return new PltHttpEntity(MediaType.ALL);
  }

  public static PltHttpEntity createStreamHttpEntity(){
    return new PltHttpEntity(MediaType.APPLICATION_OCTET_STREAM);
  }

  public static PltHttpEntity createMultipartFormDataHttpEntity(){
    return new PltHttpEntity(MediaType.MULTIPART_FORM_DATA);
  }

}
