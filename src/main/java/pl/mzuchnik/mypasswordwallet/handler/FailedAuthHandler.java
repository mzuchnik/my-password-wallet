package pl.mzuchnik.mypasswordwallet.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import pl.mzuchnik.mypasswordwallet.entity.AuthenticationAntiSpam;
import pl.mzuchnik.mypasswordwallet.entity.User;
import pl.mzuchnik.mypasswordwallet.entity.UserLog;
import pl.mzuchnik.mypasswordwallet.service.AuthenticationAntiSpamHelper;
import pl.mzuchnik.mypasswordwallet.service.AuthenticationAntiSpamService;
import pl.mzuchnik.mypasswordwallet.service.UserLogService;
import pl.mzuchnik.mypasswordwallet.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Component
public class FailedAuthHandler implements AuthenticationFailureHandler {

    private AuthenticationAntiSpamService antiSpamService;
    private UserService userService;
    private UserLogService userLogService;

    @Autowired
    public FailedAuthHandler(AuthenticationAntiSpamService antiSpamService,
                             UserService userService,
                             UserLogService userLogService) {
        this.antiSpamService = antiSpamService;
        this.userService = userService;
        this.userLogService = userLogService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException exception) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());

        AuthenticationAntiSpam authenticationAntiSpam =
                antiSpamService.findByIpAddress(httpServletRequest.getRemoteAddr()).get();

        AuthenticationAntiSpamHelper helper =
                new AuthenticationAntiSpamHelper(authenticationAntiSpam);

        helper.incrementNumberOfFailAuth();
        helper.updateLastTimeFailAuth();

        int numberOfFailAuth = helper.getNumberOfFailAuth();

        switch (numberOfFailAuth){
            case 3: helper.updateTimeOfLockBySeconds(5); break;
            case 4: helper.updateTimeOfLockBySeconds(10); break;
            case 5: helper.updateTimeOfLockBySeconds(30); break;
            case 6: authenticationAntiSpam.setPermBanned(true); break;
        }
        antiSpamService.save(authenticationAntiSpam);

        /*USER's LOGS*/

        String username = httpServletRequest.getParameter("username");
        Optional<User> userByLogin = userService.findByLogin(username);
        Timestamp timestamp = Timestamp.from(Instant.now());

        if (userByLogin.isPresent()) {
            User user = userByLogin.get();
            UserLog userLog = new UserLog();
            userLog.setResult(exception.getMessage());
            userLog.setTimestamp(timestamp);
            userLog.setIpAddress(httpServletRequest.getRemoteAddr());
            userLog.setUser(user);
            userLogService.save(userLog);
        }
        httpServletResponse.sendRedirect("/login?error");
    }
}
