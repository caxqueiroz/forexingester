package io.cax.forex.ingester.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cax.forex.ingester.repositories.InstrumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cq on 22/4/16.
 */
@Component
public class InstrumentsLoader {

    Logger logger = LoggerFactory.getLogger(InstrumentsLoader.class);


    private InstrumentRepository repository;

    @Autowired
    public void setRepository(InstrumentRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void init(){

        ObjectMapper mapper = new ObjectMapper();
        try{

            Instrument[] arraysOfInstruments = mapper.readValue(
                    InstrumentsLoader.class.getResource("/instruments.json"),Instrument[].class);
            List<Instrument> instruments = Arrays.asList(arraysOfInstruments);
            repository.save(instruments);

        }catch(Exception e){
            logger.error("Error loading instruments",e);
        }

    }

}
