package com.fundriser.service;

import com.fundriser.models.UserModel;
import com.fundriser.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public UserModel findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public UserModel saveUser(UserModel user) {
        return userRepo.save(user);
    }
    public List<UserModel> findAllUsers() {
        return userRepo.findAll();
    }
}
