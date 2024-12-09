package com.frankefelipee.myissuertracker.auth;

import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.frankefelipee.myissuertracker.user.User;
import com.frankefelipee.myissuertracker.user.UserRepository;
import com.frankefelipee.myissuertracker.user_role.UserRole;
import com.frankefelipee.myissuertracker.user_role.UserRoleRepository;

@Configuration
public class AdminConfig implements CommandLineRunner {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${server.admin.username}")
    private final String adminUserName;

    @Value("${server.admin.password}")
    private final String adminPassword;

    public AdminConfig(
            UserRoleRepository userRoleRepository,
            UserRepository userRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            String adminUserName,
            String adminPassword
    ) {

        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.adminUserName = adminUserName;
        this.adminPassword = adminPassword;

    }

    @Override
    public void run(String... args) {

        var admin = userRepository.findByName(adminUserName);
        var adminRole = userRoleRepository.findByName(UserRole.Values.ADMIN.name());

        if (adminRole.isEmpty()) {
            var basicRole = new UserRole();
            basicRole.setName("BASIC");
            userRoleRepository.save(basicRole);
            var newAdminRole = new UserRole();
            newAdminRole.setId(2L);
            newAdminRole.setName("ADMIN");
        }

        if (admin.isEmpty()) {
            var user = new User();
            user.setName(adminUserName);
            user.setPassword(bCryptPasswordEncoder.encode(adminPassword));
            user.setRoles(Set.of(adminRole.get()));
            userRepository.save(user);
        }

    }

}
