package com.j2e.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull(message = "User name cannot be null")
    @NotBlank(message = "User name cannot be blank")
    private String name;

    @NotNull(message = "User email cannot be null")
    @NotBlank(message = "User email cannot be blank")
    @Email(regexp = ".+[@].+[\\.].+",message = "User email not valid")
    private String email;
}
