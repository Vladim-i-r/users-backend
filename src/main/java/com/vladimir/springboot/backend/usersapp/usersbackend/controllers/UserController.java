package com.vladimir.springboot.backend.usersapp.usersbackend.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vladimir.springboot.backend.usersapp.usersbackend.entities.User;
import com.vladimir.springboot.backend.usersapp.usersbackend.services.UserService;

import jakarta.validation.Valid;

// @CrossOrigin(originPatterns = {"*"})
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<User> list() {
        return service.findAll();
    }

    @GetMapping("/page/{page}")
    public Page<User> listPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Optional<User> optUser = service.findById(id);
        if (optUser.isPresent()) {
            // return ResponseEntity.ok(optUser.orElseThrow()); /// 200
            return ResponseEntity.status(HttpStatus.OK).body(optUser.orElseThrow()); /// 200
        }
        // return ResponseEntity.notFound().build();    //404
        return ResponseEntity.status(404).body(Collections.singletonMap("error", "El usuario no se encontro por el id:" + id));    //404
                                //(HttpStatus.NOT_FOUND).body...      
    }
    
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody User user, BindingResult result, @PathVariable Long id){

        if (result.hasErrors()) {
            return validation(result);
        }

        Optional<User> userOpt = service.findById(id);
        if (userOpt.isPresent()) {
        User userDB = userOpt.get();
        userDB.setEmail(user.getEmail()); 
        userDB.setLastname(user.getLastname());
        userDB.setName(user.getName());
        userDB.setUsername(user.getUsername());
        userDB.setPassword(user.getPassword());
        return ResponseEntity.ok(service.save(userDB));
        }
        return ResponseEntity.notFound().build();
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Optional<User> userOpt = service.findById(id);
        if (userOpt.isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
           errors.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}







