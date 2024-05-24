package com.vladimir.springboot.backend.usersapp.usersbackend.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.vladimir.springboot.backend.usersapp.usersbackend.entities.User;

////No se coloca @Repository ya que CrudRepository es un componente y se esta heredando
public interface UserRepository extends CrudRepository<User, Long>{

    ////Aqui se pueden declarar @Query personalizados para consultas mas detalladas

    Page<User> findAll(Pageable pageable); 

    Optional<User> findByUsername(String name);
}
