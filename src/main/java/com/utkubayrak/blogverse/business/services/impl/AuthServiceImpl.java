package com.utkubayrak.blogverse.business.services.impl;

import com.utkubayrak.blogverse.business.dto.request.LoginRequest;
import com.utkubayrak.blogverse.business.dto.response.AuthResponse;
import com.utkubayrak.blogverse.business.services.IAuthService;
import com.utkubayrak.blogverse.config.JwtProvider;
import com.utkubayrak.blogverse.data.entities.ERole;
import com.utkubayrak.blogverse.data.entities.UserEntity;
import com.utkubayrak.blogverse.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AuthServiceImpl implements IAuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Override
    public AuthResponse registerUser(UserEntity userEntity) throws Exception {
        UserEntity isEmailExist = userRepository.findByEmail(userEntity.getEmail());

        if (isEmailExist != null) {
            throw new Exception("Email is already used with another account");
        }
        UserEntity createdUser = new UserEntity();
        createdUser.setEmail(userEntity.getEmail());
        createdUser.setFullName(userEntity.getFullName());
        createdUser.setRole(userEntity.getRole());
        createdUser.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        UserEntity savedUser = userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userEntity.getEmail(),userEntity.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Register succes");
        authResponse.setRole(savedUser.getRole());

        return authResponse;
    }
    @Override
    public AuthResponse loginUser(LoginRequest loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username, password);
        Collection<?extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty()?null:authorities.iterator().next().getAuthority();
        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login succes");
        authResponse.setRole(ERole.valueOf(role));
        return authResponse;
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username...");
        }
        if (!passwordEncoder.matches(password,userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password...");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
