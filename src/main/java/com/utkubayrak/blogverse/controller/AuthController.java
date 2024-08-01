package com.utkubayrak.blogverse.controller;

import com.utkubayrak.blogverse.business.services.IAuthService;
import com.utkubayrak.blogverse.business.services.impl.UserDetailsServiceImpl;
import com.utkubayrak.blogverse.config.JwtProvider;
import com.utkubayrak.blogverse.data.entities.ERole;
import com.utkubayrak.blogverse.data.entities.UserEntity;
import com.utkubayrak.blogverse.data.repository.UserRepository;
import com.utkubayrak.blogverse.business.dto.request.LoginRequest;
import com.utkubayrak.blogverse.business.dto.response.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private IAuthService authService;
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody UserEntity userEntity) throws Exception {
        return new ResponseEntity<>(authService.registerUser(userEntity), HttpStatus.CREATED);
    }
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest loginRequest){
        return new ResponseEntity<>(authService.loginUser(loginRequest), HttpStatus.OK);
    }
}
