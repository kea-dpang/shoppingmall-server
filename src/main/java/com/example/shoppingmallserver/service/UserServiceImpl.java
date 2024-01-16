package com.example.shoppingmallserver.service;

import com.example.shoppingmallserver.dto.UserReadDto;
import com.example.shoppingmallserver.entity.User;
import com.example.shoppingmallserver.exception.UserNotFoundException;
import com.example.shoppingmallserver.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
}