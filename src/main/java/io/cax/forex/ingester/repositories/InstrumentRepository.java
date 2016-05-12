package io.cax.forex.ingester.repositories;

import io.cax.forex.ingester.domain.Instrument;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by cq on 22/4/16.
 */
@Repository
public interface InstrumentRepository extends CrudRepository<Instrument,String>{

    @Query("select i from Instrument i")
    public List<Instrument> getAll();
}
