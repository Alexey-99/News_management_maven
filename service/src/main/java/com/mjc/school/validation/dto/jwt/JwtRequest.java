package com.mjc.school.validation.dto.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Validated
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest {
    @NotNull
    @NotBlank
    @Size(min = 3)
    private String userName;
    @NotNull
    @NotBlank
    @Size(min = 3)
    private String password;
}