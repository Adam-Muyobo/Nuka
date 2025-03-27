package com.nuka.nuka_pos.api.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
