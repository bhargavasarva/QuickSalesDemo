package sa.com.stc.customerverification.web.api;

import static net.logstash.logback.argument.StructuredArguments.v;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sa.com.stc.customerverification.web.dto.CustomerContactDetails;
import sa.com.stc.framework.annotation.StcRestController;
import sa.com.stc.customerverification.web.service.CustomerVerificationService;

@StcRestController
@RequiredArgsConstructor
@Slf4j
public class CustomerVerificationApi {

    private final CustomerVerificationService customerVerificationService;

    @GetMapping("/customers/contact-details")
    public CustomerContactDetails verifyCustomerProfile(@RequestParam String idNumber, @RequestParam String idType) {
      log.info("ID Number: {} ", v("idNumber", idNumber));
      log.info("ID Type: {} ", v("idType", idType));
      return customerVerificationService.fetchCustomerContactDetails(idNumber, idType);
    }
}
