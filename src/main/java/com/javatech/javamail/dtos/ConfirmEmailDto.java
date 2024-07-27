package com.javatech.javamail.dtos;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class ConfirmEmailDto {
    @NotNull
    private String from;
    @NotNull
    private String pinCode;
}
