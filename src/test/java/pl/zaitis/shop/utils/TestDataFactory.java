package pl.zaitis.shop.utils;

import pl.zaitis.shop.common.model.Product;
import pl.zaitis.shop.common.model.Review;
import pl.zaitis.shop.common.model.Cart;
import pl.zaitis.shop.common.model.CartItem;
import pl.zaitis.shop.security.repository.model.User;
import pl.zaitis.shop.security.repository.model.UserRole;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Factory class for creating test data objects.
 * This helps maintain consistency across tests and reduces code duplication.
 */
public class TestDataFactory {

    public static Product createProduct(String name, String slug, BigDecimal price) {
        return Product.builder()
                .name(name)
                .slug(slug)
                .price(price)
                .description("Test description for " + name)
                .fullDescription("Full test description for " + name)
                .build();
    }

    public static Product createProduct(Long id, String name, String slug, BigDecimal price) {
        return Product.builder()
                .id(id)
                .name(name)
                .slug(slug)
                .price(price)
                .description("Test description for " + name)
                .fullDescription("Full test description for " + name)
                .build();
    }

    public static Product createSimpleProduct() {
        return createProduct("Test Product", "test-product", new BigDecimal("19.99"));
    }

    public static Review createReview(Long productId, String authorName, String content) {
        return Review.builder()
                .productId(productId)
                .authorName(authorName)
                .content(content)
                .moderated(false)
                .build();
    }

    public static Review createReview(Long id, Long productId, String authorName, String content, boolean moderated) {
        return Review.builder()
                .id(id)
                .productId(productId)
                .authorName(authorName)
                .content(content)
                .moderated(moderated)
                .build();
    }

    public static Review createModeratedReview(Long productId, String authorName, String content) {
        return Review.builder()
                .productId(productId)
                .authorName(authorName)
                .content(content)
                .moderated(true)
                .build();
    }

    public static User createUser(String username, String password, UserRole role) {
        return User.builder()
                .username(username)
                .password(password)
                .enabled(true)
                .authorities(List.of(role))
                .build();
    }

    public static User createCustomerUser(String username, String password) {
        return createUser(username, password, UserRole.ROLE_CUSTOMER);
    }

    public static User createAdminUser(String username, String password) {
        return createUser(username, password, UserRole.ROLE_ADMIN);
    }

    public static Cart createCart() {
        return Cart.builder()
                .created(LocalDateTime.now())
                .build();
    }

    public static Cart createCart(Long id) {
        return Cart.builder()
                .id(id)
                .created(LocalDateTime.now())
                .build();
    }

    public static CartItem createCartItem(Long cartId, Product product, int quantity) {
        return CartItem.builder()
                .cartId(cartId)
                .product(product)
                .quantity(quantity)
                .build();
    }

    public static CartItem createCartItem(Long id, Long cartId, Product product, int quantity) {
        return CartItem.builder()
                .id(id)
                .cartId(cartId)
                .product(product)
                .quantity(quantity)
                .build();
    }

    // Helper methods for creating test data with common patterns
    public static List<Product> createProductList(int count) {
        return java.util.stream.IntStream.range(1, count + 1)
                .mapToObj(i -> createProduct(
                        "Product " + i,
                        "product-" + i,
                        new BigDecimal("10.00").add(new BigDecimal(i))
                ))
                .toList();
    }

    public static List<Review> createReviewList(Long productId, int count) {
        return java.util.stream.IntStream.range(1, count + 1)
                .mapToObj(i -> createReview(
                        productId,
                        "Author " + i,
                        "Review content " + i
                ))
                .toList();
    }

    public static List<Review> createModeratedReviewList(Long productId, int count) {
        return java.util.stream.IntStream.range(1, count + 1)
                .mapToObj(i -> createModeratedReview(
                        productId,
                        "Author " + i,
                        "Moderated review content " + i
                ))
                .toList();
    }

    // Constants for commonly used test values
    public static final class Constants {
        public static final String TEST_USERNAME = "test@example.com";
        public static final String TEST_PASSWORD = "password123";
        public static final String ADMIN_USERNAME = "admin@example.com";
        public static final String ADMIN_PASSWORD = "zaitis123";
        public static final String BCRYPT_TEST_PASSWORD = "{bcrypt}$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy";
        
        public static final BigDecimal DEFAULT_PRICE = new BigDecimal("19.99");
        public static final String DEFAULT_SLUG = "test-product";
        public static final String DEFAULT_PRODUCT_NAME = "Test Product";
        public static final String DEFAULT_DESCRIPTION = "Test description";
        public static final String DEFAULT_FULL_DESCRIPTION = "Full test description";
        
        public static final String DEFAULT_REVIEW_AUTHOR = "Test Author";
        public static final String DEFAULT_REVIEW_CONTENT = "Great product!";
        
        public static final int DEFAULT_QUANTITY = 1;
        public static final int DEFAULT_PAGE_SIZE = 10;
    }
} 