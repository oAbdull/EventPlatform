package org.example.userservice.service;

import org.example.userservice.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    void saveuser(User user) throws Exception;
    long count();
    Optional<User> findByUserId(String userid);
    User getAuthenticatedUser();
    List<User> listall();
}