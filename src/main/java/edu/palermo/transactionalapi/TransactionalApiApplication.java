package edu.palermo.transactionalapi;

import edu.palermo.transactionalapi.models.User;
import edu.palermo.transactionalapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TransactionalApiApplication {
	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(TransactionalApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(UserRepository repository) {
		return (args) -> {
			// save a few users
			//repository.save(new User("Juan", "Perez", "36274837", "juanperez@gmail.com", "e10adc3949ba59abbe56e057f20f883e"));
		};
	}

}
