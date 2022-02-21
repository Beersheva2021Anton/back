package telran.validation.constraint;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
/**
 * 
 * Validation constraint defining maximal value of number field
 *
 */
public @interface Max {
	int value();
	String message() default "max constraint violation";
}
