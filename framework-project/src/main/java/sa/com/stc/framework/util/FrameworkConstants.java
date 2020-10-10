/*
 * Copyright @2020 stc. All Rights reserved.
 * https://www.stc.com.sa
 */

package sa.com.stc.framework.util;

/**
 *  Framework Constants
 */
public class FrameworkConstants {

  public static final String APIGEE_X_API_KEY = "x-apikey";
  public static final String ACCESS_TOKEN = "access_token";
  public static final String CONTENT_TYPE = "Content-Type";
  public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";
  public static final String AUTHORIZATION_HEADER = "Authorization";
  public static final String AUTHORIZATION_BASIC_HEADER = "Basic";
  public static final String AUTHORIZATION_BEARER_HEADER = "Bearer";
  public static final String CHARSET_ASCII = "US-ASCII";
  public static final String PLM_BASE_PACKAGE = "sa.com.stc";
  public static final String ERROR_DETAILS_NOT_PROVIDED = "Error Details Not Provided";
  public static final String MICROSERVICE = "MSC";
  public static final String REQUEST_PAYLOAD = "requestPayload";
  public static final String RESPONSE_PAYLOAD = "responsePayload";

  /* Error Codes **/
  public static final String WEB_SERVICE_INVOCATION_ERROR = "APP.WS.ERROR";
  public static final String DATABASE_ERROR = "APP.DB.ERROR";
  public static final String JMS_ERROR = "MSC.JMS.ERROR";
  public static final String DATA_VALIDATION_ERROR = "MSC.DATAVALIDATION.ERROR";
  public static final String SMTP_ERROR = "MSC.SMTP.ERROR";
  public static final String MAPPING_ERROR = "MSC.MAP.ERROR";
  public static final String RUNTIME_ERROR = "MSC.RUNTIME.ERROR";
  public static final String CACHE_ERROR = "MSC.CACHE.ERROR";
  public static final String LOOKUP_ERROR = "MSC.LOOKUP.ERROR";
  public static final String EXTERNAL_SYSTEM_FUNCTIONAL_ERROR = "APP.FN.ERROR";
  public static final String HTTP_INVOCATION_ERROR = "APP.HTTP.ERROR";
  public static final String AUTHENTICATION_FAILURE = "MSC.AUTH.ERROR";

  private FrameworkConstants() {
    // Being a utility class, it should not be allowed to instantiate
  }
}
