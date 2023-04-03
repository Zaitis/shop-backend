package pl.zaitis.shop.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zaitis.shop.category.model.Category;


public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findBySlug(String slug);
}
