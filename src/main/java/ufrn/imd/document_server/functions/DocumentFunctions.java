package ufrn.imd.document_server.functions;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ufrn.imd.document_server.dto.DocumentDTO;
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
    public Function<Mono<String>, Mono<DocumentDTO>> createdocument(){
        return text -> text.flatMap(content -> documentService.createAndSave(content));
    }

    @Bean
    public Supplier<Flux<DocumentDTO>> getalldocuments(){
        return() -> documentService.getAllDocuments();
    }

    @Bean
    public Function<Mono<Long>, Mono<DocumentDTO>> getdocumentbyid(){
        return monoId -> monoId.flatMap(id-> documentService.findById(id));
    }
}

