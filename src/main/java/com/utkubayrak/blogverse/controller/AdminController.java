package com.utkubayrak.blogverse.controller;

import com.utkubayrak.blogverse.business.services.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<?> adminDeletePost(@PathVariable Long postId){
        adminService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> adminDeleteUser(@PathVariable Long userId){
        adminService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<?> adminDeleteComment(@PathVariable Long commentId){
        adminService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
