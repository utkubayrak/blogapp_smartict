package com.utkubayrak.blogverse.business.services.impl;

import com.utkubayrak.blogverse.business.services.ICommentService;
import com.utkubayrak.blogverse.business.services.IPostService;
import com.utkubayrak.blogverse.business.services.IUserService;
import com.utkubayrak.blogverse.config.JwtProvider;
import com.utkubayrak.blogverse.data.entities.CommentEntity;
import com.utkubayrak.blogverse.data.entities.PostEntity;
import com.utkubayrak.blogverse.data.entities.UserEntity;
import com.utkubayrak.blogverse.data.repository.CommentRepository;
import com.utkubayrak.blogverse.data.repository.PostRepository;
import com.utkubayrak.blogverse.data.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IPostService postService;

    @Autowired
    private JwtProvider jwtProvider;
    @Override
    public UserEntity findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        UserEntity userEntity = findUserByEmail(email);
        return userEntity;
    }

    @Override
    public UserEntity findUserByEmail(String email) throws Exception {

        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new Exception("user not found");
        }
        return userEntity;
    }

    @Override
    public List<UserEntity> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        Optional<UserEntity> userEntityOpt = userRepository.findById(id);
        if (!userEntityOpt.isPresent()) {
            throw new RuntimeException("User not found with id: " + id);
        }
        UserEntity userEntity = userEntityOpt.get();
        // Kullanıcının sahip olduğu tüm gönderileri alın
        List<PostEntity> posts = postRepository.findByUserId(id);
        for (PostEntity postEntity : posts) {
            // Gönderiye ait tüm yorumları silin
            List<CommentEntity> comments = commentRepository.findByPost_id(postEntity.getId());
            for (CommentEntity commentEntity : comments){
                commentService.deleteComment(commentEntity.getId());
            }
            // Gönderiyi veritabanından silin
            postService.deletePost(postEntity.getId());
        }
        // Kullanıcıyı veritabanından silin
        userRepository.deleteById(id);
    }

    @Override
    public UserEntity updateUser(UserEntity userEntity) {
        return null;
    }
}
