/*
 * Copyright @2020 stc. All Rights reserved.
 * https://www.stc.com.sa
 */

package sa.com.stc.framework.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import sa.com.stc.framework.dto.BilingualMessage;
import sa.com.stc.framework.dto.Payload;
import sa.com.stc.framework.util.FrameworkConstants;


/**
 * Custom exception class to handle exceptions from PLM services
 */
@NoArgsConstructor
@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BaseException extends RuntimeException {

  protected String errorCode;
  protected String errorMessageCode;
  private BilingualMessage error;

  BaseException(
      String errorCode,
      String errorMessageCode,
      BilingualMessage error) {
    super(error.getEnglish());
    this.errorCode = errorCode;
    this.errorMessageCode = errorMessageCode;
    this.error = error;
  }
}
