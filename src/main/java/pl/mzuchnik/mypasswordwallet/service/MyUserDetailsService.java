package pl.mzuchnik.mypasswordwallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.mzuchnik.mypasswordwallet.entity.User;

import java.util.Collections;

@Service
@Primary
public class MyUserDetailsService implements UserDetailsService {

    private UserService userService;

    @Autowired
    public MyUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        User userByLogin = userService.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find user with login: " + login));

        org.springframework.security.core.userdetails.User user =
                new org.springframework.security.core.userdetails.User(
                        userByLogin.getLogin(),
                        userByLogin.getPassword(),
                        Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));

        return user;
    }
}
