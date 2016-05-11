package io.cax.forex.ingester;

import io.cax.forex.ingester.services.TickService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by cq on 23/4/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
@ActiveProfiles("test")
public class TickServiceTests {

    @Autowired
    TickService service;

    @Test
    public void testStartStreaming() throws Exception {

        service.startStreaming();
        Thread.sleep(5000);
        assertThat(service.isRunning(),equalTo(true));
        service.stopStreaming();
        Thread.sleep(5000);
    }

    @Test
    public void testStopStreaming() throws Exception {

        service.startStreaming();
        Thread.sleep(5000);
        service.stopStreaming();
        assertThat(service.isRunning(),equalTo(false));

    }
}
