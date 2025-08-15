package com.LMS.libraryMngSystem.web;

import com.LMS.libraryMngSystem.domain.user.dto.RegisterUserRequest;
import com.LMS.libraryMngSystem.domain.user.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api" + UserController.PATH)
@RequiredArgsConstructor
public class UserController {

    public static final String PATH = "/user";
    public static final String REGISTER = "/register";

    private @NonNull UserService userService;

    @PostMapping(REGISTER)
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserRequest request) {
        String response = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
