package com.learning.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import com.learning.tools.Cryptograpy;
import com.learning.tools.Utility;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    Utility utility = new Utility();
    Cryptograpy crypto = new Cryptograpy();
    
    @Autowired
    private UserServices userServices;
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody UserModel user) {
        try {
            user.setUsername(utility.generateUser(user.getName()));
            user.setPassword(crypto.sha256(user.getPassword()));
            var result = userServices.createUser(user);
            ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(utility.responseSuccess(result), HttpStatus.OK);            
            return responseEntity;
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(utility.responseError(e),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // return userServices.createUser(user);
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
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Long id) {
        try {
            var userDetail = userServices.getUserById(id);
            ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(utility.responseSuccess(userDetail), HttpStatus.OK);
            if (userDetail.isEmpty()) {
                return new ResponseEntity<>(utility.responseInfo("Data Tidak ditemukan Tidak di temukan"),HttpStatus.OK);
            }
            return responseEntity;
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(utility.responseError(e),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // return userServices.getUserById(id);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @RequestBody UserModel userData) {
        try {
            var updateUser =  userServices.updateUser(id, userData);
            ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(utility.responseSuccess(updateUser), HttpStatus.OK);
            
            if (updateUser == null) {
                return new ResponseEntity<>(utility.responseInfo("Data Perbaharui Tidak di temukan"),HttpStatus.OK);
            }
            return responseEntity;
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(utility.responseError(e),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // return userServices.updateUser(id, userData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id) {
        try {
            Map<String, Object> data = new HashMap<>();
            userServices.deleteUser(id);
            data.put("status","Deleted");
            ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(utility.responseSuccess(data), HttpStatus.OK);
            return responseEntity;
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(utility.responseError(e),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // userServices.deleteUser(id);
    }
}