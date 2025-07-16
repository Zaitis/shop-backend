package pl.zaitis.shop.product.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pl.zaitis.shop.common.model.Product;
import pl.zaitis.shop.common.model.Review;
import pl.zaitis.shop.common.repository.ProductRepository;
import pl.zaitis.shop.common.repository.ReviewRepository;
import pl.zaitis.shop.product.service.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldGetProducts() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        List<Product> products = List.of(
                Product.builder()
                        .id(1L)
                        .name("Product 1")
                        .price(new BigDecimal("10.99"))
                        .slug("product-1")
                        .build(),
                Product.builder()
                        .id(2L)
                        .name("Product 2")
                        .price(new BigDecimal("20.99"))
                        .slug("product-2")
                        .build()
        );
        Page<Product> productPage = new PageImpl<>(products, pageable, products.size());
        when(productRepository.findAll(pageable)).thenReturn(productPage);

        // When
        Page<Product> result = productService.getProduct(pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Product 1");
        assertThat(result.getContent().get(1).getName()).isEqualTo("Product 2");
    }

    @Test
    void shouldAddProduct() {
        // Given
        Product product = Product.builder()
                .name("New Product")
                .price(new BigDecimal("15.99"))
                .slug("new-product")
                .build();
        
        Product savedProduct = Product.builder()
                .id(1L)
                .name("New Product")
                .price(new BigDecimal("15.99"))
                .slug("new-product")
                .build();

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        // When
        Product result = productService.addProduct(product);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("New Product");
        assertThat(result.getPrice()).isEqualTo(new BigDecimal("15.99"));
    }

    @Test
    void shouldGetProductBySlug() {
        // Given
        String slug = "test-product";
        Product product = Product.builder()
                .id(1L)
                .name("Test Product")
                .price(new BigDecimal("19.99"))
                .slug(slug)
                .description("Test description")
                .fullDescription("Full test description")
                .build();

        List<Review> reviews = List.of(
                Review.builder()
                        .id(1L)
                        .authorName("John Doe")
                        .content("Great product!")
                        .productId(1L)
                        .moderated(true)
                        .build(),
                Review.builder()
                        .id(2L)
                        .authorName("Jane Smith")
                        .content("Very good quality")
                        .productId(1L)
                        .moderated(true)
                        .build()
        );

        when(productRepository.findBySlug(slug)).thenReturn(Optional.of(product));
        when(reviewRepository.findAllByProductIdAndModerated(eq(1L), eq(true))).thenReturn(reviews);

        // When
        ProductDto result = productService.getProductBySlug(slug);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Test Product");
        assertThat(result.getPrice()).isEqualTo(new BigDecimal("19.99"));
        assertThat(result.getSlug()).isEqualTo(slug);
        assertThat(result.getDescription()).isEqualTo("Test description");
        assertThat(result.getFullDescription()).isEqualTo("Full test description");
        assertThat(result.getReviews()).hasSize(2);
        assertThat(result.getReviews().get(0).getAuthorName()).isEqualTo("John Doe");
        assertThat(result.getReviews().get(1).getAuthorName()).isEqualTo("Jane Smith");
    }

    @Test
    void shouldThrowExceptionWhenProductNotFoundBySlug() {
        // Given
        String slug = "non-existent-product";
        when(productRepository.findBySlug(slug)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            productService.getProductBySlug(slug);
        });
    }

    @Test
    void shouldGetProductBySlugWithNoReviews() {
        // Given
        String slug = "no-reviews-product";
        Product product = Product.builder()
                .id(1L)
                .name("No Reviews Product")
                .price(new BigDecimal("29.99"))
                .slug(slug)
                .description("Product without reviews")
                .fullDescription("Full description of product without reviews")
                .build();

        when(productRepository.findBySlug(slug)).thenReturn(Optional.of(product));
        when(reviewRepository.findAllByProductIdAndModerated(eq(1L), eq(true))).thenReturn(List.of());

        // When
        ProductDto result = productService.getProductBySlug(slug);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("No Reviews Product");
        assertThat(result.getReviews()).isEmpty();
    }

    @Test
    void shouldGetProductBySlugWithOnlyModeratedReviews() {
        // Given
        String slug = "moderated-reviews-product";
        Product product = Product.builder()
                .id(1L)
                .name("Moderated Reviews Product")
                .price(new BigDecimal("39.99"))
                .slug(slug)
                .build();

        List<Review> moderatedReviews = List.of(
                Review.builder()
                        .id(1L)
                        .authorName("Approved User")
                        .content("This review is approved")
                        .productId(1L)
                        .moderated(true)
                        .build()
        );

        when(productRepository.findBySlug(slug)).thenReturn(Optional.of(product));
        when(reviewRepository.findAllByProductIdAndModerated(eq(1L), eq(true))).thenReturn(moderatedReviews);

        // When
        ProductDto result = productService.getProductBySlug(slug);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getReviews()).hasSize(1);
        assertThat(result.getReviews().get(0).getAuthorName()).isEqualTo("Approved User");
    }
} 