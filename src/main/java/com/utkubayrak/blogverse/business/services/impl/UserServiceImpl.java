package com.utkubayrak.blogverse.business.services.impl;

import com.utkubayrak.blogverse.business.services.IUserService;
import com.utkubayrak.blogverse.config.JwtProvider;
import com.utkubayrak.blogverse.data.entities.UserEntity;
import com.utkubayrak.blogverse.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;
    @Override
    public UserEntity findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        UserEntity userEntity = findUserByEmail(email);
        return userEntity;
    }

    @Override
    public UserEntity findUserByEmail(String email) throws Exception {

        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new Exception("user not found");
        }
        return userEntity;
    }
}
