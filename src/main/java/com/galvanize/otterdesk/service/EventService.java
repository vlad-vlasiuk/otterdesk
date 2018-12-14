package com.galvanize.otterdesk.service;

import com.galvanize.otterdesk.entity.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Value("${amqp.exchange.name}")
    String exchangeName;
    @Value("${amqp.message_bus.binding.key}")
    String routingKey;


    @Autowired
    AmqpSenderService senderService;

    private static final Logger LOGGER = LoggerFactory.getLogger(EventService.class);

    public void createProcess(String fileLocation) {
        workWithEvent(Event.builder()
                .currentProcessingPhase("upload")
                .fileLocation(fileLocation)
                .build());
    }

    public void workWithEvent(Event event) {
        LOGGER.info("need to work with event: {}",event);

        String phase = event.getCurrentProcessingPhase();
        if (phase.equals("upload"))
            uploadWork(event);
        else if (phase.equals("pdf_to_image"))
            pdfToImageWork(event);
        else if (phase.equals("orientation_worker"))
            orientationWork(event);
        else if (phase.equals("OCR_worker"))
            ocrWork(event);
        else if (phase.equals("Callouts_worker"))
            calloutsWork(event);
        else if (phase.equals("Search_indexer_worker"))
            searchIndexerWork(event);
        else if (phase.equals("Orchestrator")) {
            orchestratorWork(event);
        }

        Event newEvent = EventFactory.createEvent(event);
        if (phase.equals("Orchestrator"))
            LOGGER.info("all work done");
        else
            senderService.sendMessage(exchangeName, routingKey, newEvent);


    }

    private void orchestratorWork(Event event) {
        LOGGER.info("--------------------------");
        LOGGER.info("Orchestrator work with {}", event);
        LOGGER.info("--------------------------");
    }

    private void searchIndexerWork(Event event) {
        LOGGER.info("--------------------------");
        LOGGER.info("Search indexer work with {}", event);
        LOGGER.info("--------------------------");
    }

    private void calloutsWork(Event event) {
        LOGGER.info("--------------------------");
        LOGGER.info("Callouts work with {}", event);
        LOGGER.info("--------------------------");
    }

    private void ocrWork(Event event) {
        LOGGER.info("--------------------------");
        LOGGER.info("OCR work with {}", event);
        LOGGER.info("--------------------------");
    }

    private void orientationWork(Event event) {
        LOGGER.info("--------------------------");
        LOGGER.info("Orientation work with {}", event);
        LOGGER.info("--------------------------");
    }

    private void pdfToImageWork(Event event) {
        LOGGER.info("--------------------------");
        LOGGER.info("Pdf to image work with {}", event);
        LOGGER.info("--------------------------");
    }

    private void uploadWork(Event event) {
        LOGGER.info("--------------------------");
        LOGGER.info("make upload work with {}", event);
        LOGGER.info("--------------------------");
    }
}
