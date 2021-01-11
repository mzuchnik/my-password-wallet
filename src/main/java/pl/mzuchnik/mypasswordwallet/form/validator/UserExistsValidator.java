package pl.mzuchnik.mypasswordwallet.form.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mzuchnik.mypasswordwallet.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UserExistsValidator implements ConstraintValidator<UserExists, String> {

    private UserService userService;

    @Autowired
    public UserExistsValidator(UserService userService) {
        this.userService = userService;
    }

    public void initialize(UserExists constraint) {
    }

    public boolean isValid(String login, ConstraintValidatorContext context) {
        return userService.exitsByLogin(login);
    }
}
