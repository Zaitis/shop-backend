package pl.zaitis.shop.admin.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zaitis.shop.admin.review.model.AdminReview;
import pl.zaitis.shop.admin.review.repositroy.AdminReviewRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminReviewService {
    private final AdminReviewRepository reviewRepository;
    public List<AdminReview> getReviews() {
        return reviewRepository.findAll();
    }
    @Transactional
    public void moderate(Long id) {
        reviewRepository.moderate(id);
    }
    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }
}
