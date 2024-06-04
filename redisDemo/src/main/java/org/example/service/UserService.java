package org.example.service;

import org.example.entity.User;

public interface UserService {

    User saveOrUpdate(User user);

    User get(Long id);

    void delete(Long id);
}
