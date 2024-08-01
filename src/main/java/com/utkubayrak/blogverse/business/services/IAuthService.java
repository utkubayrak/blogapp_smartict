package com.utkubayrak.blogverse.business.services;

import com.utkubayrak.blogverse.business.dto.request.LoginRequest;
import com.utkubayrak.blogverse.business.dto.response.AuthResponse;
import com.utkubayrak.blogverse.data.entities.UserEntity;

public interface IAuthService {

    public AuthResponse registerUser(UserEntity userEntity) throws Exception;

    public AuthResponse loginUser(LoginRequest loginRequest);
}
