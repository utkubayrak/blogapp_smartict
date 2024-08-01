package com.utkubayrak.blogverse.business.services;

import com.utkubayrak.blogverse.data.entities.UserEntity;

public interface IUserService {
    public UserEntity findUserByJwtToken(String jwt) throws Exception;

    public UserEntity findUserByEmail(String email) throws Exception;
}
