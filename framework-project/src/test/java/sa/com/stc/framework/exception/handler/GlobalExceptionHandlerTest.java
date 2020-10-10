package sa.com.stc.framework.exception.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.method.HandlerMethod;
import sa.com.stc.framework.dto.BilingualMessage;
import sa.com.stc.framework.dto.StcResponse;
import sa.com.stc.framework.exception.DataException;
import sa.com.stc.framework.exception.FunctionalException;
import sa.com.stc.framework.exception.TechnicalException;
import sa.com.stc.framework.util.BilingualMessageGenerator;

@ExtendWith(SpringExtension.class)
class GlobalExceptionHandlerTest {

  @InjectMocks
  GlobalExceptionHandler globalExceptionHandler;
  @Mock
  BilingualMessageGenerator messageGenerator;

  HandlerMethod handlerMethod;
  BilingualMessage bilingualMessage;

  public void testMethod() {
  }

  @BeforeEach
  void setUp() throws NoSuchMethodException {
    Method method = GlobalExceptionHandlerTest.class.getMethod("testMethod");
    GlobalExceptionHandlerTest controller = new GlobalExceptionHandlerTest();
    handlerMethod = new HandlerMethod(controller, method);
    bilingualMessage = new BilingualMessage("Ø§Ø®ØªØ¨Ø§Ø±", "test");
  }

  @Test
  void handleConstraintViolationException() {
    ConstraintViolationException constraintViolationException = new ConstraintViolationException("",
        null);
    ResponseEntity<StcResponse> response = globalExceptionHandler
        .handleConstraintViolationException(constraintViolationException, handlerMethod);
    assertEquals("BAD_REQUEST", response.getBody().getStatus());
  }

  @Test
  void handlePlmException_FunctionalTest() {
    FunctionalException functionalException = new FunctionalException(
        "APP.FN.ERROR", "CRM_FAULT_RESPONSE", bilingualMessage);
    ResponseEntity<StcResponse> response = globalExceptionHandler
        .handlePlmException(functionalException, handlerMethod);
  }

  @Test
  void handlePlmException_TechnicalTest() {
    TechnicalException technicalException = new TechnicalException("APP.WS.ERROR", "FWK_ERR", bilingualMessage);
    ResponseEntity<StcResponse> response = globalExceptionHandler
        .handlePlmException(technicalException, handlerMethod);
  }

  @Test
  void handlePlmException_DataTest() {
    DataException dataException = new DataException("APP.DATA.ERROR", "FWK_ERR",
        bilingualMessage);
    ResponseEntity<StcResponse> response = globalExceptionHandler
        .handlePlmException(dataException, handlerMethod);
  }

  @Test
  void handleError() {
    Throwable throwable = new Throwable("broken pipeline");
    ResponseEntity<StcResponse> response = globalExceptionHandler
        .handleError(throwable, handlerMethod);
    assertEquals("MSC.RUNTIME.ERROR", response.getBody().getError().getErrorCode());
  }

  @Test
  void handleTypeMismatchException(){
    TypeMismatchException exception=new TypeMismatchException("Datatype mismatch", String.class);
    ResponseEntity<Object> response = globalExceptionHandler.handleTypeMismatch(exception,handlerMethod);
    assertEquals(400,response.getStatusCodeValue());
  }

}