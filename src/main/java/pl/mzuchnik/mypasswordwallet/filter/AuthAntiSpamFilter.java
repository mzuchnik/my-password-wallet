package pl.mzuchnik.mypasswordwallet.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.filter.RelativeRedirectFilter;
import pl.mzuchnik.mypasswordwallet.entity.AuthenticationAntiSpam;
import pl.mzuchnik.mypasswordwallet.service.AuthenticationAntiSpamHelper;
import pl.mzuchnik.mypasswordwallet.service.AuthenticationAntiSpamService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Component
public class AuthAntiSpamFilter extends OncePerRequestFilter {

    private AuthenticationAntiSpamService antiSpamService;

    @Autowired
    public AuthAntiSpamFilter(AuthenticationAntiSpamService antiSpamService) {
        this.antiSpamService = antiSpamService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = httpServletRequest.getRequestURI();
        String method = httpServletRequest.getMethod();

        if (!(requestURI.equals("/login") && method.equals("POST"))) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        String ipAddress = httpServletRequest.getRemoteAddr();
        Optional<AuthenticationAntiSpam> byIpAddress = antiSpamService.findByIpAddress(ipAddress);
        AuthenticationAntiSpam authenticationAntiSpam;

        if (byIpAddress.isEmpty()) {
            antiSpamService.save(new AuthenticationAntiSpam(ipAddress));
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        } else {
            authenticationAntiSpam = byIpAddress.get();
        }

        AuthenticationAntiSpamHelper helper = new AuthenticationAntiSpamHelper(authenticationAntiSpam);

        if (helper.isPermBanned()) {
            httpServletResponse.getWriter().println("Your account is perm banned");
            return;
        }

        if (helper.isTimeLocked()) {
            if (authenticationAntiSpam.getBlockTime() == null ||
                    Timestamp.from(Instant.now()).after(authenticationAntiSpam.getBlockTime())) {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }
            long lockTime = helper.getTimeOfLockInSeconds();
            httpServletRequest.getRequestDispatcher("/login-lock?lockTime=" + lockTime).forward(httpServletRequest, httpServletResponse);
            return;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
