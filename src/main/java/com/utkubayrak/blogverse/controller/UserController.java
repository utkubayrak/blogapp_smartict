package com.utkubayrak.blogverse.controller;

import com.utkubayrak.blogverse.business.services.IUserService;
import com.utkubayrak.blogverse.data.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserEntity> findUserByJwtToken(@RequestHeader("Authorization") String jwt) throws Exception {
        UserEntity userEntity = userService.findUserByJwtToken(jwt);
        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String jwt) throws Exception {
        UserEntity userEntity = userService.findUserByJwtToken(jwt);
        userService.deleteUser(userEntity.getId());
        return ResponseEntity.ok().body("Hesabınız silinmiştir.");
    }
}
