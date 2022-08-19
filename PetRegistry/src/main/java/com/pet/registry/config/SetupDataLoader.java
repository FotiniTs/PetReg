package com.pet.registry.config;

import java.util.*;

import com.pet.registry.models.User;
import com.pet.registry.models.enums.Role;
import com.pet.registry.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // API

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        // == create initial user
        createUserIfNotFound("admin", "admin", "admin", "admin","Athens","Halandri",Role.ROLE_ADMIN);

        alreadySetup = true;
    }


    @Transactional
    User createUserIfNotFound(final String username, final String firstName, final String lastName, final String password, final String city, final  String state , final Role role) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            user = new User();
            user.setFirst_name(firstName);
            user.setLast_name(lastName);
            user.setPassword(passwordEncoder.encode(password));
            user.setUsername(username);
            user.setCity(city);
            user.setState(state);
            user.setRole(role);
        }
        user = userRepository.save(user);
        return user;
    }

}
