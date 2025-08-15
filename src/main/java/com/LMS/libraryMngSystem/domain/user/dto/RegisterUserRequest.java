package com.LMS.libraryMngSystem.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserRequest {

    private String name;
    private String email;
    private String password;

}
