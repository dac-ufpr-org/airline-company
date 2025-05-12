package com.msauth.ms_auth.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.msauth.ms_auth.model.User;

public interface UserRepository extends MongoRepository<User, String> {

    User findByLogin(String login);
}
