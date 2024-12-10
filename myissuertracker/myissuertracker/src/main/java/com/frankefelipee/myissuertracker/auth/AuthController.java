package com.frankefelipee.myissuertracker.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final JwtEncoder jwtEncoder;
    private final AuthService authService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    @PostMapping("/get_token")
    public ResponseEntity<AuthResponse> getToken(@Valid @RequestBody AuthRequest authRequest) {

        String token = authService.getValidToken(authRequest, jwtEncoder, bCryptPasswordEncoder);

        if (token == null) {

            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        }

        AuthResponse response = new AuthResponse(token, 500L);
        return ResponseEntity.ok(response);

    }

}
