package com.sgutsev.library.dao.interfaces;

import com.sgutsev.library.models.Roles;

import java.util.List;

public interface RolesOperation {
    List<Roles> index();

    Roles showByName(String name);

}
