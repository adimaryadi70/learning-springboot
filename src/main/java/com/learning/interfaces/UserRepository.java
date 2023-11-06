package com.learning.interfaces;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import com.learning.model.user.UserModel;
import java.util.List;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Page<UserModel> findByNameContaining(String name, Pageable pageable);
    List<UserModel> findByNameContaining(String name, Sort sort);
}