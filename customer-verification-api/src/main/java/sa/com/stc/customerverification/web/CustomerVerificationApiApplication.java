package sa.com.stc.customerverification.web;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import sa.com.stc.framework.annotation.StcSpringBootApplication;

@StcSpringBootApplication
@EnableFeignClients
public class CustomerVerificationApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerVerificationApiApplication.class, args);
    }

}
