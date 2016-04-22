package io.cax.forex.ingester;

import io.cax.forex.ingester.domain.InstrumentsLoader;
import io.cax.forex.ingester.repositories.InstrumentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
@ActiveProfiles("test")
public class InstrumentsLoaderTests {


    @Autowired
    InstrumentRepository repository;

	@Test
    public void testDataLoading() {
        InstrumentsLoader loader = new InstrumentsLoader();
        loader.setRepository(repository);
        loader.init();
        assertThat(repository.count(),is(122L));
    }



}
