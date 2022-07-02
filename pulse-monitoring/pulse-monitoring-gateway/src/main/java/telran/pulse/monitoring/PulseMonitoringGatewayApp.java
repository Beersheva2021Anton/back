package telran.pulse.monitoring;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import telran.pulse.monitoring.dto.*;
import telran.pulse.monitoring.security.*;

@SpringBootApplication
@RestController
public class PulseMonitoringGatewayApp {

	public static void main(String[] args) {
		SpringApplication.run(PulseMonitoringGatewayApp.class, args);
	}
	
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	AccountingManagement accManagement;

	@PostMapping("/login")
	ResponseEntity<?> login(@RequestBody LoginData loginData) {
		var account = accManagement.getAccount(loginData.email);
		if (account != null && passwordEncoder.matches(loginData.password, account.getHashPassword())) {
			return ResponseEntity.ok(getToken(loginData, account));
		}
		return ResponseEntity.badRequest().body("Wrong Credentials");
	}

	private LoginResponse getToken(LoginData loginData, Account account) {
		byte[] code = String.format("%s:%s", loginData.email, loginData.password).getBytes();
		return new LoginResponse("Basic " + Base64.getEncoder().encodeToString(code), account.getRole());
	}
}
