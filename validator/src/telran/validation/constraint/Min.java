package telran.validation.constraint;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
/**
 * 
 * Validation constraint defining minimal value of number field
 *
 */
public @interface Min {
	int value();
	String message() default "min constraint violation";
}
