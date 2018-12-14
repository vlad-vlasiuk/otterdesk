package com.galvanize.otterdesk.controller;

import com.galvanize.otterdesk.entity.Event;
import com.galvanize.otterdesk.service.AmqpSenderService;
import com.galvanize.otterdesk.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    @Value("${amqp.exchange.name}")
    String exchangeName;
    @Value("${amqp.message_bus.binding.key}")
    String routingKey;

    private final EventService service;

    @Autowired
    public PdfController(EventService service) {
        this.service = service;
    }


// use rest call with body string "https://s3.us-east-2.amazonaws.com/someco.com/uploads/pdfs/74efe087-7949-46db-8a8d-ee06776eb2b0.pdf"
    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTask(@RequestBody String path) {
        service.createProcess(path);
    }

}
