package io.cax.forex.ingester;

import io.cax.forex.ingester.domain.Tick;
import io.cax.forex.ingester.repositories.TickRepository;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by cq on 22/4/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ForexIngesterApp.class)
@ActiveProfiles("test")
public class TickRepositoryTests {

    @Autowired
    TickRepository repository;

    @Before
    public void tearDown(){
        repository.deleteAll();
    }

    @Test
    public void testSaveTick() throws Exception{

        saveTick();
        assertThat(repository.count(),equalTo(1L));

    }

    @Test
    public void testRetrieveTick() throws Exception{

        Tick returnedTick = saveTick();
        repository.findAll().forEach(tick -> assertThat(tick.getDateTime(),equalTo(returnedTick.getDateTime())));


    }

    private Tick saveTick() {
        Tick tick = new Tick();
        tick.setAsk(2.222);
        tick.setBid(2.333);
        tick.setDateTime(DateTime.now());
        tick.setInstrument("AUD/USD");

        return repository.save(tick);
    }


}
