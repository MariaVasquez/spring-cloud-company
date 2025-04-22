package com.debugideas.report_listener.documents;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
public class ReportDocument {
    @Id
    private String id;
    private String content;
}
