package sa.com.stc.customerverification.web.filters;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sa.com.stc.customerverification.web.model.AppUserDetails;
import sa.com.stc.customerverification.web.service.CustomerVerificationFeignClient;
import sa.com.stc.framework.exception.util.ExceptionBuilder;
import sa.com.stc.framework.util.FrameworkConstants;
import sa.com.stc.framework.util.FrameworkMessageKeys;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

  private final CustomerVerificationFeignClient feignClient;
  private final ExceptionBuilder exceptionBuilder;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    String jwt = null;
    AppUserDetails userDetails = null;

    final String authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      jwt = authorizationHeader.substring(7);
    }

    try {
      userDetails = feignClient.validateJWT(jwt);
    } catch (Exception ex) {
      exceptionBuilder.throwDataException(
          FrameworkConstants.AUTHENTICATION_FAILURE, FrameworkMessageKeys.AUTHENTICATION_FAILURE);
    }

    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    usernamePasswordAuthenticationToken.setDetails(
        new WebAuthenticationDetailsSource().buildDetails(request));

    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

    chain.doFilter(request, response);
  }
}
