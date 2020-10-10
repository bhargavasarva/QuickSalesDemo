package sa.com.stc.customerverification.web.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sa.com.stc.customerverification.web.model.AppUserDetails;

@FeignClient(url = "${login-api.url}", name="feignClient")
public interface CustomerVerificationFeignClient {

  @GetMapping("/validate")
  AppUserDetails validateJWT(@RequestParam String token);

}
