package com.javatech.javamail.dtos;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class RegisterDto {
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String password;
}
