package pl.zaitis.shop.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zaitis.shop.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}