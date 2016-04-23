package io.cax.forex.ingester;

import io.cax.forex.ingester.domain.Tick;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by cq on 23/4/16.
 */
public class UtilsTests {

    @Test
    public void testConvertToTick() throws Exception{
        //{"tick":{"instrument":"XCU_USD","time":"14613963669924145","bid":2.26043,"ask":2.26143}}
        String content = "{\"tick\":{\"instrument\":\"XCU_USD\",\"time\":\"14613963669924145\",\"bid\":2.26043,\"ask\":2.26143}}";
        Tick tick = Utils.convertToTick(content);
        assertThat(tick.getInstrument(),equalTo("XCU_USD"));

    }

    @Test
    public void testConvertToTick2() throws Exception{
        //{"tick":{"instrument":"EUR_USD","time":"1461396366924145","bid":1.13929,"ask":1.14029}}
        String content = "{\"tick\":{\"instrument\":\"EUR_USD\",\"time\":\"1461396366924145\",\"bid\":1.13929,\"ask\":1.14029}}";
        Tick tick = Utils.convertToTick(content);
        assertThat(tick.getInstrument(),equalTo("EUR_USD"));
        assertThat(tick.getTime(),equalTo(1461396366924145L));

    }

}
