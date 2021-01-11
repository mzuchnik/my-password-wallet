package pl.mzuchnik.mypasswordwallet.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import pl.mzuchnik.mypasswordwallet.entity.User;
import pl.mzuchnik.mypasswordwallet.entity.UserLog;
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
public class LogoutAuthHandler implements LogoutSuccessHandler {

    private UserService userService;
    private UserLogService userLogService;

    public LogoutAuthHandler(UserService userService, UserLogService userLogService) {
        this.userService = userService;
        this.userLogService = userLogService;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        org.springframework.security.core.userdetails.User userDetails =
                (org.springframework.security.core.userdetails.User)authentication.getPrincipal();
        Optional<User> byLogin = userService.findByLogin(userDetails.getUsername());


        // ---------- Add log to database for user --------------
        if (byLogin.isPresent()) {
            User user = byLogin.get();
            UserLog userLog = new UserLog();
            userLog.setResult("Logged out");
            userLog.setTimestamp(Timestamp.from(Instant.now()));
            userLog.setIpAddress(request.getRemoteAddr());
            userLog.setUser(user);
            userLogService.save(userLog);
            System.out.println("User log: " + userLog);
        }
        response.sendRedirect("/login?logout");
    }
}
