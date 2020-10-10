package sa.com.stc.customerverification.web.service.impl;

import static net.logstash.logback.argument.StructuredArguments.v;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import sa.com.stc.customerverification.web.dto.CustomerContactDetails;
import sa.com.stc.customerverification.web.dto.avro.customer;
import sa.com.stc.customerverification.web.dto.avro.customerEvent;
import sa.com.stc.customerverification.web.infra.persistence.crm.CrmCustomerDetailsRepository;
import sa.com.stc.customerverification.web.infra.persistence.fda.CustomerDetailsRepository;
import sa.com.stc.customerverification.web.model.crm.CrmCustomerIdentity;
import sa.com.stc.customerverification.web.model.crm.CrmCustomerProfile;
import sa.com.stc.customerverification.web.model.fda.CustomerIdentity;
import sa.com.stc.customerverification.web.model.fda.CustomerProfile;
import sa.com.stc.customerverification.web.service.CustomerVerificationService;
import sa.com.stc.framework.exception.DataException;
import sa.com.stc.framework.exception.util.ExceptionBuilder;
import sa.com.stc.framework.util.FrameworkConstants;
import sa.com.stc.framework.util.FrameworkMessageKeys;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerVerificationServiceImpl implements CustomerVerificationService {

  private static final String RESPONSE_PAYLOAD_FROM_FDA = "From FDA";
  private static final String RESPONSE_PAYLOAD_FROM_SOR = "From SOR";
  private static final String RESPONSE_PAYLOAD_TO_KAFKA = "To Kafka";
  private String id_number;
  @Value("${kafka.topic}")
  private String kafkaTopic;

  private final ExceptionBuilder exceptionBuilder;
  private final CustomerDetailsRepository customerDetailsRepository;
  private final CrmCustomerDetailsRepository crmCustomerDetailsRepository;
  private final CustomerContactDetails customerResponse = new CustomerContactDetails();
  private final ObjectMapper objectMapper;
  private final KafkaTemplate<String, customerEvent> kafkaTemplate;


  sa.com.stc.customerverification.web.dto.avro.customerEvent customerEvent=new customerEvent();

  public CustomerContactDetails fetchCustomerContactDetails(String idNumber, String idType) {
    try {
      Optional<CustomerProfile> customerprofile = customerDetailsRepository.findById
              (new CustomerIdentity(idNumber, idType));

      if (customerprofile.isPresent()) {
        log.info("Fetching from FDA");
        customerResponse.setName(customerprofile.get().getFirstName());
        customerResponse.setDateOfBirth(customerprofile.get().getDateOfBirth());
        customerResponse.setIdExpiryDate(customerprofile.get().getIdExpiryDt());
        customerResponse.setPrimaryContactNumber(customerprofile.get().getPrimaryContactNumber());
        log.info("Response Payload From FDA :: {}", objectMapper.writeValueAsString(customerResponse), v(FrameworkConstants.RESPONSE_PAYLOAD, RESPONSE_PAYLOAD_FROM_FDA));
        return customerResponse;

      } else {
        log.info("searching in crm db");
        Optional<CrmCustomerProfile> crmCustomerProfile = crmCustomerDetailsRepository.findById(new CrmCustomerIdentity(idNumber, idType));
        if (crmCustomerProfile.isPresent()) {
          log.info("fetching from crm db");
          customerResponse.setName(crmCustomerProfile.get().getFirstName());
          customerResponse.setDateOfBirth(crmCustomerProfile.get().getDateOfBirth());
          customerResponse.setIdExpiryDate(crmCustomerProfile.get().getIdExpiryDt());
          customerResponse.setPrimaryContactNumber(crmCustomerProfile.get().getPrimaryContactNumber());
          customer customerData=customer.newBuilder().setIdNumber(crmCustomerProfile.get().getCustomerIdentity().getIdNumber())
                  .setIdType(crmCustomerProfile.get().getCustomerIdentity().getIdType())
                  .setFirstName(crmCustomerProfile.get().getFirstName())
                  .setMiddleName(crmCustomerProfile.get().getMiddleName())
                  .setLastName(crmCustomerProfile.get().getLastName())
                  .setCustomerDob(crmCustomerProfile.get().getDateOfBirth())
                  .setIdExpiryDt(crmCustomerProfile.get().getIdExpiryDt())
                  .setPrimaryContact(crmCustomerProfile.get().getPrimaryContactNumber())
                  .setIdEffectiveDt(crmCustomerProfile.get().getIdEffectiveDt())
                  .build();
          log.info("Response Payload From SOR :: {}", objectMapper.writeValueAsString(customerResponse), v(FrameworkConstants.RESPONSE_PAYLOAD, RESPONSE_PAYLOAD_FROM_SOR));
          log.info("Response payload to be published to Kafka :: {}", customerData.toString(), v(FrameworkConstants.RESPONSE_PAYLOAD, RESPONSE_PAYLOAD_TO_KAFKA));
          id_number = crmCustomerProfile.get().getCustomerIdentity().getIdNumber();
          customerEvent customerevent =customerEvent.newBuilder().setEventType("createCustomer").setCustomerData(customerData).build();
          kafkaTemplate.send(kafkaTopic,id_number, customerevent);
          return customerResponse;
        }
        else{
          log.error("data not found in crm and Fda");
          exceptionBuilder.throwDataException(FrameworkConstants.LOOKUP_ERROR, DATA_NOT_FOUND);

        }
      }
    } catch (DataException ex) {
      log.error(ex.getLocalizedMessage());
      throw ex;
    } catch (TransactionException ex) {
      log.error(ex.getLocalizedMessage());
      exceptionBuilder.throwTechnicalException(
              FrameworkConstants.DATABASE_ERROR, FDA_CONNECTION_FAILURE);
    } catch (Exception ex) {
      log.error(ex.getLocalizedMessage());
      exceptionBuilder.throwTechnicalException(
              FrameworkConstants.RUNTIME_ERROR, FrameworkMessageKeys.RUNTIME_ERROR);
    }

    return null;
  }

}
