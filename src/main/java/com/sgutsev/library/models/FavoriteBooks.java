package com.sgutsev.library.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Base64;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "favorite_books")
public class FavoriteBooks {
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

    public static String getBase64Image(byte[] photo) {
        return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(photo);
    }
}
