package pl.zaitis.shop.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.zaitis.shop.security.repository.UserRepository;
import pl.zaitis.shop.security.repository.model.User;
import pl.zaitis.shop.security.repository.model.UserRole;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
public class LoginController {


    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final long expirationTime;
    private final String secret;

    public LoginController(AuthenticationManager authenticationManager,
                           UserRepository userRepository, PasswordEncoder passwordEncoder,
                           @Value("${jwt.expirationTime}") long expirationTime,
                           @Value("${jwt.secret}") String secret) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.expirationTime = expirationTime;
        this.secret = secret;
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginCredentials loginCredentials) {
        return authenticate(loginCredentials.getUsername(), loginCredentials.getPassword());
    }

    @PostMapping("/register")
    public Token register(@RequestBody @Valid RegisterCredentials registerCredentials) {
        if (!registerCredentials.getPassword().equals(registerCredentials.getRepeatPassword())) {
            throw new IllegalArgumentException("Passwords are not identical");
        }
        if (userRepository.existsByUsername(registerCredentials.getUsername())) {
            throw new IllegalArgumentException("This email already exists");
        }
        userRepository.save(User.builder()
                .username(registerCredentials.getUsername())
                .password(passwordEncoder.encode(registerCredentials.getPassword()))
                .enabled(true)
                .authorities(List.of(UserRole.ROLE_CUSTOMER))
                .build());
        return authenticate(
                registerCredentials.getUsername(),
                registerCredentials.getPassword());
    }


    private Token authenticate(String username, String password) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password));

        UserDetails principal = (UserDetails) authenticate.getPrincipal();
        String token = JWT.create()
                .withSubject(principal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC256(secret));


        return new Token(token, principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(s -> UserRole.ROLE_ADMIN.name().equals(s))
                .map(s -> true)
                .findFirst()
                .orElse(false)
        );
    }

    @Getter
    private static class LoginCredentials {
        private String username;
        private String password;
    }

    @Getter
    private static class RegisterCredentials {
        @Email
        private String username;
        @NotBlank
        private String password;
        @NotBlank
        private String repeatPassword;
    }

    @Getter
    @AllArgsConstructor
    private static class Token {
        private String token;
        private boolean adminAccess;

    }
}
