package com.frankefelipee.myissuertracker.auth;

import java.time.Instant;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frankefelipee.myissuertracker.user.UserRepository;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/get_token")
    public ResponseEntity<AuthResponse> getToken(@Valid @RequestBody AuthRequest authRequest) {

        var user = userRepository.findByName(authRequest.name());

        if ((user.isEmpty()) || (!user.get().isLoginOk(authRequest, bCryptPasswordEncoder))) {
            throw new BadCredentialsException("User or Password is incorrect");
        }

        var now = Instant.now();
        var expiresIn = 500L;

        var claims = JwtClaimsSet.builder()
                .issuer("myissuertracker")
                .subject(user.get().getId().toString())
                .expiresAt(now.plusSeconds(expiresIn))
                .issuedAt(now)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new AuthResponse(jwtValue, expiresIn));

    }

}
