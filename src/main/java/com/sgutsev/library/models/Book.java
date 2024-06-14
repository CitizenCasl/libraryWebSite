package com.sgutsev.library.models;

import com.sgutsev.library.dao.CommentDAO;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.util.Base64;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "books")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idauthor")
    private Author nameAuthorOfBook;

    @NotEmpty(message = "Name book should not be empty")
    @Size(min = 1, max = 150, message = "Name book should be between 1 and 150 characters")
    @Column(name = "namebook")
    private String nameBook;

    @Max(value = 2024, message = "Year release shouldn't be more than current year")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @Positive(message = "Year release should be positive")
    @Column(name = "yearrelease")
    private int yearRelease;

    @NotEmpty(message = "Book description should not be empty")
    @Column(name = "description")
    private String description;

    @Column(name = "cover")
    private byte[] cover;

    public static String getBase64Image(byte[] photo) {
        return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(photo);
    }

    public Double getBookRating() {
        CommentDAO commentDAO = new CommentDAO();
        return commentDAO.getBookRating(this);
    }

}
