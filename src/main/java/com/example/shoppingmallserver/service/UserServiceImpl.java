package com.example.shoppingmallserver.service;

import com.example.shoppingmallserver.dto.UserReadDto;
import com.example.shoppingmallserver.entity.User;
import com.example.shoppingmallserver.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserReadDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 회원이 존재하지 않습니다."));
        UserReadDto userDto = new UserReadDto(user);
        return userDto;
    }
}