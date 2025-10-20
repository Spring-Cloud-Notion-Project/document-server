package ufrn.imd.document_server.functions;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import ufrn.imd.document_server.models.DocumentEntity;
import ufrn.imd.document_server.services.DocumentService;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
public class DocumentFunctions {

    private final DocumentService documentService;

    public DocumentFunctions(DocumentService documentService) {
        this.documentService = documentService;
    }

    @Bean
    public Function<String, ResponseEntity<DocumentEntity>> createdocument(){

        return text -> {
            DocumentEntity document = null;
            try {
                document = documentService.createAndSave(text);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return ResponseEntity.ok().body(document);
        };
    }

    @Bean
    public Supplier<List<DocumentEntity>> getalldocuments(){
        return() -> documentService.getAllDocuments();
    }

    @Bean
    public Function<Long, DocumentEntity> getdocumentbyid(){
        return id -> documentService.findById(id);
    }
}

