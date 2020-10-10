/*
 * Copyright @2020 stc. All Rights reserved.
 * https://www.stc.com.sa
 */

package sa.com.stc.framework.exception.util;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import sa.com.stc.framework.dto.Payload;
import sa.com.stc.framework.exception.DataException;
import sa.com.stc.framework.exception.FunctionalException;
import sa.com.stc.framework.exception.TechnicalException;
import sa.com.stc.framework.util.BilingualMessageGenerator;


/**
 * An Utility class to build the {@link TechnicalException}, {@link DataException} and {@link
 * FunctionalException}
 */
@Component
@Validated
@RequiredArgsConstructor
public class ExceptionBuilder {

  private final BilingualMessageGenerator messageGenerator;

  public void throwTechnicalException(
      @NotBlank String errorCode,
      @NotBlank String errorMessageCode) {
    throw new TechnicalException(
        errorCode,
        errorMessageCode,
        messageGenerator.generateMessage(errorMessageCode, ArrayUtils.EMPTY_STRING_ARRAY));
  }

  public void throwDataException(
      @NotBlank String errorCode,
      @NotBlank String errorMessageCode) {
    throw new DataException(
        errorCode,
        errorMessageCode,
        messageGenerator.generateMessage(errorMessageCode, ArrayUtils.EMPTY_STRING_ARRAY));
  }

  public void throwFunctionalException(
      @NotBlank String errorCode,
      @NotBlank String errorMessageCode) {
    throw new FunctionalException(
        errorCode,
        errorMessageCode,
        messageGenerator.generateMessage(errorMessageCode, ArrayUtils.EMPTY_STRING_ARRAY));
  }
}
