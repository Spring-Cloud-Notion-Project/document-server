package ufrn.imd.document_server.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import ufrn.imd.document_server.models.DocumentEntity;
import ufrn.imd.document_server.repositories.DocumentRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    @Value("${upload_directory}")
    private String ROOT_DIRECTORY;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public DocumentEntity createAndSave(String text) throws IOException {
        byte[] pdfDocument = generatePdf(text);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS");
        LocalDateTime now = LocalDateTime.now();

        String name = "doc_" + now.format(formatter) + ".pdf";

        Path directoryPath = Paths.get(ROOT_DIRECTORY);
        Files.createDirectories(directoryPath);

        Path fullPath = directoryPath.resolve(name);

        try {
            Files.write(fullPath, pdfDocument);
        } catch (IOException e){
            throw new IOException("Falha ao salvar o arquivo no disco.", e);
        }

        DocumentEntity document = new DocumentEntity();
        document.setName(name);
        document.setFullPath(fullPath.toString());

        return documentRepository.save(document);
    }

    private static byte[] generatePdf(String text) {
        text = text.replace("\n", "").replace("\r", "");

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDType1Font font = new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN);
            int fontSize = 12;
            float leading = 1.5f * fontSize;
            float margin = 50;
            float width = page.getMediaBox().getWidth() - 2 * margin;
            float startX = page.getMediaBox().getLowerLeftX() + margin;
            float startY = page.getMediaBox().getUpperRightY() - margin;

            List<String> lines = new ArrayList<>();
            String[] words = text.split(" ");
            StringBuilder lineBuilder = new StringBuilder();

            for (String word : words) {
                String proposedLine;
                if (lineBuilder.length() == 0) {
                    proposedLine = word;
                } else {
                    proposedLine = lineBuilder + " " + word;
                }

                float size = fontSize * font.getStringWidth(proposedLine) / 1000;

                if (size <= width) {
                    lineBuilder = new StringBuilder(proposedLine);
                } else {
                    if (lineBuilder.length() > 0) {
                        lines.add(lineBuilder.toString());
                    }
                    lineBuilder = new StringBuilder(word);
                }
            }
            if (lineBuilder.length() > 0) {
                lines.add(lineBuilder.toString());
            }

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(font, fontSize);
                contentStream.setLeading(leading);
                contentStream.newLineAtOffset(startX, startY);

                for (String line : lines) {
                    contentStream.showText(line);
                    contentStream.newLine();
                }
                contentStream.endText();
            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);

            return byteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao gerar o pdf");
        }
    }

    public List<DocumentEntity> getAllDocuments() {
        return documentRepository.findAll();
    }

    public DocumentEntity findById(long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento não encontrado"));
    }
}
