package ufrn.imd.document_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ufrn.imd.document_server.models.DocumentEntity;

public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {
}
