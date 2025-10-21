package ufrn.imd.document_server.mappers;

import ufrn.imd.document_server.dto.DocumentDTO;
import ufrn.imd.document_server.models.DocumentEntity;

public class DocumentMapper {
    public static DocumentDTO toDto(DocumentEntity document){
        DocumentDTO dto = new DocumentDTO(document.getId(), document.getName(), document.getFullPath());
        return dto;
    }

    public static DocumentEntity toEntity(DocumentDTO dto){
        DocumentEntity document = new DocumentEntity();
        document.setName(dto.getName());
        document.setFullPath(dto.getFullPath());

        return document;
    }
}
