package ufrn.imd.document_server.dto;

public class DocumentDTO {

    private Long id;
    private String name;
    private String fullPath;

    // Construtor padrão (opcional, mas boa prática)
    public DocumentDTO() {
    }

    // Construtor com todos os campos (opcional, mas útil)
    public DocumentDTO(Long id, String name, String fullPath) {
        this.id = id;
        this.name = name;
        this.fullPath = fullPath;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }
}