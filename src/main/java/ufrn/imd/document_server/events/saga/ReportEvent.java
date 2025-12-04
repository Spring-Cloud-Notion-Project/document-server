package ufrn.imd.document_server.events.saga;

import java.util.UUID;

public sealed interface ReportEvent extends DomainEvent, ReportSaga {
    record DocumentCreated(UUID reportId, String path) implements ReportEvent{}
    record CreateReportDocument(UUID reportId, String content) implements ReportEvent {}
    record ReportFailed(UUID reportId) implements ReportEvent {}
}