package io.cax.forex.ingester;

import io.cax.forex.ingester.services.TickService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ForexIngesterApp implements CommandLineRunner{

    Logger logger = LoggerFactory.getLogger(ForexIngesterApp.class);

    @Autowired
    private TickService service;

	public static void main(String[] args) {
		SpringApplication.run(ForexIngesterApp.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		service.startStreaming();
        logger.info("Tick Ingestion Service started!!");
	}
}
