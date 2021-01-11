package pl.mzuchnik.mypasswordwallet.form.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserExistsValidator.class)
public @interface UserExists {

    String message() default "Given user's login is not correct";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
