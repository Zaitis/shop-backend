package pl.zaitis.shop.product.contoller;


import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.zaitis.shop.common.dto.ProductListDto;
import pl.zaitis.shop.common.model.Product;
import pl.zaitis.shop.product.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Page<ProductListDto>> getProducts(Pageable pageable) {
        Page<Product> products =productService.getProduct(pageable);
        List<ProductListDto> productListDtoList =
                products.getContent().stream()
                .map(product -> ProductListDto.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .currency(product.getCurrency())
                        .image(product.getImage())
                        .slug(product.getSlug())
                        .build())
                .toList();
        return ResponseEntity.ok(new PageImpl<>(productListDtoList, pageable, products.getTotalElements()));
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