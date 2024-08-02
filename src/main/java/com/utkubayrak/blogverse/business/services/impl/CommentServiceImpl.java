package com.utkubayrak.blogverse.business.services.impl;

import com.utkubayrak.blogverse.business.dto.response.CommentResponse;
import com.utkubayrak.blogverse.business.dto.response.PostResponse;
import com.utkubayrak.blogverse.business.services.ICommentService;
import com.utkubayrak.blogverse.converter.CommentConverter;
import com.utkubayrak.blogverse.converter.PostConverter;
import com.utkubayrak.blogverse.data.entities.CommentEntity;
import com.utkubayrak.blogverse.data.entities.PostEntity;
import com.utkubayrak.blogverse.data.entities.UserEntity;
import com.utkubayrak.blogverse.data.repository.CommentRepository;
import com.utkubayrak.blogverse.data.repository.PostRepository;
import com.utkubayrak.blogverse.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements ICommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Override
    public PostResponse addComment(Long postId, CommentResponse commentResponse, Long userId) {
        Optional<PostEntity> postEntityOpt = postRepository.findById(postId);
        PostEntity postEntity = postEntityOpt.get();
        Optional<UserEntity> userEntityOpt = userRepository.findById(userId);
        UserEntity userEntity = userEntityOpt.get();
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentContent(commentResponse.getContent());
        commentEntity.setPost(postEntity);
        commentEntity.setUser(userEntity);
        userEntity.getComments().add(commentEntity);
        postEntity.getComments().add(commentEntity);
        commentRepository.save(commentEntity);
        return PostConverter.toResponse(postEntity);
    }
    @Override
    public PostResponse deleteComment(Long commentId) {
        Optional<CommentEntity> commentEntityOpt = commentRepository.findById(commentId);
        CommentEntity commentEntity = commentEntityOpt.get();
        if (commentEntity == null) {
            throw new RuntimeException("Comment not found");
        }
        PostEntity postEntity = commentEntity.getPost();
        UserEntity userEntity = commentEntity.getUser();
        postEntity.getComments().remove(commentEntity);
        userEntity.getComments().remove(commentEntity);
        commentRepository.deleteById(commentId);
        return PostConverter.toResponse(postEntity);
    }

    @Override
    public CommentResponse findByCommentId(Long commentId) {
        Optional<CommentEntity> commentEntityOpt = commentRepository.findById(commentId);
        CommentEntity commentEntity = commentEntityOpt.get();

        return CommentConverter.toResponse(commentEntity);
    }
}
