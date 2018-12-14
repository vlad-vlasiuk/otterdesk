package com.galvanize.otterdesk.service;

import com.galvanize.otterdesk.entity.Event;

import java.util.UUID;

public class EventFactory {

    public static Event createEvent(Event event) {
        Event.EventBuilder eventBuilder = Event.builder()
                .blueprintId(UUID.randomUUID().toString())
                .fileLocation(event.getFileLocation());

        String phase = event.getCurrentProcessingPhase();
        if (phase.equals("upload"))
            eventBuilder.currentProcessingPhase("pdf_to_image");
        else if (phase.equals("pdf_to_image"))
            eventBuilder.currentProcessingPhase("orientation_worker");
        else if (phase.equals("orientation_worker"))
            eventBuilder.currentProcessingPhase("OCR_worker");
        else if (phase.equals("OCR_worker"))
            eventBuilder.currentProcessingPhase("Callouts_worker");
        else if (phase.equals("Callouts_worker"))
            eventBuilder.currentProcessingPhase("Search_indexer_worker");
        else if (phase.equals("Search_indexer_worker"))
            eventBuilder.currentProcessingPhase("Orchestrator");

        return eventBuilder.build();
    }
}
