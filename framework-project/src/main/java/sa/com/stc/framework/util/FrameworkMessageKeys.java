/*
 * Copyright @2020 stc. All Rights reserved.
 * https://www.stc.com.sa
 */

package sa.com.stc.framework.util;

/**
 * PLM Framework message keys
 */
public class FrameworkMessageKeys {

  public static final String INTERNAL_SERVER_ERROR = "FWK-ERR-10000";
  public static final String DATA_VALIDATION_ERROR = "FWK-ERR-10001";
  public static final String TYPE_MISMATCH = "FWK-ERR-10002";
  public static final String CONSTRAINT_VIOLATION_ERROR = "FWK-ERR-10003";
  public static final String RUNTIME_ERROR = "FWK-ERR-10004";
  public static final String AUTHENTICATION_FAILURE = "FWK-ERR-10005";

  private FrameworkMessageKeys() {
    // Being a utility class, it should not be allowed to instantiate
  }
}
