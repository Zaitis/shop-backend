package pl.zaitis.shop.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zaitis.shop.admin.model.AdminProduct;

public interface AdminProductRepository extends JpaRepository<AdminProduct, Long> {
}
