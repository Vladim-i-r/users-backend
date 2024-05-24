package com.vladimir.springboot.backend.usersapp.usersbackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vladimir.springboot.backend.usersapp.usersbackend.entities.User;
import com.vladimir.springboot.backend.usersapp.usersbackend.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository repository;

    public UserServiceImpl(UserRepository repository) { /// se puede inyectar mediante el constructor como este caso, o con el @Autowired en la variable
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true) //Cada transaccion es individual por usuario
    public List<User> findAll() {
        return (List<User>) this.repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return this.repository.findById(id);
    }

    @Transactional
    @Override
    public User save(User user) {
       return this.repository.save(user);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        this.repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<User> findAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

}
