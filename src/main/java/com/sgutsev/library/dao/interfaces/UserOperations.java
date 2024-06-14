package com.sgutsev.library.dao.interfaces;

import com.sgutsev.library.models.User;


public interface UserOperations {
    User findByLoginUser(String userLogin);

    void save(User user);

    void updateInfo(int id, User user);

    void updatePassword(int id, User user);
}
