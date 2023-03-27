package pl.zaitis.shop.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.zaitis.shop.product.model.Product;
import pl.zaitis.shop.product.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {


    private final ProductRepository productRepository;

    public Page<Product> getProduct(Pageable pageable){
        return productRepository.findAll(pageable);
    }

    public Product addProduct(Product product){
        return productRepository.save(product);
    }
}