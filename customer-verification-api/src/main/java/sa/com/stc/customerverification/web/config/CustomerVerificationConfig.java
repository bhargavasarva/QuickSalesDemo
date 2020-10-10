package sa.com.stc.customerverification.web.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;


import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class CustomerVerificationConfig {

  private static final String CUSTOMER_VERIFICATION_APP_PROPERTY_FILE ="customer-verification-messages";

  private final ResourceBundleMessageSource resourceBundleMessageSource;

  @PostConstruct
  public ResourceBundleMessageSource loadResourceBundleMessageSource() {
    resourceBundleMessageSource.addBasenames(CUSTOMER_VERIFICATION_APP_PROPERTY_FILE);
    return resourceBundleMessageSource;
  }
}
