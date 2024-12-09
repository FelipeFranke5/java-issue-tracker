package com.frankefelipee.myissuertracker.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.frankefelipee.myissuertracker.auth.AuthRequest;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "users")
public class User {

    public boolean isLoginOk(AuthRequest authRequest, PasswordEncoder passwordEncoder) {

        return passwordEncoder.matches(authRequest.password(), this.getPassword());

    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID id;

    @NotBlank(message = "Username is required")
    @Column(unique = true)
    private String name;

    @NotBlank(message = "Password is required")
    private String password;

}
