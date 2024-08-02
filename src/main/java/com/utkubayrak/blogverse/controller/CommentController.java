package com.utkubayrak.blogverse.controller;

import com.utkubayrak.blogverse.business.dto.response.CommentResponse;
import com.utkubayrak.blogverse.business.dto.response.PostResponse;
import com.utkubayrak.blogverse.business.services.ICommentService;
import com.utkubayrak.blogverse.business.services.IUserService;
import com.utkubayrak.blogverse.data.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comment")
public class CommentController {

    @Autowired
    private ICommentService commentService;
    @Autowired
    private IUserService userService;

    @PostMapping("/add/{postId}")
    public ResponseEntity<PostResponse> addComment(@PathVariable Long postId,
                                                   @RequestBody CommentResponse commentResponse,
                                                   @RequestHeader("Authorization") String jwt) throws Exception {
        UserEntity userEntity = userService.findUserByJwtToken(jwt);
        PostResponse postResponse = commentService.addComment(postId,commentResponse,userEntity.getId());
        return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<?> deletePost(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long commentId
    ) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        CommentResponse commentResponse = commentService.findByCommentId(commentId);

        // Kullanıcı e-posta adreslerini karşılaştırmak için equals() kullanın
        if (!user.getId().equals(commentResponse.getUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bu yorumu silmeye yetkiniz yok");
        }

        commentService.deleteComment(commentId);
        return ResponseEntity.ok().body("Yorum silinmiştir.");
    }
}
