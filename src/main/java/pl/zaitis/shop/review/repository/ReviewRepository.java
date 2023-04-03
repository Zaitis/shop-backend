package pl.zaitis.shop.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zaitis.shop.review.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
