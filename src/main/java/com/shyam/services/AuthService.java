package com.shyam.services;

import com.shyam.dto.request.LoginRequest;
import com.shyam.dto.request.UserRequest;
import com.shyam.entities.UserEntity;
import com.shyam.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserEntity registerUser(UserRequest user) {
        UserEntity userEntity = userRepository.findByEmail(user.getEmail());

        if(userEntity != null)
            return null;

        UserEntity newUser = modelMapper.map(user, UserEntity.class);
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(newUser);
    }

    public UserEntity loginUser(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        return userRepository.findByEmail(loginRequest.getUsername());

    }

    public UserEntity getUserById(int userId) {
        return userRepository.findById(userId);
    }

}
