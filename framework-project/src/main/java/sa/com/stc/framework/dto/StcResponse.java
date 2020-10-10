/*
 * Copyright @2020 stc. All Rights reserved.
 * https://www.stc.com.sa
 */

package sa.com.stc.framework.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sa.com.stc.framework.exception.dto.ErrorDetail;

/**
 * PLM Response Wrapper class
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"responsePayload", "status", "error"})
public class StcResponse implements Serializable {

  private static final long serialVersionUID = -4760021372785797122L;

  @JsonInclude(Include.NON_NULL)
  private Payload responsePayload;

  @NotBlank
  private String status;

  @JsonInclude(Include.NON_NULL)
  private ErrorDetail error;
}
