package telran.pulse.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"telran"})
public class DoctorDataProviderApp {

	public static void main(String[] args) {
		SpringApplication.run(DoctorDataProviderApp.class, args);
	}

}
