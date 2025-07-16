package pl.zaitis.shop.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import pl.zaitis.shop.security.repository.UserRepository;
import pl.zaitis.shop.security.repository.model.User;
import pl.zaitis.shop.security.repository.model.UserRole;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
class LoginControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void shouldRegisterNewUser() throws Exception {
        // Given
        String registerJson = """
                {
                    "username": "test@example.com",
                    "password": "password123",
                    "repeatPassword": "password123"
                }
                """;

        // When & Then
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.adminAccess").value(false));

        // Verify user was created in database
        assertThat(userRepository.existsByUsername("test@example.com")).isTrue();
    }

    @Test
    void shouldNotRegisterUserWithMismatchedPasswords() throws Exception {
        // Given
        String registerJson = """
                {
                    "username": "test@example.com",
                    "password": "password123",
                    "repeatPassword": "differentpassword"
                }
                """;

        // When & Then
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotRegisterUserWithExistingEmail() throws Exception {
        // Given - create existing user
        User existingUser = User.builder()
                .username("existing@example.com")
                .password("{bcrypt}$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy")
                .enabled(true)
                .authorities(List.of(UserRole.ROLE_CUSTOMER))
                .build();
        userRepository.save(existingUser);

        String registerJson = """
                {
                    "username": "existing@example.com",
                    "password": "password123",
                    "repeatPassword": "password123"
                }
                """;

        // When & Then
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldLoginWithValidCredentials() throws Exception {
        // Given - create user first
        String registerJson = """
                {
                    "username": "test@example.com",
                    "password": "password123",
                    "repeatPassword": "password123"
                }
                """;

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerJson));

        String loginJson = """
                {
                    "username": "test@example.com",
                    "password": "password123"
                }
                """;

        // When & Then
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.adminAccess").value(false));
    }

    @Test
    void shouldNotLoginWithInvalidCredentials() throws Exception {
        // Given - create user first
        String registerJson = """
                {
                    "username": "test@example.com",
                    "password": "password123",
                    "repeatPassword": "password123"
                }
                """;

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerJson));

        String loginJson = """
                {
                    "username": "test@example.com",
                    "password": "wrongpassword"
                }
                """;

        // When & Then
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnAdminAccessForAdminUser() throws Exception {
        // Given - create admin user
        User adminUser = User.builder()
                .username("admin@example.com")
                .password("{bcrypt}$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy")
                .enabled(true)
                .authorities(List.of(UserRole.ROLE_ADMIN))
                .build();
        userRepository.save(adminUser);

        String loginJson = """
                {
                    "username": "admin@example.com",
                    "password": "zaitis123"
                }
                """;

        // When & Then
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.adminAccess").value(true));
    }

    @Test
    void shouldValidateEmailFormat() throws Exception {
        // Given
        String registerJson = """
                {
                    "username": "invalid-email",
                    "password": "password123",
                    "repeatPassword": "password123"
                }
                """;

        // When & Then
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerJson))
                .andExpect(status().isBadRequest());
    }
} 