package io.cax.forex.ingester;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ForexIngesterApp {

	public static void main(String[] args) {
		SpringApplication.run(ForexIngesterApp.class, args);
	}

}
