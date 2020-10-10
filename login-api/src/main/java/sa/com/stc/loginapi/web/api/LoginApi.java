package sa.com.stc.loginapi.web.api;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sa.com.stc.framework.exception.util.ExceptionBuilder;
import sa.com.stc.framework.util.FrameworkConstants;
import sa.com.stc.login.model.AuthenticationRequest;
import sa.com.stc.login.model.AuthenticationResponse;
import sa.com.stc.login.service.AppUserDetailsService;
import sa.com.stc.login.util.JwtUtil;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LoginApi {

  private static final String BAD_CREDENTIALS = "LO-ERR-10000";

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtTokenUtil;
  private final AppUserDetailsService userDetailsService;
  private final ExceptionBuilder exceptionBuilder;

  @PostMapping(value = "/authenticate")
  public ResponseEntity<?> createAuthenticationToken(
      @RequestBody AuthenticationRequest authenticationRequest) {

    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              authenticationRequest.getUsername(), authenticationRequest.getPassword()));
    } catch (BadCredentialsException e) {
      exceptionBuilder.throwDataException(
          FrameworkConstants.DATA_VALIDATION_ERROR, BAD_CREDENTIALS);
    }

    final UserDetails userDetails =
        userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

    final String jwt = jwtTokenUtil.generateToken(userDetails);
    final List<GrantedAuthority> authorities =
        (List<GrantedAuthority>) userDetails.getAuthorities();
    final List<String> roles =
        authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    return ResponseEntity.ok(
        new AuthenticationResponse(
            jwt,
            authenticationRequest.getUsername(),
            roles.get(0).isEmpty() ? StringUtils.EMPTY : roles.get(0)));
  }

  @GetMapping(value = "/validate")
  public ResponseEntity<?> validateAuthenticationToken(@RequestParam String token) {
    try {
      final String username = jwtTokenUtil.extractUsername(token);
      final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
      jwtTokenUtil.validateToken(token, userDetails);
      return ResponseEntity.ok(userDetails);
    } catch (Exception ex) {
      return ResponseEntity.badRequest().build();
    }
  }
}
