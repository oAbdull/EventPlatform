package org.example.userservice.repo;

import org.example.userservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUserid(String userid);
    Optional<User> findByRole(String role);
}