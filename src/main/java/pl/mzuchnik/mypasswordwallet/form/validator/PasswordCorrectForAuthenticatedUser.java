package pl.mzuchnik.mypasswordwallet.form.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordCorrectForAuthenticatedUserValidator.class)
public @interface PasswordCorrectForAuthenticatedUser {

    String message() default "Given username's password is not correct";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
