package pl.zaitis.shop.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zaitis.shop.product.model.Product;
import pl.zaitis.shop.product.repository.ProductRepository;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ProductService {


    private final ProductRepository productRepository;

    public List<Product> getProduct(){
        return productRepository.findAll();
    }

    public Product addProduct(Product product){
        return productRepository.save(product);
    }
}