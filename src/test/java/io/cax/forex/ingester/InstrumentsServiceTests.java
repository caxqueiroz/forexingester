package io.cax.forex.ingester;

import io.cax.forex.ingester.domain.InstrumentsLoader;
import io.cax.forex.ingester.repositories.InstrumentRepository;
import io.cax.forex.ingester.services.InstrumentsService;
import org.junit.After;
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
 * Created by cq on 23/4/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
@ActiveProfiles("test")
public class InstrumentsServiceTests {


    @Autowired
    InstrumentRepository repository;

    @Before
    public void prepareData(){
        InstrumentsLoader loader = new InstrumentsLoader();
        loader.setRepository(repository);
        loader.init();
    }

    @After
    public void cleanData(){
        repository.deleteAll();
    }

    @Test
    public void testInstrumentsToTrack() throws Exception{
        InstrumentsService service = new InstrumentsService();
        service.setRepository(repository);
        String instruments = service.instruments();
        assertThat(instruments.length(),equalTo(1028));

    }
}
