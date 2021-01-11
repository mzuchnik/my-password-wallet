package pl.mzuchnik.mypasswordwallet.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.mzuchnik.mypasswordwallet.filter.AuthAntiSpamFilter;
import pl.mzuchnik.mypasswordwallet.encoder.HmacPasswordEncoder;
import pl.mzuchnik.mypasswordwallet.handler.FailedAuthHandler;
import pl.mzuchnik.mypasswordwallet.handler.LogoutAuthHandler;
import pl.mzuchnik.mypasswordwallet.handler.SuccessAuthHandler;
import pl.mzuchnik.mypasswordwallet.service.AuthenticationAntiSpamService;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;
    private AuthAntiSpamFilter authAntiSpamFilter;
    private FailedAuthHandler failedAuthHandler;
    private SuccessAuthHandler successAuthHandler;
    private LogoutAuthHandler logoutAuthHandler;

    @Autowired
    public WebSecurity(UserDetailsService userDetailsService,
                       AuthAntiSpamFilter authAntiSpamFilter,
                       FailedAuthHandler failedAuthHandler,
                       SuccessAuthHandler successAuthHandler,
                       LogoutAuthHandler logoutAuthHandler) {
        this.userDetailsService = userDetailsService;
        this.authAntiSpamFilter = authAntiSpamFilter;
        this.failedAuthHandler = failedAuthHandler;
        this.successAuthHandler = successAuthHandler;
        this.logoutAuthHandler = logoutAuthHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/wallet").authenticated()
                .antMatchers("/**").permitAll()
                //.antMatchers("/sign-up", "/main").permitAll()
                .and()
                .addFilterBefore(authAntiSpamFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage("/login")
                .failureHandler(failedAuthHandler)
                .successHandler(successAuthHandler)
                .permitAll()
                .and()
                .logout()
                .logoutSuccessHandler(logoutAuthHandler)
                .and()
                .httpBasic();
    }

    @Bean
    public PasswordEncoder encoder() {
        String id = "bcrypt";
        Map<String, PasswordEncoder> encoderMap = new HashMap<>();
        encoderMap.put(id, new BCryptPasswordEncoder());
        encoderMap.put("hmac", new HmacPasswordEncoder());
        return new DelegatingPasswordEncoder(id, encoderMap);
    }

    @Bean
    public OncePerRequestFilter myAuthAntiSpamFilter(AuthenticationAntiSpamService antiSpamService){
        return new AuthAntiSpamFilter(antiSpamService);
    }
}
