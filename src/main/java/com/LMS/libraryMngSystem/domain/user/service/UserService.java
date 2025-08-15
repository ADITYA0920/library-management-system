package com.LMS.libraryMngSystem.domain.user.service;

import com.LMS.libraryMngSystem.domain.user.dto.RegisterUserRequest;
import com.LMS.libraryMngSystem.domain.user.entity.User;
import com.LMS.libraryMngSystem.domain.user.exception.UserAlreadyExistsException;
import com.LMS.libraryMngSystem.domain.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public String registerUser(RegisterUserRequest request) {
        String email = request.getEmail().toLowerCase();

        userRepository.findByEmail(email)
                .ifPresent(u -> {
                    throw new UserAlreadyExistsException("Email already exists: " + email);
                });

        String encryptedPassword = new BCryptPasswordEncoder().encode(request.getPassword());

        Date current = new Date();
        String name = request.getName();

        User user = User.builder()
                .name(name)
                .email(email)
                .password(encryptedPassword)
                .role(User.Role.READER)
                .created(current)
                .createdBy(name)
                .lastModified(current)
                .lastModifiedBy(name)
                .build();

        userRepository.save(user);

        return "User created successfully with id: " + user.getId();
    }

}
