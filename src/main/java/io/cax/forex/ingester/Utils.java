package io.cax.forex.ingester;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cax.forex.ingester.domain.Tick;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by cq on 23/4/16.
 */
public class Utils {

    private final static Logger logger = LoggerFactory.getLogger(Utils.class);

    private final static ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);

    /**
     *
     * @param content
     * @return
     * @throws Exception
     */
    public static Tick convertToTick(String content) throws Exception{

        try {
            if(content.contains("tick")){
                Tick tick = mapper.readValue(content,Tick.class);
                logger.debug(mapper.writer(new DefaultPrettyPrinter()).writeValueAsString(tick));
                return tick;

            }else{

                logger.debug(content);
                return null;
            }


        } catch (IOException e) {
            throw e;
        }
    }

    /**
     *
     * @param ask
     * @param bid
     * @return
     */
    public static double mean(double ask, double bid) {
        return Math.abs((ask + bid)/2);
    }


    /**
     *
     * @param dateTime
     * @return
     */
    public static String printFormatDate(long dateTime){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.").format(dateTime/1000) + String.format("%06d", dateTime%1000000);
    }
}
