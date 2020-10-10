package sa.com.stc.customerverification.web.infra.persistence.crm;


import org.springframework.data.jpa.repository.JpaRepository;
import sa.com.stc.customerverification.web.model.crm.CrmCustomerIdentity;
import sa.com.stc.customerverification.web.model.crm.CrmCustomerProfile;

public interface CrmCustomerDetailsRepository extends JpaRepository<CrmCustomerProfile, CrmCustomerIdentity> {

}
