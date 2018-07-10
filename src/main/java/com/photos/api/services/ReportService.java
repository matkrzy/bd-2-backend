package com.photos.api.services;

import com.photos.api.exceptions.*;
import com.photos.api.models.*;
import com.photos.api.models.enums.UserRole;
import com.photos.api.repositories.CategoryRepository;
import com.photos.api.repositories.PhotoRepository;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReportService {
    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserService userService;

    public PDDocument getReportByUser(User user) throws EntityGetDeniedException, IOException {
        User currentUser = userService.getCurrent();

        if (user != currentUser && currentUser.getRole() != UserRole.ADMIN) {
            throw new EntityGetDeniedException();
        }

        List<Category> categories = categoryRepository.findAllByUser(user);
        List<Photo> photos = photoRepository.findAllByUser(user);

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        float height = page.getMediaBox().getHeight();

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.setFont(PDType1Font.COURIER, 12);

        contentStream.beginText();
        contentStream.newLineAtOffset(50, height - 50);
        contentStream.showText("--- Categories ---");
        contentStream.newLineAtOffset(0, -50);

        for (int i = 0; i < categories.size(); i++) {
            Category category = categories.get(i);

            contentStream.showText("Name: " + category.getName());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Parent: " + (category.getParent() == null ? "- none -" : category.getParent().getName()));
            contentStream.newLineAtOffset(0, -50);
        }

        contentStream.endText();
        contentStream.close();

        page = new PDPage();
        document.addPage(page);
        height = page.getMediaBox().getHeight();

        contentStream = new PDPageContentStream(document, page);
        contentStream.setFont(PDType1Font.COURIER, 12);

        contentStream.beginText();
        contentStream.newLineAtOffset(50, height - 50);
        contentStream.showText("--- Photos ---");
        contentStream.endText();

        for (int i = 0; i < photos.size(); i++) {
            Photo photo = photos.get(i);

            String url = photo.getUrl();
            String ext = FilenameUtils.getExtension(url);
            InputStream inputStream = new URL(url).openStream();
            byte[] imageBytes = IOUtils.toByteArray(inputStream);
            inputStream.close();

            PDImageXObject image = PDImageXObject.createFromByteArray(document, imageBytes, "a." + ext);
            contentStream.drawImage(image, 50, height - (100 + i*200 + 150), 150, 150);

            contentStream.beginText();
            contentStream.newLineAtOffset(220, height - (100 + i*200 + 20));

            contentStream.showText("Name: " + photo.getName());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Description: " + photo.getDescription());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Creation date: " + photo.getCreationDate());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Categories: ");
            contentStream.showText(String.join(", ", photo.getCategories().stream().map(Category::getName).collect(Collectors.toList())));
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Tags: ");
            contentStream.showText(String.join(", ", photo.getTags().stream().map(Tag::getName).collect(Collectors.toList())));
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("State: " + photo.getState());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Visibility: " + photo.getVisibility());

            contentStream.endText();
        }

        contentStream.close();

        return document;
    }
}
