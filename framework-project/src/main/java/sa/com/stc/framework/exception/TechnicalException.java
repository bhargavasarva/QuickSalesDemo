/*
 * Copyright @2020 stc. All Rights reserved.
 * https://www.stc.com.sa
 */

package sa.com.stc.framework.exception;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import sa.com.stc.framework.dto.BilingualMessage;
import sa.com.stc.framework.dto.Payload;
import sa.com.stc.framework.exception.enums.ErrorType;
import sa.com.stc.framework.util.FrameworkConstants;


/**
 * Custom exception class to handle PLM technical exceptions which includes but not limited to
 * connection time-out, database connection failure, web service invocation errors, etc.
 */
@NoArgsConstructor
@ResponseStatus(
    code = HttpStatus.INTERNAL_SERVER_ERROR,
    reason = "Internal error. Please contact IT help desk.")
public class TechnicalException extends BaseException {

  public TechnicalException(
      String errorCode,
      String errorMessageCode,
      BilingualMessage message) {
    super(
        errorCode,
        errorMessageCode,
        message);
  }
}
