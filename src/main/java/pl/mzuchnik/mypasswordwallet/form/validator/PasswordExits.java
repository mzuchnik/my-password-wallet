package pl.mzuchnik.mypasswordwallet.form.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(value = {ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordExistsValidator.class)
public @interface PasswordExits {

    String message() default "Given password id is not correct";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
