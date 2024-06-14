package com.sgutsev.library.config;

import com.sgutsev.library.dao.interfaces.UserOperations;
import com.sgutsev.library.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserOperations userOperations;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User currentUser = userOperations.findByLoginUser(login);
        if (currentUser == null) {
            throw new UsernameNotFoundException("There is no such user in the repository: " + login);
        }
        return new MyUserDetails(currentUser);
    }
}
