package com.sgutsev.library.models;

import jakarta.validation.constraints.NotEmpty;
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
@Table(name = "reviews")
public class Comment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "iduser")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idbook")
    private Book book;

    @NotEmpty(message = "Comment should not be empty")
    @Column(name = "comment")
    private String comment;

    @Column(name = "grade")
    private int grade;
}
