package telran.spring.aop;

import java.util.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ValidationAspect {
	
	@Value("${aop.telran.stopwords}")
	private String[] stopWords;
	
	@Around("execution(public * telran.spring.service..send(..))")
	Object validator(ProceedingJoinPoint jp) throws Throwable {
		
		var deniedWords = new HashSet<>(Arrays.asList(stopWords));
		
		var args = jp.getArgs();
		var message = args[0];
		var words = message.toString().split(" ");
		var messageModified = String.join(" ", Arrays.stream(words)
				.map(w -> deniedWords.contains(w) 
						? w.charAt(0) + ".".repeat(w.length() - 1) 
						: w)
				.toList());
		args[0] = messageModified;
		return jp.proceed(args);
	}
}