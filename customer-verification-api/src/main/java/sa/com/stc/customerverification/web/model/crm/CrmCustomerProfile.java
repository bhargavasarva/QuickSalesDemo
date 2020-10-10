package sa.com.stc.customerverification.web.model.crm;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "CUSTOMER_PROFILE", schema = "SIEBEL")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CrmCustomerProfile {
    @EmbeddedId
    private CrmCustomerIdentity customerIdentity;

    @Column(name = "ID_EFFECTIVE_DT")
    private String idEffectiveDt;

    @Column(name = "ID_EXPIRY_DT")
    private String idExpiryDt;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "DATE_OF_BIRTH")
    private String dateOfBirth;
    @Column(name = "GENDER")
    private String gender;
    @Column(name = "CUSTOMER_REFERENCE_NUM")
    private String customerReferenceNumber;
    @Column(name = "CUSTOMER_STATUS")
    private String customerStatus;
    @Column(name = "PRIMARY_CONTACT")
    private String primaryContactNumber;

}