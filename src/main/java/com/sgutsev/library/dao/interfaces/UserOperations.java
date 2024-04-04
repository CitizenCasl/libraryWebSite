package com.sgutsev.library.dao.interfaces;

import com.sgutsev.library.models.User;

import java.util.List;
import java.util.Optional;

public interface UserOperations {
    Optional<User> findByLoginUser(String userLogin);
}
