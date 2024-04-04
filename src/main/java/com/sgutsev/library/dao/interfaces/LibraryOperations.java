package com.sgutsev.library.dao.interfaces;

import com.sgutsev.library.models.Library;

import java.util.List;

public interface LibraryOperations {

    List<Library> index();

    void save(Library library);

    void delete(int id);

    void update(int id, Library library);

}
