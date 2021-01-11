package pl.mzuchnik.mypasswordwallet.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import pl.mzuchnik.mypasswordwallet.entity.AuthenticationAntiSpam;
import pl.mzuchnik.mypasswordwallet.entity.User;
import pl.mzuchnik.mypasswordwallet.entity.UserLog;
import pl.mzuchnik.mypasswordwallet.service.AuthenticationAntiSpamService;
import pl.mzuchnik.mypasswordwallet.service.UserLogService;
import pl.mzuchnik.mypasswordwallet.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Component
public class SuccessAuthHandler implements AuthenticationSuccessHandler {

    private AuthenticationAntiSpamService antiSpamService;
    private UserService userService;
    private UserLogService userLogService;

    @Autowired
    public SuccessAuthHandler(AuthenticationAntiSpamService antiSpamService, UserService userService, UserLogService userLogService) {
        this.antiSpamService = antiSpamService;
        this.userService = userService;
        this.userLogService = userLogService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // ---------- Clear Login Time Block --------------
        AuthenticationAntiSpam authenticationAntiSpam =
                antiSpamService.findByIpAddress(request.getRemoteAddr()).get();

        authenticationAntiSpam.setCountOfFails(0);
        authenticationAntiSpam.setTimeLocked(false);
        authenticationAntiSpam.setBlockTime(null);
        antiSpamService.save(authenticationAntiSpam);

        org.springframework.security.core.userdetails.User userDetails =
                (org.springframework.security.core.userdetails.User)authentication.getPrincipal();
        Optional<User> byLogin = userService.findByLogin(userDetails.getUsername());


        // ---------- Add log to database for user --------------
        if (byLogin.isPresent()) {
            User user = byLogin.get();
            UserLog userLog = new UserLog();
            userLog.setResult("Logged In");
            userLog.setTimestamp(Timestamp.from(Instant.now()));
            userLog.setIpAddress(request.getRemoteAddr());
            userLog.setUser(user);
            userLogService.save(userLog);
            System.out.println("User log: " + userLog);
        }
        response.sendRedirect("/wallet");
    }

}
