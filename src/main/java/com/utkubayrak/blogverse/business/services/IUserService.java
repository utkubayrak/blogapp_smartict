package com.utkubayrak.blogverse.business.services;

import com.utkubayrak.blogverse.business.dto.response.AuthResponse;
import com.utkubayrak.blogverse.data.entities.UserEntity;

import java.util.List;

public interface IUserService {
    public UserEntity findUserByJwtToken(String jwt) throws Exception;

    public UserEntity findUserByEmail(String email) throws Exception;

    public List<UserEntity> getAllUser();

    public void deleteUser(Long id);

    public UserEntity updateUser(UserEntity userEntity);


}
