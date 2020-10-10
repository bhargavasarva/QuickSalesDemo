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
 * Custom exception class for PLM exceptions related to data. For e.g., data validation errors,
 * constraint violations, etc
 */
@NoArgsConstructor
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class DataException extends BaseException {

  public DataException(
      String errorCode,
      String errorMessageCode,
      BilingualMessage message) {
    super(
        errorCode,
        errorMessageCode,
        message);
  }
}
