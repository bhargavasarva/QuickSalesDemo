package sa.com.stc.customerverification.web.infra.persistence.fda;


import org.springframework.data.jpa.repository.JpaRepository;
import sa.com.stc.customerverification.web.model.fda.CustomerIdentity;
import sa.com.stc.customerverification.web.model.fda.CustomerProfile;

public interface CustomerDetailsRepository extends JpaRepository<CustomerProfile, CustomerIdentity> {

}
