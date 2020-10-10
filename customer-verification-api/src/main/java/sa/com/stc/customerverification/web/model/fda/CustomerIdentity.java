package sa.com.stc.customerverification.web.model.fda;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerIdentity implements Serializable {
    @Column(name = "ID_NUMBER")
    private String idNumber;
    @Column(name = "ID_TYPE")
    private String idType;
}
