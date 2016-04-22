package io.cax.forex.ingester;

import io.cax.forex.ingester.services.TickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by cq on 22/4/16.
 */
@RestController
public class MainController {

    @Autowired
    private TickService service;


    @RequestMapping(path = "/start")
    public void startStream() throws IOException {

        service.startStreaming();
    }

    @RequestMapping(path = "/stop")
    public void stopStream() throws IOException{

        service.stopStreaming();
    }



}
