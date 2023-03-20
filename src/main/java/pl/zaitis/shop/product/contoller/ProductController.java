package pl.zaitis.shop.product.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.zaitis.shop.product.model.Product;
import pl.zaitis.shop.product.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public List<Product> getProducts() {
        List<Product> list = productService.getProduct();
        return list;
    }

    @PostMapping("/products")
    public Product addProducts(@RequestBody Product product){
        productService.addProduct(product);
        return product;
    }

}