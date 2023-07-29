package com.sgutsev.library.models;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Book {
    private int id;
    private String nameAuthorOfBook;
    @NotEmpty(message = "Name book should not be empty")
    @Size(min = 1, max = 150, message = "Name book should be between 1 and 150 characters")
    private String nameBook;
    @Max(value = 2024, message = "Year release shouldn't be more than current year")
    private int yearRelease;
    @Min(value = 1, message = "Amount must be a positive number")
    @NotNull(message = "Amount cannot be empty")
    private int amount;
}
