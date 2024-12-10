package com.frankefelipee.myissuertracker.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.frankefelipee.myissuertracker.user.User;
import com.frankefelipee.myissuertracker.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AdminConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private Environment environment;

    @Override
    public void run(String... args) {

        var adminUserName = environment.getProperty("server.admin.username");
        var adminPassword = environment.getProperty("server.admin.password");
        var admin = userRepository.findByName(adminUserName);

        if (admin.isEmpty()) {

            var user = new User();
            user.setName(adminUserName);
            user.setPassword(bCryptPasswordEncoder.encode(adminPassword));
            userRepository.save(user);

        }

    }

}
