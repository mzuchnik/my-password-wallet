package pl.mzuchnik.mypasswordwallet.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import pl.mzuchnik.mypasswordwallet.entity.AuthenticationAntiSpam;
import pl.mzuchnik.mypasswordwallet.filter.AuthAntiSpamFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

class AuthenticationAntiSpamHelperTest {


    @ParameterizedTest
    @CsvSource(value = {"1"})
    void should_not_pass_user_with_five_login_attempts(String attempts) throws Exception {
        String ipAddress = "127.192.168.1";
        AuthenticationAntiSpam authenticationAntiSpam = new AuthenticationAntiSpam(ipAddress);
        authenticationAntiSpam.setCountOfFails(Integer.parseInt(attempts));
        authenticationAntiSpam.setTimeLocked(true);

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        FilterChain chain = Mockito.mock(FilterChain.class);
        AuthenticationAntiSpamService antiSpamService = Mockito.mock(AuthenticationAntiSpamService.class);
        AuthAntiSpamFilter authFilter = Mockito.mock(AuthAntiSpamFilter.class);

        Mockito.when(request.getRemoteAddr()).thenReturn(ipAddress);

        Mockito.when(antiSpamService.findByIpAddress(ipAddress)).thenReturn(Optional.of(authenticationAntiSpam));

        authFilter.doFilter(request, response, chain);

    }

}