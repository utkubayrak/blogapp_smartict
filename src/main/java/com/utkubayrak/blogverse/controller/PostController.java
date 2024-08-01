package com.utkubayrak.blogverse.controller;

import com.utkubayrak.blogverse.business.services.IPostService;
import com.utkubayrak.blogverse.business.services.IUserService;
import com.utkubayrak.blogverse.business.dto.request.PostSaveRequest;
import com.utkubayrak.blogverse.business.dto.response.PostResponse;
import com.utkubayrak.blogverse.data.entities.ECategory;
import com.utkubayrak.blogverse.data.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private IPostService postService;
    @Autowired
    private IUserService userService;



    @PostMapping()
    public ResponseEntity<PostResponse> createPost(
            @RequestBody PostSaveRequest postSaveRequest,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{
        UserEntity userEntity = userService.findUserByJwtToken(jwt);
        PostResponse postResponse = postService.createPost(postSaveRequest, userEntity);
        return ResponseEntity.ok(postResponse);
    }
    @GetMapping("find/{id}")
    public ResponseEntity<?> getPostById(
            @PathVariable Long id
    ){
        PostResponse postResponse = postService.findPostById(id);
        return ResponseEntity.ok(postResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePost(
            @RequestBody PostSaveRequest postSaveRequest,
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        UserEntity userEntity = userService.findUserByJwtToken(jwt);
        PostResponse postResponse = postService.findPostById(id);

        if (!userEntity.getId().equals(postResponse.getUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bu postu güncelleme yetkiniz yok");

        }
        postResponse = postService.updatePost(id, postSaveRequest);
        return ResponseEntity.ok(postResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePost(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        PostResponse postResponse = postService.findPostById(id);

        // Kullanıcı e-posta adreslerini karşılaştırmak için equals() kullanın
        if (!user.getId().equals(postResponse.getUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bu postu silmeye yetkiniz yok");
        }

        postService.deletePost(id);
        return ResponseEntity.ok().body("Post silinmiştir.");
    }
    @GetMapping("category/{name}")
    public ResponseEntity<?> getPostsByCategory(
            @PathVariable String name
    ){
        String formattedName = name.toUpperCase();


        // Eğer içinde boşluk varsa boşluk yerine _ işareti koy
        List<PostResponse> postResponses = postService.getPostByCategory(ECategory.valueOf(formattedName));

        return ResponseEntity.ok(postResponses);
    }
    @GetMapping("user/{id}")
    public ResponseEntity<?> getPostsByUserId(
            @PathVariable Long id
    ){
        List<PostResponse> postResponses = postService.getPostByUserId(id);
        return ResponseEntity.ok(postResponses);
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllPost(){
        List<PostResponse> posts = postService.getAllPost();

        return ResponseEntity.ok(posts);
    }
/*


    @GetMapping("category/{name}")
    public ResponseEntity<?> getPostByCategory(
            @PathVariable String name
    ){
        String formattedName = name.toUpperCase();

        // Eğer içinde boşluk varsa boşluk yerine _ işareti koy
        List<PostEntity> posts = postService.findByCategoryContaining(ECategory.valueOf(formattedName));

        return ResponseEntity.ok(posts);
    }
    @GetMapping("find/{id}")
    public ResponseEntity<?> getPostById(
            @PathVariable Long id
    ){
        PostEntity post = postService.findPostById(id);
        return ResponseEntity.ok(post);
    }


     */
}
