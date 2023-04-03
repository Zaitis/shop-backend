package pl.zaitis.shop.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.zaitis.shop.common.model.Product;
import pl.zaitis.shop.common.repository.ProductRepository;

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

    public Product getProductBySlug(String slug) {
        return productRepository.findBySlug(slug).orElseThrow();
        }
}
