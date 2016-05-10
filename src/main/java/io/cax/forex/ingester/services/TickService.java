package io.cax.forex.ingester.services;

import io.cax.forex.ingester.Utils;
import io.cax.forex.ingester.domain.Tick;
import io.cax.forex.ingester.repositories.TickRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by cq on 17/4/16.
 */
@Service
@EnableAsync
public class TickService {

    Logger logger = LoggerFactory.getLogger(TickService.class);

    @Value("${oanda.access_token}")
    private String accessToken;

    @Value("${oanda.account_id}")
    private String accountId;


    @Value("${oanda.domain}")
    private String domain;


    private RestOperations restTemplate;

    private TickRepository repository;

    @Autowired
    public void setRepository(TickRepository repository) {
        this.repository = repository;
    }

    private InstrumentsService instrumentsService;

    @Autowired
    public void setInstrumentsService(InstrumentsService instrumentsService) {
        this.instrumentsService = instrumentsService;
    }

    private AtomicBoolean running = new AtomicBoolean();


    /**
     * Starts the data streaming.
     */
    @Async
    public void startStreaming(){

        if(running.get()==true) return; //already running.

        running.compareAndSet(false,true);

        restTemplate = new RestTemplate();

        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString(domain)
                .path("/v1/prices")
                .queryParam("accountId",accountId)
                .queryParam("instruments",instrumentsService.instruments()).build();

        restTemplate.execute(uriComponents.toUriString(),
                HttpMethod.GET,
                clientHttpRequest -> setHeaders(clientHttpRequest),
                clientHttpResponse -> {
                    try(Scanner scanner = new Scanner(clientHttpResponse.getBody(),"utf-8")){

                        while(scanner.hasNext() && running.get()) saveTick(scanner.nextLine());
                    }
                    return new ResponseEntity<>(HttpStatus.OK);
                });
    }

    /**
     * Stops the data streaming
     */
    public void stopStreaming(){

        running.compareAndSet(true, false);
    }


    /**
     * Processes the data and saves the tick if pertinent. Hearbeat ticks are not saved.
     * @param content
     */
    private void saveTick(String content){

        try{

            Tick tick = Utils.convertToTick(content);
            if(tick!=null) repository.save(tick);

        } catch(Exception e){
            logger.error("Error saving tick: " + e.getMessage());
        }


    }


    private ClientHttpRequest setHeaders(ClientHttpRequest clientHttpRequest){
        clientHttpRequest.getHeaders().add("X-Accept-Datetime-Format","UNIX");
        clientHttpRequest.getHeaders().add("Authorization","Bearer " + accessToken);
        return clientHttpRequest;
    }

    public long ticksProcessed(){
        logger.debug("ticksProcessed called!!");
        return repository.count();
    }

}
