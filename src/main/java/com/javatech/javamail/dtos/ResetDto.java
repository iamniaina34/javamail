package com.javatech.javamail.dtos;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class ResetDto {
    @NotNull
    private String email;
    /**
     * The client endpoint which will handle password resetting
     */
    @NotNull
    private String clientAddress;
}
