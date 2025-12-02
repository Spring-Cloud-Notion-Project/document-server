package ufrn.imd.document_server.events.saga;

import java.util.UUID;

public interface ReportSaga extends Saga {
    UUID reportId();
}
