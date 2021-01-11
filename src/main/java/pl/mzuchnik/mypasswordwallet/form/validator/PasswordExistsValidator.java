package pl.mzuchnik.mypasswordwallet.form.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mzuchnik.mypasswordwallet.service.PasswordService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class PasswordExistsValidator implements ConstraintValidator<PasswordExits, Long> {

    private PasswordService passwordService;

    @Autowired
    public PasswordExistsValidator(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    public void initialize(PasswordExits constraint) {

    }

    public boolean isValid(Long id, ConstraintValidatorContext context) {
        return passwordService.passwordIdExits(id);
    }
}
