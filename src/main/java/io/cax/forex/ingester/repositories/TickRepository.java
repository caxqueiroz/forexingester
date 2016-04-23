package io.cax.forex.ingester.repositories;

import io.cax.forex.ingester.domain.Tick;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by cq on 15/4/16.
 */
@Repository
public interface TickRepository extends CrudRepository<Tick,Long> {

}
