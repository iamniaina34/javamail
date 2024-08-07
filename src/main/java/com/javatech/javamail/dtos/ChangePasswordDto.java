package com.javatech.javamail.dtos;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class ChangePasswordDto {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
