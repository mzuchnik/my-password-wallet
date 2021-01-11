package pl.mzuchnik.mypasswordwallet.form.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.mzuchnik.mypasswordwallet.entity.User;
import pl.mzuchnik.mypasswordwallet.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.security.Principal;

@Component
public class PasswordCorrectForAuthenticatedUserValidator implements ConstraintValidator<PasswordCorrectForAuthenticatedUser, String> {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordCorrectForAuthenticatedUserValidator(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public void initialize(PasswordCorrectForAuthenticatedUser constraint) {
    }

    public boolean isValid(String password, ConstraintValidatorContext context) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByLogin(authentication.getName()).orElseThrow(
                () -> new UsernameNotFoundException("Cannot find user by login: " + authentication.getName()));

        return passwordEncoder.matches(password, user.getPassword());
    }
}
