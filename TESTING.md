# Testing Guide

This document provides comprehensive information about running and understanding the test suite for the Shop Backend application.

## 📋 Table of Contents
- [Test Structure](#test-structure)
- [Running Tests](#running-tests)
- [Test Categories](#test-categories)
- [Test Configuration](#test-configuration)
- [Writing Tests](#writing-tests)
- [Best Practices](#best-practices)
- [Troubleshooting](#troubleshooting)

## 🏗️ Test Structure

The test suite is organized into the following categories:

```
src/test/java/pl/zaitis/shop/
├── security/                 # Authentication & authorization tests
│   └── LoginControllerTest.java
├── product/                  # Product management tests
│   └── service/
│       └── ProductServiceTest.java
├── review/                   # Review system tests
│   └── service/
│       └── ReviewServiceTest.java
├── cart/                     # Shopping cart tests
│   └── service/
│       └── CartServiceTest.java
├── order/                    # Order management tests
│   └── service/
│       └── OrderServiceTest.java
├── admin/                    # Admin functionality tests
│   └── category/service/
│       └── AdminCategoryServiceTest.java
├── integration/              # Integration tests
│   └── ProductIntegrationTest.java
├── utils/                    # Test utilities
│   └── TestDataFactory.java
└── ShopApplicationTests.java # Context loading test
```

## 🚀 Running Tests

### Prerequisites
- Java 17+
- Maven 3.6+
- Internet connection (for downloading dependencies)

### All Tests
```bash
# Run all tests
mvn test

# Run tests with verbose output
mvn test -Dtest.output=verbose

# Run tests in parallel
mvn test -T 4

# Run tests with coverage
mvn test jacoco:report
```

### Specific Test Categories
```bash
# Run only unit tests
mvn test -Dtest="**/*Test"

# Run only integration tests
mvn test -Dtest="**/*IntegrationTest"

# Run security tests
mvn test -Dtest="pl.zaitis.shop.security.**"

# Run product tests
mvn test -Dtest="pl.zaitis.shop.product.**"

# Run a specific test class
mvn test -Dtest="LoginControllerTest"

# Run a specific test method
mvn test -Dtest="LoginControllerTest#shouldRegisterNewUser"
```

### Quick Test Commands
```bash
# Fast test run (skip integration tests)
mvn test -Dtest="**/*Test" -DfailIfNoTests=false

# Test with specific profile
mvn test -Dspring.profiles.active=test

# Test with debug logging
mvn test -Dlogging.level.org.springframework.test=DEBUG
```

## 📊 Test Categories

### 1. Unit Tests
- **Purpose**: Test individual components in isolation
- **Characteristics**: Fast, isolated, use mocks
- **Examples**: `ProductServiceTest`, `ReviewServiceTest`, `CartServiceTest`

### 2. Integration Tests
- **Purpose**: Test component interactions
- **Characteristics**: Use real database (H2), test full request/response cycle
- **Examples**: `ProductIntegrationTest`

### 3. Security Tests
- **Purpose**: Test authentication and authorization
- **Characteristics**: Test login, registration, JWT tokens, role-based access
- **Examples**: `LoginControllerTest`

### 4. Repository Tests
- **Purpose**: Test data access layer
- **Characteristics**: Test database operations, queries, relationships
- **Examples**: Currently covered by service tests

## ⚙️ Test Configuration

### Test Profile
Tests run with the `test` profile which:
- Uses H2 in-memory database
- Disables Liquibase migrations
- Uses fake email service
- Enables debug logging for tests

### Test Properties
```properties
# src/test/resources/application-test.properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.hibernate.ddl-auto=create-drop
spring.liquibase.enabled=false
jwt.secret=test-secret-key-for-testing-only
app.email.sender=fakeEmailService
```

### Test Dependencies
```xml
<!-- H2 Database for testing -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>test</scope>
</dependency>

<!-- Spring Security Test -->
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-test</artifactId>
    <scope>test</scope>
</dependency>
```

## ✍️ Writing Tests

### Test Naming Convention
- Class names: `{ComponentName}Test` or `{ComponentName}IntegrationTest`
- Method names: `should{ExpectedBehavior}When{Condition}`
- Example: `shouldReturnProductWhenValidSlugProvided`

### Test Structure (Given-When-Then)
```java
@Test
void shouldAddProductToCart() {
    // Given - Set up test data
    Product product = TestDataFactory.createSimpleProduct();
    when(productRepository.findById(1L)).thenReturn(Optional.of(product));
    
    // When - Execute the action
    CartProductDto result = cartService.addProductToCart(1L, new CartProductDto(1L, 2));
    
    // Then - Verify the result
    assertThat(result).isNotNull();
    assertThat(result.quantity()).isEqualTo(2);
}
```

### Using TestDataFactory
```java
// Create test data using the factory
Product product = TestDataFactory.createProduct("Test Product", "test-product", new BigDecimal("19.99"));
Review review = TestDataFactory.createReview(1L, "John Doe", "Great product!");
User user = TestDataFactory.createCustomerUser("test@example.com", "password123");

// Use constants for common values
String testEmail = TestDataFactory.Constants.TEST_USERNAME;
BigDecimal defaultPrice = TestDataFactory.Constants.DEFAULT_PRICE;
```

### Testing with MockMvc
```java
@Test
void shouldReturnProductList() throws Exception {
    mockMvc.perform(get("/products")
                    .param("page", "0")
                    .param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", hasSize(2)))
            .andExpect(jsonPath("$.content[0].name", is("Product 1")));
}
```

## 🎯 Best Practices

### 1. Test Independence
- Each test should be able to run independently
- Use `@Transactional` for database cleanup
- Don't rely on test execution order

### 2. Clear Test Names
```java
// Good
@Test
void shouldThrowExceptionWhenProductNotFound() { }

// Bad
@Test
void testProduct() { }
```

### 3. Use TestDataFactory
```java
// Good
Product product = TestDataFactory.createSimpleProduct();

// Bad
Product product = Product.builder()
    .name("Test Product")
    .slug("test-product")
    .price(new BigDecimal("19.99"))
    .build();
```

### 4. Mock External Dependencies
```java
@Mock
private EmailService emailService;

@Test
void shouldSendEmailWhenOrderPlaced() {
    // Mock external email service
    when(emailService.send(any(), any(), any())).thenReturn(true);
    
    // Test your business logic
    orderService.placeOrder(orderDto);
    
    // Verify interaction
    verify(emailService).send(eq("user@example.com"), any(), any());
}
```

### 5. Test Error Scenarios
```java
@Test
void shouldThrowExceptionWhenInvalidData() {
    // Test that your application handles errors gracefully
    assertThrows(IllegalArgumentException.class, () -> {
        productService.createProduct(null);
    });
}
```

## 🐛 Troubleshooting

### Common Issues

#### 1. Tests Pass Individually but Fail in Suite
**Problem**: Database state pollution between tests
**Solution**: Use `@Transactional` or `@DirtiesContext`

#### 2. H2 Database Issues
**Problem**: Database schema/data issues
**Solution**: Check `application-test.properties` and ensure `spring.jpa.hibernate.ddl-auto=create-drop`

#### 3. MockMvc Security Issues
**Problem**: Security configuration interfering with tests
**Solution**: Use `@WithMockUser` or configure security for tests

#### 4. JWT Token Issues in Tests
**Problem**: Token validation failing in tests
**Solution**: Use test profile with test JWT secret

### Debug Commands
```bash
# Run tests with debug output
mvn test -Dlogging.level.org.springframework.test=DEBUG

# Run single test with stack trace
mvn test -Dtest="LoginControllerTest#shouldRegisterNewUser" -Dmaven.test.failure.ignore=true

# Run tests with JVM debugging
mvn test -Dmaven.surefire.debug="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8000"
```

## 📈 Test Coverage

### Generate Coverage Report
```bash
mvn clean test jacoco:report
```

### View Coverage Report
Open `target/site/jacoco/index.html` in your browser

### Coverage Goals
- **Unit Tests**: Aim for 80%+ line coverage
- **Integration Tests**: Cover main user workflows
- **Critical Paths**: 100% coverage for security and payment logic

## 🔄 Continuous Integration

Tests are automatically run on:
- Every push to main branch
- Every pull request
- Scheduled nightly builds

### GitHub Actions
```yaml
- name: Run tests
  run: mvn test
  
- name: Generate test report
  run: mvn surefire-report:report
```

## 📚 Additional Resources

- [JUnit 5 Documentation](https://junit.org/junit5/docs/current/user-guide/)
- [Spring Boot Testing](https://spring.io/guides/gs/testing-web/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [AssertJ Documentation](https://assertj.github.io/doc/)

## 🤝 Contributing

When adding new tests:
1. Follow the existing naming conventions
2. Use TestDataFactory for test data
3. Add integration tests for new endpoints
4. Update this documentation for new test categories
5. Ensure tests are deterministic and independent

---

**Happy Testing!** 🎉 