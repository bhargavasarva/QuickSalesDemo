package sa.com.stc.customerverification.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerContactDetails {
    private String name;
    private String dateOfBirth;
    private String idExpiryDate;
    private String primaryContactNumber;
}
