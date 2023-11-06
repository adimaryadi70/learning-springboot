package com.learning.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.interfaces.UserRepository;
import com.learning.model.user.UserModel;

@Service
public class UserServices {
    
    @Autowired
    private UserRepository userRepository;

    public UserModel createUser(UserModel user) {
        return userRepository.save(user);
    }

    public List<UserModel> getAllUser() {
        return userRepository.findAll();
    }

    public Optional<UserModel> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public UserModel updateUser(Long id,UserModel userData) {
        Optional<UserModel> user = userRepository.findById(id);
        if (user.isPresent()) {
            UserModel data = user.get();
            data.setName(userData.getName());
            data.setEmail(userData.getEmail());
            return userRepository.save(data);
        }
        return null;
    }

    public void deleteAllUser() {
        userRepository.deleteAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
