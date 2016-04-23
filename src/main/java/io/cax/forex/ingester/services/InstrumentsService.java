package io.cax.forex.ingester.services;

import io.cax.forex.ingester.repositories.InstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        final StringBuilder sb = new StringBuilder();
        repository.findAll().forEach(i -> sb.append(i.getInstrumentName()).append(","));
        sb.deleteCharAt(sb.lastIndexOf(","));
        return sb.toString();
    }


}
