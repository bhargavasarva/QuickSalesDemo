/*
 * Copyright @2020 stc. All Rights reserved.
 * https://www.stc.com.sa
 */

package sa.com.stc.framework.logging;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

/**
 * Custom interceptor class to capture the key fields such as trace id , host and target system ips,
 * etc. from the incoming request
 */
@Component
public class LoggingInterceptor implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {
    try {
      HttpServletRequest req = (HttpServletRequest) request;
      String correlationId = req.getHeader("Trace-id");

      if (StringUtils.isEmpty(correlationId)) {
        correlationId = UUID.randomUUID().toString();
      }

      //MDC.put("traceId", correlationId);
      //MDC.put("hostIp", !StringUtils.isEmpty(req.getLocalAddr()) ? req.getLocalAddr() : "");
      //MDC.put("clientIp", !StringUtils.isEmpty(req.getRemoteAddr()) ? req.getRemoteAddr() : "");

      filterChain.doFilter(request, response);
    } finally {
      MDC.clear();
    }
  }
}
