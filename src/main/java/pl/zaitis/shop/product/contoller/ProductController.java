package pl.zaitis.shop.product.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zaitis.shop.product.model.Product;
import pl.zaitis.shop.product.service.ProductService;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(Pageable pageable) {
        return ResponseEntity.ok(productService.getProduct(pageable));
    }

    @PostMapping("/products")
    public Product addProducts(@RequestBody Product product){
        productService.addProduct(product);
        return product;
    }

}