package ufrn.imd.document_server.events.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ufrn.imd.document_server.events.saga.ReportEvent;
import ufrn.imd.document_server.services.DocumentService;

@Service
public class ReportEventProcessorImpl implements ReportEventProcessor<ReportEvent> {

    private static final Logger log = LoggerFactory.getLogger(ReportEventProcessorConfig.class);

    private final DocumentService documentService;

    public ReportEventProcessorImpl(DocumentService documentService) {
        this.documentService = documentService;
    }

    @Override
    public Mono<ReportEvent> handle(ReportEvent.CreateReportDocument event) {
        return documentService.createAndSave(event.content())
                .map(doc -> (ReportEvent) new ReportEvent.DocumentCreated(event.reportId(), doc.getFullPath()))
                .onErrorResume(e -> {
                    log.error("Erro processando report {}", event.reportId(), e);
                    return Mono.just(new ReportEvent.ReportFailed(event.reportId()));
                });
    }
}
