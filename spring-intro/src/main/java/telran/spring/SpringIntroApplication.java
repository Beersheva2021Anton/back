package telran.spring;

import java.util.*;
import javax.annotation.*;

import org.slf4j.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import telran.spring.service.*;

@SpringBootApplication
public class SpringIntroApplication {
	
	public static Logger LOG = LoggerFactory.getLogger(SpringIntroApplication.class);

	public static void main(String[] args) {
		
		var context = SpringApplication.run(SpringIntroApplication.class, args);
		LOG.debug("App context was created");
		var scanner = new Scanner(System.in);
		var sender = context.getBean(Sender.class);
		Map<String, SenderService> senderServices = sender.getServices();
		LOG.info("Sender services: {}", senderServices.keySet().toString());
		
		System.out.println("Enter 'exit' to stop app...");
		while (true) {
			
			System.out.println("Enter sender type");
			var line = scanner.nextLine();
			if (line.equals("exit")) {
				break;
			}
			var service = senderServices.get(line);
			if (service == null) {
				LOG.warn("Unknown sender type: {}", line);
				System.out.println("Unknown sender type: " + line);
			}
			else {
				System.out.println("Enter message");
				line = scanner.nextLine();
				service.send(line);
			}
		}
	}

	@PostConstruct
	void init() {
		System.out.println("Hello!");
	}
	
	@PreDestroy
	void close() {
		System.out.println("Bye");
	}
}
