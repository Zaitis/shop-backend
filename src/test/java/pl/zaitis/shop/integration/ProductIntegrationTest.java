package pl.zaitis.shop.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import pl.zaitis.shop.common.model.Product;
import pl.zaitis.shop.common.repository.ProductRepository;
import pl.zaitis.shop.common.repository.ReviewRepository;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ProductIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void shouldGetAllProducts() throws Exception {
        // Given
        Product product1 = Product.builder()
                .name("Test Product 1")
                .price(new BigDecimal("19.99"))
                .slug("test-product-1")
                .description("Test description 1")
                .build();
        
        Product product2 = Product.builder()
                .name("Test Product 2")
                .price(new BigDecimal("29.99"))
                .slug("test-product-2")
                .description("Test description 2")
                .build();

        productRepository.save(product1);
        productRepository.save(product2);

        // When & Then
        mockMvc.perform(get("/products")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].name", is("Test Product 1")))
                .andExpect(jsonPath("$.content[0].price", is(19.99)))
                .andExpect(jsonPath("$.content[0].slug", is("test-product-1")))
                .andExpect(jsonPath("$.content[1].name", is("Test Product 2")))
                .andExpect(jsonPath("$.content[1].price", is(29.99)))
                .andExpect(jsonPath("$.content[1].slug", is("test-product-2")));
    }

    @Test
    void shouldGetProductBySlug() throws Exception {
        // Given
        Product product = Product.builder()
                .name("Single Product")
                .price(new BigDecimal("39.99"))
                .slug("single-product")
                .description("Single product description")
                .fullDescription("Full description of single product")
                .build();

        productRepository.save(product);

        // When & Then
        mockMvc.perform(get("/products/single-product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Single Product")))
                .andExpect(jsonPath("$.price", is(39.99)))
                .andExpect(jsonPath("$.slug", is("single-product")))
                .andExpect(jsonPath("$.description", is("Single product description")))
                .andExpect(jsonPath("$.fullDescription", is("Full description of single product")))
                .andExpect(jsonPath("$.reviews", hasSize(0)));
    }

    @Test
    void shouldReturn404ForNonExistentProduct() throws Exception {
        // When & Then
        mockMvc.perform(get("/products/non-existent-product"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldAddReviewToProduct() throws Exception {
        // Given
        Product product = Product.builder()
                .name("Reviewable Product")
                .price(new BigDecimal("49.99"))
                .slug("reviewable-product")
                .description("Product for review testing")
                .build();

        Product savedProduct = productRepository.save(product);

        String reviewJson = """
                {
                    "authorName": "John Reviewer",
                    "content": "Great product, highly recommend!",
                    "productId": %d
                }
                """.formatted(savedProduct.getId());

        // When & Then
        mockMvc.perform(post("/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reviewJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorName", is("John Reviewer")))
                .andExpect(jsonPath("$.content", is("Great product, highly recommend!")))
                .andExpect(jsonPath("$.productId", is(savedProduct.getId().intValue())))
                .andExpect(jsonPath("$.moderated", is(false)));
    }

    @Test
    void shouldValidateReviewInput() throws Exception {
        // Given
        Product product = Product.builder()
                .name("Product for Invalid Review")
                .price(new BigDecimal("59.99"))
                .slug("product-for-invalid-review")
                .description("Product for invalid review testing")
                .build();

        Product savedProduct = productRepository.save(product);

        String invalidReviewJson = """
                {
                    "authorName": "A",
                    "content": "Bad",
                    "productId": %d
                }
                """.formatted(savedProduct.getId());

        // When & Then
        mockMvc.perform(post("/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidReviewJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetEmptyProductsWhenNoneExist() throws Exception {
        // When & Then
        mockMvc.perform(get("/products")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andExpect(jsonPath("$.totalElements", is(0)))
                .andExpect(jsonPath("$.totalPages", is(0)));
    }

    @Test
    void shouldHandlePagination() throws Exception {
        // Given - create multiple products
        for (int i = 1; i <= 15; i++) {
            Product product = Product.builder()
                    .name("Product " + i)
                    .price(new BigDecimal("10.00").add(new BigDecimal(i)))
                    .slug("product-" + i)
                    .description("Description for product " + i)
                    .build();
            productRepository.save(product);
        }

        // When & Then - first page
        mockMvc.perform(get("/products")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(10)))
                .andExpect(jsonPath("$.totalElements", is(15)))
                .andExpect(jsonPath("$.totalPages", is(2)))
                .andExpect(jsonPath("$.first", is(true)))
                .andExpect(jsonPath("$.last", is(false)));

        // When & Then - second page
        mockMvc.perform(get("/products")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)))
                .andExpect(jsonPath("$.totalElements", is(15)))
                .andExpect(jsonPath("$.totalPages", is(2)))
                .andExpect(jsonPath("$.first", is(false)))
                .andExpect(jsonPath("$.last", is(true)));
    }

    @Test
    void shouldValidateProductSlugFormat() throws Exception {
        // When & Then - invalid slug with uppercase
        mockMvc.perform(get("/products/INVALID-SLUG"))
                .andExpect(status().isBadRequest());

        // When & Then - invalid slug with special characters
        mockMvc.perform(get("/products/invalid@slug"))
                .andExpect(status().isBadRequest());
    }
} 