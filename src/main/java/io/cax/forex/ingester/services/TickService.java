package io.cax.forex.ingester.services;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cax.forex.ingester.domain.Tick;
import io.cax.forex.ingester.repositories.TickRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by cq on 17/4/16.
 */
@Service
public class TickService {

    Logger logger = LoggerFactory.getLogger(TickService.class);

    @Value("${oanda.access_token}")
    private String accessToken;

    @Value("${oanda.account_id}")
    private String accountId;


    @Value("${oanda.domain}")
    private String domain;

    @Value("${oanda.instruments}")
    private String instruments;

    private RestOperations restTemplate;

    @Autowired
    TickRepository repository;

    private boolean running;


    /**
     * Starts the data streaming.
     */
    public void startStreaming(){

        running = true;
        restTemplate = new RestTemplate();


        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString(domain)
                .path("/v1/prices")
                .queryParam("accountId",accountId)
                .queryParam("instruments",instruments).build();

        restTemplate.execute(uriComponents.toUriString(),
                HttpMethod.GET,
                clientHttpRequest -> clientHttpRequest.getHeaders().add("Authorization","Bearer " + accessToken),
                clientHttpResponse -> {
                    try(Scanner scanner = new Scanner(clientHttpResponse.getBody(),"utf-8")){

                        while(scanner.hasNext() && running) saveTick(scanner.nextLine());
                    }
                    return new ResponseEntity<>(HttpStatus.OK);
                });
    }

    /**
     * Stops the data streaming
     */
    public void stopStreaming(){
        running = false;
    }


    /**
     * Processes the data and saves the tick if pertinent. Hearbeat ticks are not saved.
     * @param content
     */
    private void saveTick(String content){

        ObjectMapper mapper = new ObjectMapper();

        try {

            mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
            Tick tick = mapper.readValue(content,Tick.class);
            logger.debug(mapper.writer(new DefaultPrettyPrinter()).writeValueAsString(tick));
            repository.save(tick);

        } catch (IOException e) {
            logger.debug("heartbeat not processed: " + e.getMessage());
        }
    }



}
