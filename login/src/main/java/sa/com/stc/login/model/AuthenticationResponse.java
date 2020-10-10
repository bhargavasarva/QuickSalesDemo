package sa.com.stc.login.model;

import java.io.Serializable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class AuthenticationResponse implements Serializable {

    private final String jwt;
    private final String username;
    private final String role;

}
