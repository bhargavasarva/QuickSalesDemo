package sa.com.stc.customerverification.web.service;

import sa.com.stc.customerverification.web.dto.CustomerContactDetails;

public interface CustomerVerificationService {
  String DATA_NOT_FOUND = "CP-ERR-1001";
  String FDA_CONNECTION_FAILURE = "CP-ERR-1002";

  CustomerContactDetails fetchCustomerContactDetails(String idNumber, String idType);
}
