package sa.com.stc.loginapi.web.config;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
@RequiredArgsConstructor
public class LoginApiConfig {

  private static final String LOGIN_APP_PROPERTY_FILE ="login-api-messages";

  private final ResourceBundleMessageSource resourceBundleMessageSource;

  @PostConstruct
  public ResourceBundleMessageSource loadResourceBundleMessageSource() {
    resourceBundleMessageSource.addBasenames(LOGIN_APP_PROPERTY_FILE);
    return resourceBundleMessageSource;
  }
}
