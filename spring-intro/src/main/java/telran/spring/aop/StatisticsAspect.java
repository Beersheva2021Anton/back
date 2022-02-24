package telran.spring.aop;

import java.util.*;
import javax.annotation.PreDestroy;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import telran.spring.service.SenderService;

@Component
@Aspect
public class StatisticsAspect {
	
	private Map<String, Integer> usageStatistics = new HashMap<>();
	
	@Before("execution(public * telran.spring.service..send(..))")
	void watcher(JoinPoint jp) throws Throwable {
		String serviceName = ((SenderService)jp.getTarget()).getType();
		if (!usageStatistics.containsKey(serviceName)) {
			usageStatistics.put(serviceName, 1);
		}
		else {
			usageStatistics.computeIfPresent(serviceName, (k,v) -> ++v);
		}
	}
	
	@PreDestroy
	void showUsageStatistics() {
		usageStatistics.entrySet().forEach(System.out::println);
	}
}