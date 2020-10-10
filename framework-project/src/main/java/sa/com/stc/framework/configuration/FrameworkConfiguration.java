package sa.com.stc.framework.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.client.RestTemplate;

/**
 * Class to maintain PLM framework configurations
 */
@Configuration
public class FrameworkConfiguration {

  private static final String ORDER_APP_PROPERTY_FILE = "framework-messages";

  @Bean
  public ResourceBundleMessageSource messageSource() {
    final ResourceBundleMessageSource resourceBundleMessageSource =
        new ResourceBundleMessageSource();
    resourceBundleMessageSource.addBasenames(ORDER_APP_PROPERTY_FILE);
    return resourceBundleMessageSource;
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplateBuilder().build();
  }

  @Bean
  public MethodValidationPostProcessor methodValidationPostProcessor() {
    return new MethodValidationPostProcessor();
  }

  @Bean
  public RetryTemplate kafkaRetryTemplate() {
    final RetryTemplate retryTemplate = new RetryTemplate();

    final SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
    retryPolicy.setMaxAttempts(3);
    retryTemplate.setRetryPolicy(retryPolicy);

    final FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
    fixedBackOffPolicy.setBackOffPeriod(3 * 1000L);
    retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

    return retryTemplate;
  }
}
