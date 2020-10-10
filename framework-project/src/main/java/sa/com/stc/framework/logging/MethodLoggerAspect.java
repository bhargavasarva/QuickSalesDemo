/*
 * Copyright @2020 stc. All Rights reserved.
 * https://www.stc.com.sa
 */

package sa.com.stc.framework.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * Aspect class to log the method entry and exit
 */
@Aspect
@Configuration
public class MethodLoggerAspect {

  /**
   * The list of classes that needs logging during method entry and exits
   */
  public static final String CLASSES_TO_BE_LOGGED =
      "execution(public * sa.com.stc..*ServiceImpl.*(..)) ||"
          + "execution(public * sa.com.stc..*Validator.*(..)) ||"
          + "execution(public * sa.com.stc..*Util.*(..)) ||"
          + "execution(public * sa.com.stc..*Listener.*(..))";

  @Before(CLASSES_TO_BE_LOGGED)
  public void before(JoinPoint joinPoint) {
    Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
    if (logger.isDebugEnabled()) {
      logger.debug(String.format("Enter %s .", joinPoint.getSignature().getName()));
    }
  }

  @AfterReturning(CLASSES_TO_BE_LOGGED)
  public void afterReturning(JoinPoint joinPoint) {
    Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
    if (logger.isDebugEnabled()) {
      logger.debug(String.format("Exit %s .", joinPoint.getSignature().getName()));
    }
  }
}
