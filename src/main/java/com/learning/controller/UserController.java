package com.learning.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.model.user.UserModel;
import com.learning.services.UserServices;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserServices userServices;

    @PostMapping
    public UserModel createUser(@RequestBody UserModel user) {
        return userServices.createUser(user);
    }

    @GetMapping
    public List<UserModel> getAllUser() {
        return userServices.getAllUser();
    }

    @GetMapping("/{id}")
    public Optional<UserModel> getUserById(@PathVariable Long id) {
        return userServices.getUserById(id);
    }

    @PutMapping("/{id}")
    public UserModel updateUser(@PathVariable Long id, @RequestBody UserModel userData) {
        return userServices.updateUser(id, userData);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userServices.deleteUser(id);
    }
}