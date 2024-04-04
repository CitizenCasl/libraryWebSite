package com.sgutsev.library.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Name author should not be empty")
    @Size(min = 10, max = 50, message = "Name author should be between 10 and 50 characters")
    @Pattern(regexp = "\\b[A-Z][a-z]+(?:\\s[A-Z][a-z]+)+\\b", message = "Name author should match pattern")
    @Column(name = "nameauthor")
    private String nameAuthor;

    @Column(name = "photo")
    private String photo;
}
