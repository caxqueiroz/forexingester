package io.cax.forex.ingester;

import io.cax.forex.ingester.services.TickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by cq on 22/4/16.
 */
@RestController
public class MainController {

    private TickService service;

    @Autowired
    public void setService(TickService service) {
        this.service = service;
    }

    @RequestMapping(path = "/start")
    public ResponseEntity startStream() throws IOException {

        service.startStreaming();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/stop")
    public ResponseEntity stopStream() throws IOException{
        service.stopStreaming();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/ticks")
    public ResponseEntity ticksProcessed() throws IOException{

        long ticksProcessed = service.ticksProcessed();
        return new ResponseEntity(ticksProcessed,HttpStatus.OK);
    }



}
