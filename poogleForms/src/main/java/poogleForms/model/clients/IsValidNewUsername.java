package poogleForms.model.clients;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy=NewUsernameValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsValidNewUsername {
	String message() default "Username Already Exists";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default{};
}
