package pl.zaitis.shop.admin.product.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import pl.zaitis.shop.admin.common.utils.SlugfyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AdminProductImageService {

    private static final Logger logger = LoggerFactory.getLogger(AdminProductImageService.class);

    @Value("${app.uploadDir}")
    private String uploadDir;

    private Path getUploadPath() {
        Path path = Paths.get(uploadDir).toAbsolutePath().normalize();
        Path currentDir = Paths.get("").toAbsolutePath();
        logger.debug("Current working directory: {}", currentDir);
        logger.debug("Resolved upload path: {} -> {}", uploadDir, path);
        
        // Ensure the directory exists
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                logger.info("Created upload directory: {}", path);
            } catch (IOException e) {
                logger.error("Failed to create upload directory: {}", path, e);
                throw new RuntimeException("Failed to create upload directory", e);
            }
        }
        
        return path;
    }

    public String uploadImage(String filename, InputStream inputStream) {
        String newFileName= SlugfyUtils.slugifyFileName(filename);
        Path uploadPath = getUploadPath();
        newFileName =ExistingFileRenameUtils.renameIfExists(uploadPath, newFileName);
        Path filePath = uploadPath.resolve(newFileName);

        logger.debug("Uploading image: filename={}, uploadPath={}, filePath={}", filename, uploadPath, filePath);

        try (OutputStream outputStream = Files.newOutputStream(filePath)) {
            inputStream.transferTo(outputStream);
            return newFileName;
        } catch (IOException e) {
            logger.error("Failed to save file: {}", filePath, e);
            throw new RuntimeException("You can't save a file", e);
        }
    }

    public Resource serveFiles(String filename) {
        FileSystemResourceLoader fileSystemResourceLoader = new FileSystemResourceLoader();
        Path uploadPath = getUploadPath();
        Path filePath = uploadPath.resolve(filename);
        
        logger.debug("Serving file: filename={}, uploadPath={}, filePath={}", filename, uploadPath, filePath);
        
        if (!Files.exists(filePath)) {
            logger.warn("File not found: {}", filePath);
            throw new RuntimeException("File not found: " + filename);
        }
        
        return fileSystemResourceLoader.getResource("file:" + filePath.toString());
    }
}
