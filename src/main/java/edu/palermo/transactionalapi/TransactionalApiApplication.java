package edu.palermo.transactionalapi;

import edu.palermo.transactionalapi.models.Commerce;
import edu.palermo.transactionalapi.models.CreditCard;
import edu.palermo.transactionalapi.models.User;
import edu.palermo.transactionalapi.repositories.CommerceRepository;
import edu.palermo.transactionalapi.repositories.CreditCardRepository;
import edu.palermo.transactionalapi.repositories.UserRepository;
import edu.palermo.transactionalapi.services.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SpringBootApplication
public class TransactionalApiApplication {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CreditCardRepository creditCardRepository;
	@Autowired
	private CommerceRepository commerceRepository;

	public static void main(String[] args) {
		SpringApplication.run(TransactionalApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo() {
		return (args) -> {
			// save a few users
			//userRepository.save(new User("Lucas", "Gomez", "20316161", "lucasgomez@gmail.com", "e10adc3949ba59abbe56e057f20f883e"));
			//creditCardRepository.save(new CreditCard("312","11/25","Lucas Gomez", "4200145601473690", "visa"));
			//commerceRepository.save(new Commerce("20335554441", "Almacen don Tito"));
		};
	}

	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
					.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests()
					.antMatchers(HttpMethod.POST, "/login","/user/create", "/creditcard/create", "/commerce/create", "/account/create").permitAll()
					.anyRequest().authenticated();
		}
	}

}
