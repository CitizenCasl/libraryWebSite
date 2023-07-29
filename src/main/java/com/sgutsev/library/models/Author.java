package com.sgutsev.library.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Author {
    private int id;
    @NotEmpty(message = "Name author should not be empty")
    @Size(min = 10, max = 50, message = "Name author should be between 10 and 50 characters")
    private String nameAuthor;
}
