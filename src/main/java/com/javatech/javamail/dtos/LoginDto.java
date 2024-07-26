package com.javatech.javamail.dtos;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class LoginDto {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
