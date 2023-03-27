package pl.zaitis.shop.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.zaitis.shop.admin.dto.AdminProductDto;
import pl.zaitis.shop.admin.model.AdminProduct;
import pl.zaitis.shop.admin.service.AdminProductService;

@Controller
@RequiredArgsConstructor
public class AdminProductController {

    private final AdminProductService adminProductService;

    @GetMapping("/admin/products")
    public ResponseEntity<Page<AdminProduct>> getProducts(Pageable pageable) {
        return ResponseEntity.ok(adminProductService.getProducts(pageable));
    }

    @GetMapping("/admin/products/{id}")
    public ResponseEntity<AdminProduct> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(adminProductService.getProduct(id));
    }

    @PostMapping("/admin/products")
    public ResponseEntity<AdminProduct> createProduct(@RequestBody AdminProductDto adminProductDto) {
        return ResponseEntity.ok(adminProductService.createProduct(mapAdminProduct(adminProductDto, null)));
    }

    @PutMapping("/admin/products/{id}")
    public ResponseEntity<AdminProduct> updateProduct(@RequestBody AdminProductDto adminProductDto, @PathVariable Long id) {
        return ResponseEntity.ok(adminProductService.updateProduct( mapAdminProduct(adminProductDto, id)));

    }

    private  AdminProduct mapAdminProduct(AdminProductDto adminProductDto, Long id) {
        return AdminProduct.builder()
                .id(id)
                .name(adminProductDto.getName())
                .description(adminProductDto.getDescription())
                .category(adminProductDto.getCategory())
                .price(adminProductDto.getPrice())
                .currency(adminProductDto.getCurrency().toUpperCase())
                .build();
    }

}
