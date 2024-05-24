package com.vladimir.springboot.backend.usersapp.usersbackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vladimir.springboot.backend.usersapp.usersbackend.entities.User;

public interface UserService {

    List<User> findAll();

    Page<User> findAll(Pageable pageable);

    Optional<User> findById(Long id);      //Para validar los casos NULL se agrega el optional

    User save(User user);

    void deleteById(Long id);

}
