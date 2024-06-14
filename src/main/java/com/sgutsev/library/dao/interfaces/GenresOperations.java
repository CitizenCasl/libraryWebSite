package com.sgutsev.library.dao.interfaces;

import com.sgutsev.library.models.Genres;

import java.util.List;

public interface GenresOperations {
    List<Genres> index();

    Genres getGenre(String genreName);
}
