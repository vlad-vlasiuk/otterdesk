package com.galvanize.otterdesk.service;

import com.galvanize.otterdesk.entity.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AmqpListenerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AmqpListenerService.class);

    private final EventService eventService;

    @Autowired
    public AmqpListenerService(EventService eventService) {
        this.eventService = eventService;
    }

    @RabbitListener(queues = "${amqp.message_bus.queue}")
    public void verifyListener(final Event event) {
        LOGGER.info("Received message: {} from message_bus.", event);
        eventService.workWithEvent(event);
    }
}
