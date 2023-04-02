package pl.zaitis.shop.product.contoller;


import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.zaitis.shop.product.model.Product;
import pl.zaitis.shop.product.service.ProductService;

@RestController
@RequiredArgsConstructor
@Validated
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

    @GetMapping("/products/{slug}")
    public Product getProductBySlug(@PathVariable
                                        @Pattern(regexp= "[a-z0-9\\-]+")
                                        @Length(max= 255)
                                            String slug) {
       return productService.getProductBySlug(slug);
    }
}