package sa.com.stc.loginapi.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import sa.com.stc.framework.annotation.StcSpringBootApplication;

@StcSpringBootApplication
@EnableJpaRepositories(basePackages = {"sa.com.stc.login.repository"})
@EntityScan("sa.com.stc.login.model")
public class LoginApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(LoginApiApplication.class, args);
  }
}
