package sa.com.stc.framework.annotation;

import org.apache.kafka.common.errors.RetriableException;
import org.springframework.core.annotation.AliasFor;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import java.lang.annotation.*;
import java.net.ConnectException;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Retryable
public @interface StcRetryable {

  @AliasFor(annotation = Retryable.class, attribute = "value")
  Class<? extends Throwable>[] value() default {
      RecoverableDataAccessException.class,
      TransientDataAccessException.class,
      RetriableException.class,
          ConnectException.class
  };

  @AliasFor(annotation = Retryable.class, attribute = "maxAttempts")
  int maxAttempts() default 5;

  @AliasFor(annotation = Retryable.class, attribute = "maxAttemptsExpression")
  String maxAttemptsExpression() default "";

  @AliasFor(annotation = Retryable.class, attribute = "backoff")
  Backoff backoff() default @Backoff(delay = 2000L);

  @AliasFor(annotation = Retryable.class, attribute = "exceptionExpression")
  String exceptionExpression() default "";
}
