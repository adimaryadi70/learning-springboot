package com.learning.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.model.user.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long> {}
