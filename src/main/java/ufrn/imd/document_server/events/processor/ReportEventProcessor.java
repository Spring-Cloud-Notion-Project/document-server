package ufrn.imd.document_server.events.processor;

import reactor.core.publisher.Mono;
import ufrn.imd.document_server.events.saga.DomainEvent;
import ufrn.imd.document_server.events.saga.ReportEvent;

public interface ReportEventProcessor<R extends DomainEvent> extends EventProcessor<ReportEvent, R> {

    @Override
    default Mono<R> process(ReportEvent event) {
        return switch (event){
            case ReportEvent.ReportContentGenerated e -> this.handle(e);
            default -> throw new IllegalStateException("Unexpected value: " + event);
        };
    }

    Mono<R> handle(ReportEvent.ReportContentGenerated event);
}