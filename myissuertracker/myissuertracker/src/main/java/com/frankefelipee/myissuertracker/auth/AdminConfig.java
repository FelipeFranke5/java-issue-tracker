package com.frankefelipee.myissuertracker.auth;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.frankefelipee.myissuertracker.user.UserRepository;
import com.frankefelipee.myissuertracker.user_role.UserRoleRepository;

@Configuration
public class AdminConfig implements CommandLineRunner {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdminConfig(UserRoleRepository userRoleRepository, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }

    @Override
    public void run(String... args) {

        // TODO Auto-generated method stub

    }

}
