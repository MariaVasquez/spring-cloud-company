package com.debugideas.report_listener.repositories;

import com.debugideas.report_listener.documents.ReportDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReportRepository extends MongoRepository<ReportDocument, String> {
}
