/*
 * Copyright @2020 stc. All Rights reserved.
 * https://www.stc.com.sa
 */

package sa.com.stc.framework.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import sa.com.stc.framework.dto.BilingualMessage;
import sa.com.stc.framework.dto.Payload;
import sa.com.stc.framework.exception.enums.ErrorType;


/**
 * PLM exception class catering to functional (Internal/External) exceptions
 */
@NoArgsConstructor
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class FunctionalException extends BaseException {

  public FunctionalException(
      String errorCode,
      String errorMessageCode,
      BilingualMessage message) {
    super(
        errorCode,
        errorMessageCode,
        message);
  }
}
