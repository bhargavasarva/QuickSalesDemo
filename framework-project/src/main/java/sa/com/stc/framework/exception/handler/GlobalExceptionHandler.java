package sa.com.stc.framework.exception.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.util.ContentCachingRequestWrapper;
import sa.com.stc.framework.dto.BilingualMessage;
import sa.com.stc.framework.dto.Payload;
import sa.com.stc.framework.dto.StcResponse;
import sa.com.stc.framework.exception.BaseException;
import sa.com.stc.framework.exception.dto.ErrorDetail;
import sa.com.stc.framework.util.BilingualMessageGenerator;
import sa.com.stc.framework.util.FrameworkConstants;
import sa.com.stc.framework.util.FrameworkMessageKeys;

/**
 * Global exception handler to handle all the exceptions thrown from PLM modules
 */
@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {

  private final BilingualMessageGenerator messageGenerator;

  private final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * This method handles the spring validation exception that arise due to data type mismatch
   */
  @ExceptionHandler(TypeMismatchException.class)
  protected ResponseEntity<Object> handleTypeMismatch(
      TypeMismatchException ex, HandlerMethod handlerMethod) {
    {
      final String detailedErrorDesc =
          String.format(
              "%s :: %s should be of type %s",
              ex.getMessage(), ex.getValue(), ex.getRequiredType().getName());
      log.error(
          "Data Type Mismatch Error :: {} :: inside {} method of {}",
          detailedErrorDesc,
          handlerMethod.getMethod().getName(),
          handlerMethod.getMethod().getDeclaringClass());
      final BilingualMessage message =
          messageGenerator.generateMessage(FrameworkMessageKeys.TYPE_MISMATCH, null);
      final ErrorDetail error =
          constructErrorDetail(
              FrameworkConstants.DATA_VALIDATION_ERROR,
              FrameworkMessageKeys.TYPE_MISMATCH,
              message);
      return new ResponseEntity<>(
          constructStcResponse(null, HttpStatus.BAD_REQUEST.name(), error), HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * Error handling for @Valid
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex, HandlerMethod handlerMethod, HttpServletRequest request) {
    try {
      final ContentCachingRequestWrapper wrapper = (ContentCachingRequestWrapper) request;
      log.error(
          "Request Payload :: {}",
          IOUtils.toString(wrapper.getContentAsByteArray(), wrapper.getCharacterEncoding()));
      final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
      final List<String> detailedErrors = new ArrayList<>();

      fieldErrors.stream()
          .forEach(
              error ->
                  detailedErrors.add(
                      "@" + error.getField().toUpperCase() + ": " + error.getDefaultMessage()));
      log.error(
          "Spring Validation Error :: {} :: inside {} method of {}",
          detailedErrors.toString(),
          handlerMethod.getMethod().getName(),
          handlerMethod.getMethod().getDeclaringClass());

      final BilingualMessage message =
          messageGenerator.generateMessage(FrameworkMessageKeys.DATA_VALIDATION_ERROR, null);
      final ErrorDetail error =
          constructErrorDetail(
              FrameworkConstants.DATA_VALIDATION_ERROR,
              FrameworkMessageKeys.DATA_VALIDATION_ERROR,
              message);

      return new ResponseEntity<>(
          constructStcResponse(null, HttpStatus.BAD_REQUEST.name(), error), HttpStatus.BAD_REQUEST);
    } catch (IOException exception) {
      log.error("Error in exception handler :: {}", exception.getMessage());
      return new ResponseEntity<>(
          constructStcResponse(
              null, HttpStatus.BAD_REQUEST.name(), constructGenericError(exception.getMessage())),
          HttpStatus.BAD_REQUEST);
    }
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<StcResponse> handleConstraintViolationException(
      ConstraintViolationException exception, HandlerMethod handlerMethod) {
    log.error(
        "Constraint Violation(s) Error :: {} :: inside {} method of {}",
        exception.getMessage(),
        handlerMethod.getMethod().getName(),
        handlerMethod.getMethod().getDeclaringClass());
    final ErrorDetail error =
        constructErrorDetail(
            FrameworkConstants.RUNTIME_ERROR,
            FrameworkMessageKeys.CONSTRAINT_VIOLATION_ERROR,
            messageGenerator.generateMessage(
                FrameworkMessageKeys.CONSTRAINT_VIOLATION_ERROR, null));

    return new ResponseEntity<>(
        constructStcResponse(null, HttpStatus.BAD_REQUEST.name(), error), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BaseException.class)
  public ResponseEntity<StcResponse> handlePlmException(
      BaseException exception, HandlerMethod handlerMethod) {
    log.error(
        "PLM Error :: {} :: inside {} method of {}",
        exception.getMessage(),
        handlerMethod.getMethod().getName(),
        handlerMethod.getMethod().getDeclaringClass());
    final ResponseStatus status = exception.getClass().getAnnotation(ResponseStatus.class);
    final ErrorDetail error =
        constructErrorDetail(
            exception.getErrorCode(),
            exception.getErrorMessageCode(),
            exception.getError());

    return new ResponseEntity<>(
        constructStcResponse(null, status.code().name(), error), status.code());
  }

  @ExceptionHandler(Throwable.class)
  public ResponseEntity<StcResponse> handleError(Throwable throwable, HandlerMethod handlerMethod) {
    log.error(
        "Unknown Error :: {} :: inside {} method of {}",
        throwable.getMessage(),
        handlerMethod.getMethod().getName(),
        handlerMethod.getMethod().getDeclaringClass());
    final ErrorDetail error =
        constructErrorDetail(
            FrameworkConstants.RUNTIME_ERROR,
            FrameworkMessageKeys.INTERNAL_SERVER_ERROR,
            messageGenerator.generateMessage(FrameworkMessageKeys.INTERNAL_SERVER_ERROR, null));

    return new ResponseEntity<>(
        constructStcResponse(null, HttpStatus.INTERNAL_SERVER_ERROR.name(), error),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ErrorDetail constructErrorDetail(
      final String errorCode,
      final String errorMessageCode,
      final BilingualMessage errorMessage) {
    return ErrorDetail.builder()
        .errorCode(errorCode)
        .errorMessageCode(errorMessageCode)
        .errorMessage(errorMessage)
        .build();
  }

  private ErrorDetail constructGenericError(final String detailedErrorMessage) {
    final BilingualMessage message =
        messageGenerator.generateMessage(FrameworkMessageKeys.RUNTIME_ERROR, null);
    return ErrorDetail.builder()
        .errorCode(FrameworkConstants.RUNTIME_ERROR)
        .errorMessageCode(FrameworkMessageKeys.RUNTIME_ERROR)
        .errorMessage(message)
        .build();
  }

  private StcResponse constructStcResponse(
      final Payload responsePayload, final String status, final ErrorDetail errorDetail) {
    final StcResponse stcResponse =
        StcResponse.builder()
            .responsePayload(responsePayload)
            .status(status)
            .error(errorDetail)
            .build();
    try {
      log.error(
          "Exception handled, final response payload :: {}",
          objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(stcResponse));
    } catch (JsonProcessingException ex) {
      log.error("Error during pretty printing response :: {}", ex.getMessage());
    }
    return stcResponse;
  }
}
