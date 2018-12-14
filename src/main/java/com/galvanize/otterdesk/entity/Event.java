package com.galvanize.otterdesk.entity;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Event {
    String blueprintId; //: '5d5f044f-ea3b-4533-9f55-2e1a45b02aab',
    String currentProcessingPhase; //: 'pdf_to_image',
    String fileLocation; //: 'https://s3.us-east-2.amazonaws.com/someco.com/uploads/pdfs/74efe087-7949-46db-8a8d-ee06776eb2b0.pdf',
    Date createTime = new Date();

    @Builder
    public Event(String blueprintId, String currentProcessingPhase, String fileLocation) {
        this.blueprintId = blueprintId;
        this.currentProcessingPhase = currentProcessingPhase;
        this.fileLocation = fileLocation;
    }
}
