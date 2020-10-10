/*
 * Copyright @2020 stc. All Rights reserved.
 * https://www.stc.com.sa
 */

package sa.com.stc.framework.dto;

import java.io.Serializable;
import lombok.NoArgsConstructor;

/**
 * Class to capture the result from the external systems and attach to the {@link
 * sa.com.stc.framework.dto.StcResponse} with the status and error (if applicable).
 */
@NoArgsConstructor
public abstract class Payload implements Serializable {

  private static final long serialVersionUID = 8189422934876749038L;
}
