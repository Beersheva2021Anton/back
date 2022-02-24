package telran.spring.service;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {
		
	private Map<String, SenderService> services;
	private final List<SenderService> serviceList;
	
	@Autowired
	public Sender(List<SenderService> serviceList) {
		this.serviceList = serviceList;
	}

	public Map<String, SenderService> getServices() {
		return services;
	}
	
	@PostConstruct
	void displayMap() {
		services = serviceList.stream().collect(Collectors.toMap(s -> s.getType(), s -> s));
	}
	
}
