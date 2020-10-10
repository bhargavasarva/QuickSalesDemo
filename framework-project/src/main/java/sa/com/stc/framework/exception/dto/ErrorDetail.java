/*
 * Copyright @2020 stc. All Rights reserved.
 * https://www.stc.com.sa
 */

package sa.com.stc.framework.exception.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sa.com.stc.framework.dto.BilingualMessage;


/**
 * Error response wrapper class
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
    "errorCode",
    "errorMessage"
})
public class ErrorDetail implements Serializable {

  private static final long serialVersionUID = 8996096224349631274L;

  private String errorCode;
  @JsonIgnore
  private String errorMessageCode;
  private BilingualMessage errorMessage;
}
