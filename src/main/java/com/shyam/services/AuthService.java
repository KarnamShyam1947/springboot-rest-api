package com.shyam.services;

import com.shyam.config.custom.MyUserDetails;
import com.shyam.dto.request.LoginRequest;
import com.shyam.dto.request.UserPasswordRequest;
import com.shyam.dto.request.UserRequest;
import com.shyam.entities.UserEntity;
import com.shyam.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
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

    public UserEntity setPassword(UserPasswordRequest request) {
        UserEntity user = userRepository.findByUniqueToken(request.getToken());
        
        if (user == null) {
            log.error("Invalid token. Please check the token");
            throw new RuntimeException("Invalid token. Please check the token");
        }
        
        else if (user.getExpirationTime().isBefore(LocalDateTime.now())) {
            log.error("Link expired contact admin for new link");
            throw new RuntimeException("Link expired contact admin for new link");
        }

        else
            user.setUniqueToken(null);
            user.setExpirationTime(null);
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            return userRepository.save(user);
        
    }

    public UserEntity getCurrentUser() {
        MyUserDetails user = ((MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return user.getUserEntity();
    }

}
