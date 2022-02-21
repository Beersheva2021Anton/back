package telran.validation.constraint;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
/**
 * 
 * LocalDate field less than the current date
 *
 */
public @interface Past {
	String message() default "field may be a past date";
}
