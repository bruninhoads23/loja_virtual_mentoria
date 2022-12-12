package br.com.lojavirtual.security;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
		
		.httpBasic()
		.and()
		.authorizeHttpRequests()
		.antMatchers(HttpMethod.GET,"/salvarAcesso/**").permitAll()
		.antMatchers(HttpMethod.POST,"/deleteAcesso/**").permitAll()
		.antMatchers(HttpMethod.DELETE,"/deleteAcesso/**").permitAll()
		.anyRequest().authenticated()
		.and()
		.csrf().disable();
		return http.build();	
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();	
	}
}

