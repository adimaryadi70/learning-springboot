package com.learning.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learning.interfaces.UserRepository;
import com.learning.model.user.UserModel;
import com.learning.services.UserServices;
import com.learning.tools.Utility;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    Utility utility = new Utility();
    
    @Autowired
    private UserServices userServices;
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public UserModel createUser(@RequestBody UserModel user) {
        return userServices.createUser(user);
    }

    // @GetMapping
    // public List<UserModel> getAllUser() {
    //     return userServices.getAllUser();
    // }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getUserPagination(@RequestParam(required = false) String name, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "asc") String[] sort) {
        try {
            List<UserModel> users = new ArrayList<UserModel>();
            Pageable paging = PageRequest.of(page-1, size);
            if (sort[0].equals("asc")) {
                paging = PageRequest.of(page-1, size, Sort.by(Sort.Order.asc("name")));
            }
            if (sort[0].equals("desc")) {
                paging = PageRequest.of(page-1, size, Sort.by(Sort.Order.desc("name")));
            }

            Page<UserModel> pagination;

            if (name == null) {
                pagination = userRepository.findAll(paging);
            } else {
                pagination = userRepository.findByNameContaining(name, paging);
            }

            users = pagination.getContent();
            return new ResponseEntity<>(utility.responseSuccesGet(users,pagination),HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(utility.responseError(e),HttpStatus.INTERNAL_SERVER_ERROR);
        }
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