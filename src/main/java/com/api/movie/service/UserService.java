package com.api.movie.service;

import com.api.movie.component.JwtUtil;
import com.api.movie.dto.LoginRequestDto;
import com.api.movie.entity.User;
import com.api.movie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // for hashing passwords

    @Autowired
    private JwtUtil jwtUtil; // utility for JWT generation

    public User signup(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public String login(LoginRequestDto req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println(user.getPassword());
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return jwtUtil.generateToken(user.getEmail());
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, User updated) {
        User user = getUserById(id);
        user.setName(updated.getName());
        user.setEmail(updated.getEmail());
        if (updated.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(updated.getPassword()));
        }
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

