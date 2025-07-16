package pl.zaitis.shop.review.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.zaitis.shop.common.model.Review;
import pl.zaitis.shop.common.repository.ReviewRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void shouldAddReview() {
        // Given
        Review review = Review.builder()
                .authorName("John Doe")
                .content("Great product!")
                .productId(1L)
                .build();

        Review savedReview = Review.builder()
                .id(1L)
                .authorName("John Doe")
                .content("Great product!")
                .productId(1L)
                .moderated(false)
                .build();

        when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);

        // When
        Review result = reviewService.addReview(review);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getAuthorName()).isEqualTo("John Doe");
        assertThat(result.getContent()).isEqualTo("Great product!");
        assertThat(result.getProductId()).isEqualTo(1L);
        assertThat(result.isModerated()).isFalse();
    }

    @Test
    void shouldAddReviewWithLongContent() {
        // Given
        String longContent = "This is a very long review content that should be handled properly by the service. ".repeat(10);
        Review review = Review.builder()
                .authorName("Jane Smith")
                .content(longContent)
                .productId(2L)
                .build();

        Review savedReview = Review.builder()
                .id(2L)
                .authorName("Jane Smith")
                .content(longContent)
                .productId(2L)
                .moderated(false)
                .build();

        when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);

        // When
        Review result = reviewService.addReview(review);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(2L);
        assertThat(result.getAuthorName()).isEqualTo("Jane Smith");
        assertThat(result.getContent()).isEqualTo(longContent);
        assertThat(result.getProductId()).isEqualTo(2L);
        assertThat(result.isModerated()).isFalse();
    }

    @Test
    void shouldAddReviewWithSpecialCharacters() {
        // Given
        Review review = Review.builder()
                .authorName("Test User")
                .content("Great product! ⭐⭐⭐⭐⭐ Would buy again 😊")
                .productId(3L)
                .build();

        Review savedReview = Review.builder()
                .id(3L)
                .authorName("Test User")
                .content("Great product! ⭐⭐⭐⭐⭐ Would buy again 😊")
                .productId(3L)
                .moderated(false)
                .build();

        when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);

        // When
        Review result = reviewService.addReview(review);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(3L);
        assertThat(result.getAuthorName()).isEqualTo("Test User");
        assertThat(result.getContent()).isEqualTo("Great product! ⭐⭐⭐⭐⭐ Would buy again 😊");
        assertThat(result.getProductId()).isEqualTo(3L);
        assertThat(result.isModerated()).isFalse();
    }

    @Test
    void shouldAddReviewWithMinimalContent() {
        // Given
        Review review = Review.builder()
                .authorName("Bob")
                .content("Good")
                .productId(4L)
                .build();

        Review savedReview = Review.builder()
                .id(4L)
                .authorName("Bob")
                .content("Good")
                .productId(4L)
                .moderated(false)
                .build();

        when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);

        // When
        Review result = reviewService.addReview(review);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(4L);
        assertThat(result.getAuthorName()).isEqualTo("Bob");
        assertThat(result.getContent()).isEqualTo("Good");
        assertThat(result.getProductId()).isEqualTo(4L);
        assertThat(result.isModerated()).isFalse();
    }

    @Test
    void shouldAddReviewWithNullInitialModeration() {
        // Given
        Review review = Review.builder()
                .authorName("Alice Johnson")
                .content("Excellent quality product!")
                .productId(5L)
                .build();

        Review savedReview = Review.builder()
                .id(5L)
                .authorName("Alice Johnson")
                .content("Excellent quality product!")
                .productId(5L)
                .moderated(false)
                .build();

        when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);

        // When
        Review result = reviewService.addReview(review);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(5L);
        assertThat(result.getAuthorName()).isEqualTo("Alice Johnson");
        assertThat(result.getContent()).isEqualTo("Excellent quality product!");
        assertThat(result.getProductId()).isEqualTo(5L);
        assertThat(result.isModerated()).isFalse();
    }
} 