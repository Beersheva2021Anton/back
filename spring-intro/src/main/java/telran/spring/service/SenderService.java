package telran.spring.service;

public interface SenderService {
	
	String getType();
	void send(String message);
}
