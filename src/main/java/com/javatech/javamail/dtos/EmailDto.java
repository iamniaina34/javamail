package com.javatech.javamail.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {
    @NotNull
    private String to;
    private String subject;
    private String text;
}
