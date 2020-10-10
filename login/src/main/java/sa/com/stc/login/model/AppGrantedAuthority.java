package sa.com.stc.login.model;

import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@NoArgsConstructor
public class AppGrantedAuthority implements GrantedAuthority {

  private String role;

  public AppGrantedAuthority(String role) {
    this.role = role;
  }

  @Override
  public String getAuthority() {
    return this.role;
  }

  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    } else {
      return obj instanceof AppGrantedAuthority && this.role
          .equals(((AppGrantedAuthority) obj).role);
    }
  }

  public int hashCode() {
    return this.role.hashCode();
  }

  public String toString() {
    return this.role;
  }
}
