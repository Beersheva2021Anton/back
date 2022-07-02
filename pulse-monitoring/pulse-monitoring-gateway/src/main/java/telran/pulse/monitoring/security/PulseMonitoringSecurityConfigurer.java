package telran.pulse.monitoring.security;

import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@ComponentScan("telran")
public class PulseMonitoringSecurityConfigurer {

	@Bean
	PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.httpBasic();
//		http.cors().and().csrf().disable();
//		http.authorizeHttpRequests().antMatchers("/login").permitAll();
//		http.authorizeHttpRequests().antMatchers(HttpMethod.GET).hasAnyRole("USER", "ADMIN");
//		http.authorizeHttpRequests().anyRequest().hasRole("ADMIN");
//	}
	
	@Bean
	SecurityWebFilterChain getSecurityWebFilterChain(ServerHttpSecurity http) {
		http.httpBasic();
		http.cors().and().csrf().disable();
		http.authorizeExchange().pathMatchers("/login").permitAll();
		http.authorizeExchange().pathMatchers(HttpMethod.GET).hasAnyRole("USER", "ADMIN");
		http.authorizeExchange().anyExchange().hasRole("ADMIN");
		return http.build();
	}
}
