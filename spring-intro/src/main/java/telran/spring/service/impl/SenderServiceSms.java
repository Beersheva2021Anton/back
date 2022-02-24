package telran.spring.service.impl;

import org.slf4j.*;
import org.springframework.stereotype.Service;

import telran.spring.service.SenderService;

@Service
public class SenderServiceSms implements SenderService {

	private static Logger LOG = LoggerFactory.getLogger(SenderServiceSms.class);
	@Override
	public void send(String message) {
		
		System.out.println("Sms message: " + message);
	}

	@Override
	public String getType() {
		
		LOG.debug("Get sms type");
		return "sms";
	}

}
