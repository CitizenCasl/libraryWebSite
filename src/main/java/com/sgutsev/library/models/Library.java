package com.sgutsev.library.models;

import jakarta.validation.constraints.NotEmpty;
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
@Table(name = "library_info")
public class Library {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Library name should not be empty")
    @Size(min = 10, max = 150, message = "Library name should be between 10 and 50 characters")
    @Column(name = "name")
    private String nameLibrary;

    @NotEmpty(message = "Library address should not be empty")
    @Size(min = 10, max = 100, message = "Library address should be between 10 and 100 characters")
    @Column(name = "address")
    private String addressLibrary;

    @NotEmpty(message = "Library phone should not be empty")
    @Size(min = 4, max = 15, message = "Library phone should be between 4 and 15 characters")
    @Column(name = "phone")
    private String phoneLibrary;

}
