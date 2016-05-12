package io.cax.forex.ingester.services;

import io.cax.forex.ingester.repositories.InstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Created by cq on 23/4/16.
 */
@Service
public class InstrumentsService {


    private InstrumentRepository repository;

    @Autowired
    public void setRepository(InstrumentRepository repository) {
        this.repository = repository;
    }

    public String instruments(){

        return repository.getAll().stream().map(i-> i.getInstrumentName()).collect(Collectors.joining(","));
    }


}
