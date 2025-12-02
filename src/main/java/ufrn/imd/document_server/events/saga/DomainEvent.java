package ufrn.imd.document_server.events.saga;

import java.time.Instant;

public interface DomainEvent {
    Instant createdAt();
}