package ufrn.imd.document_server.events.saga;

import java.time.Instant;
import java.util.UUID;

public sealed interface ReportEvent extends DomainEvent, ReportSaga {

    // eventos que serão emitidos pelo operation servic
    record ReportContentGenerated(UUID reportId, String content) implements ReportEvent{
        @Override
        public Instant createdAt() {
            return null;
        }
    }
    record DocumentCreated(UUID reportId, String path) implements ReportEvent{
        @Override
        public Instant createdAt() {
            return null;
        }
    }

    record ReportFailed(UUID reportId) implements ReportEvent {
        @Override
        public Instant createdAt() {
            return null;
        }
    }
}