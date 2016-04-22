package io.cax.forex.ingester;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by cq on 22/4/16.
 */
@Configuration
@EnableJpaRepositories(basePackages = "io.cax.forex.ingester.repositories")
@EnableAutoConfiguration(exclude=ForexIngesterApp.class)
public class TestConfig {

}
