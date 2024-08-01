package com.utkubayrak.blogverse.business.dto.response;

import com.utkubayrak.blogverse.data.entities.ERole;
import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private String message;
    private ERole role;
}
