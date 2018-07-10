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
import java.util.Scanner;

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

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.setFont(PDType1Font.COURIER, 12);

        contentStream.beginText();
        contentStream.showText("--- Categories ---");

        for (int i = 0; i < categories.size(); i++) {
            Category category = categories.get(i);

            contentStream.newLineAtOffset(0, 50 + i*60);
            contentStream.showText("Name: " + category.getName());
            contentStream.newLineAtOffset(0, 50 + i*60 + 20);
            contentStream.showText("Parent: " + (category.getParent() == null ? "- none -" : category.getParent().getName()));
        }

        contentStream.endText();
        contentStream.close();

        page = new PDPage();
        document.addPage(page);

        contentStream = new PDPageContentStream(document, page);
        contentStream.setFont(PDType1Font.COURIER, 12);

        contentStream.beginText();
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
            contentStream.drawImage(image, 0, 50 + i*200, 100, 100);

            contentStream.beginText();

            contentStream.newLineAtOffset(0, 50 + i*200 + 100);
            contentStream.showText("Name: " + photo.getName());
            contentStream.newLineAtOffset(0, 50 + i*200 + 120);
            contentStream.showText("Description: " + photo.getDescription());
            contentStream.newLineAtOffset(0, 50 + i*200 + 140);
            contentStream.showText("Categories: ");
            for (Category category : photo.getCategories()) {
                contentStream.showText(category.getName() + ",");
            }
            contentStream.newLineAtOffset(0, 50 + i*200 + 160);
            contentStream.showText("Tags: ");
            for (Tag tag : photo.getTags()) {
                contentStream.showText(tag.getName() + ",");
            }

            contentStream.endText();
        }

        contentStream.close();

        return document;
    }
}
