package com.learning.TodoList.repositories;

import com.learning.TodoList.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);

    void deleteByEmail(String email);
}
