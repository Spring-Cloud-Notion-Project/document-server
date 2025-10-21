package ufrn.imd.document_server.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ufrn.imd.document_server.models.DocumentEntity;

public interface DocumentRepository extends ReactiveCrudRepository<DocumentEntity, Long> {
}
