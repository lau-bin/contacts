package com.freelance.lautarocutri.compass_challenge;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.shell.Input;
import org.springframework.shell.Shell;

@SpringBootApplication
public class CompassChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompassChallengeApplication.class, args);
	}

	@Bean
	@Profile("test")
	public CommandLineRunner commandLineRunner(Shell shell) {
		return args -> {
			shell.run(() -> (Input) () -> "find-duplicates --archivo src/test/resources/contact_data.csv");
		};
	}
}
