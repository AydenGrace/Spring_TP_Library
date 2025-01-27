package com.j2e.library.dto;

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
public class BookDto {
    @NotNull(message = "Book title cannot be null")
    @NotBlank(message = "Book title cannot be blank")
    private String title;

    @NotNull(message = "Book author cannot be null")
    @NotBlank(message = "Book author cannot be blank")
    private String author;

    private boolean isAvailable = true;
}
