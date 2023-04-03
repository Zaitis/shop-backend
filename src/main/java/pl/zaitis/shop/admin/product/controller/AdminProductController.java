package pl.zaitis.shop.admin.product.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.zaitis.shop.admin.product.controller.dto.AdminProductDto;
import pl.zaitis.shop.admin.product.controller.dto.UploadResponse;
import pl.zaitis.shop.admin.product.model.AdminProduct;
import pl.zaitis.shop.admin.product.service.AdminProductService;
import pl.zaitis.shop.admin.product.service.AdminProductImageService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static pl.zaitis.shop.admin.common.utils.SlugfyUtils.slugifySlugName;

@RestController
@RequiredArgsConstructor
public class AdminProductController {

    private final AdminProductService adminProductService;
    private final AdminProductImageService adminProductImageService;

    @GetMapping("/admin/products")
    public ResponseEntity<Page<AdminProduct>> getProducts(Pageable pageable) {
        return ResponseEntity.ok(adminProductService.getProducts(pageable));
    }

    @GetMapping("/admin/products/{id}")
    public ResponseEntity<AdminProduct> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(adminProductService.getProduct(id));
    }

    @PostMapping("/admin/products")
    public ResponseEntity<AdminProduct> createProduct(@RequestBody @Valid AdminProductDto adminProductDto) {
        return ResponseEntity.ok(adminProductService.createProduct(mapAdminProduct(adminProductDto,null)));
    }

    @PutMapping("/admin/products/{id}")
    public ResponseEntity<AdminProduct> updateProduct(@RequestBody @Valid AdminProductDto adminProductDto, @PathVariable Long id) {
        return ResponseEntity.ok(adminProductService.updateProduct( mapAdminProduct(adminProductDto, id)));
    }

    @DeleteMapping("/admin/products/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id){
        adminProductService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin/products/upload")
    public UploadResponse uploadImage(@RequestParam(value = "file") MultipartFile multipartFile){
        String filename = multipartFile.getOriginalFilename();

        try(InputStream inputStream = multipartFile.getInputStream()){
            String savedFileName =adminProductImageService.uploadImage(filename, inputStream);
            return new UploadResponse(savedFileName);
        } catch (IOException e) {
            throw new RuntimeException("Something is wrong with save file", e);
        }
    }

    @GetMapping("/data/productImage/{filename}")
    public ResponseEntity<Resource> serveFiles(@PathVariable String filename) throws IOException {
       Resource resource =adminProductImageService.serveFiles(filename);

       return ResponseEntity.ok()
               .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(Path.of(filename)))
               .body(resource);
    }

    private  AdminProduct mapAdminProduct(AdminProductDto adminProductDto, Long id) {
        return AdminProduct.builder()
                .id(id)
                .name(adminProductDto.getName())
                .description(adminProductDto.getDescription())
                .fullDescription(adminProductDto.getFullDescription())
                .categoryId(adminProductDto.getCategoryId())
                .price(adminProductDto.getPrice())
                .currency(adminProductDto.getCurrency())
                .image(adminProductDto.getImage())
                .slug(slugifySlugName(adminProductDto.getSlug()))
                .build();
    }



}