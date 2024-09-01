package com.fundriser.repos;

import com.fundriser.models.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepo extends MongoRepository<UserModel, String> {
    UserModel findByEmail(String email);
    List<UserModel> findAll();
}
