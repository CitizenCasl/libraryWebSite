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
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name user should not be empty")
    @Size(min = 10, max = 50, message = "Name user should be between 10 and 50 characters")
    @Pattern(regexp = "\\b[A-Z][a-z]+(?:\\s[A-Z][a-z]+)+\\b", message = "Name user should match pattern")
    @Column(name = "nameuser")
    private String nameUser;

    @NotEmpty(message = "Login should not be empty")
    @Size(min = 10, max = 100, message = "Login should be between 4 and 15 characters")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Login should match pattern.Example: apetrov@gmail.com")
    @Column(name = "login", unique = true)
    private String loginUser;

    @NotEmpty(message = "Password card should not be empty")
    @Size(min = 15, max = 30, message = "Password should be between 15 and 30 characters")
    @Column(name = "password")
    private String password;

    @NotEmpty(message = "Number of user card should not be empty")
    @Pattern(regexp = "^[A-Z]{2}\\d{7}+$", message = "Number of card should match pattern.Example: HT9871235")
    @Column(name = "numbercard", unique = true)
    private String numberOfCard;

    @NotEmpty(message = "User phone should not be empty")
    @Pattern(regexp = "^\\+\\d{11}$", message = "Phone should match pattern.Example: +12152688872")
    @Column(name = "phone")
    private String phoneUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idrole")
    private Roles userRole;
}
