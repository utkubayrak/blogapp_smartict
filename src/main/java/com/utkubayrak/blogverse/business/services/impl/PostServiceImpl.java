package com.utkubayrak.blogverse.business.services.impl;

import com.utkubayrak.blogverse.business.services.ICommentService;
import com.utkubayrak.blogverse.business.services.IPostService;
import com.utkubayrak.blogverse.business.dto.request.PostSaveRequest;
import com.utkubayrak.blogverse.business.dto.response.PostResponse;
import com.utkubayrak.blogverse.converter.PostConverter;
import com.utkubayrak.blogverse.data.entities.CommentEntity;
import com.utkubayrak.blogverse.data.entities.ECategory;
import com.utkubayrak.blogverse.data.entities.PostEntity;
import com.utkubayrak.blogverse.data.entities.UserEntity;
import com.utkubayrak.blogverse.data.repository.CommentRepository;
import com.utkubayrak.blogverse.data.repository.PostRepository;
import com.utkubayrak.blogverse.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements IPostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ICommentService commentService;

    @Override
    public PostResponse createPost(PostSaveRequest postSaveRequest, UserEntity user) {
        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(postSaveRequest.getTitle());
        postEntity.setContent(postSaveRequest.getContent());
        postEntity.setCategories(postSaveRequest.getCategories());
        postEntity.setUser(user);
        user.getPosts().add(postEntity);
        postRepository.save(postEntity);
        return PostConverter.toResponse(postEntity);
    }

    @Override
    public PostResponse updatePost(Long postId, PostSaveRequest postSaveRequest) {
        Optional<PostEntity> postEntityOptional = postRepository.findById(postId);
        PostEntity postEntity = postEntityOptional.get();
        postEntity.setTitle(postSaveRequest.getTitle());
        postEntity.setContent(postSaveRequest.getContent());
        postEntity.setCategories(postSaveRequest.getCategories());
        postRepository.save(postEntity);
        return PostConverter.toResponse(postEntity);
    }
    @Override
    public PostResponse findPostById(Long id) {
        Optional<PostEntity> postEntityOptional = postRepository.findById(id);
        PostEntity postEntity = postEntityOptional.get();
        return PostConverter.toResponse(postEntity);
    }
    @Override
    public void deletePost(Long postId) {
        Optional<PostEntity> postEntityOptional = postRepository.findById(postId);
        if (!postEntityOptional.isPresent()) {
            throw new RuntimeException("Post not found with id: " + postId);
        }
        PostEntity postEntity = postEntityOptional.get();
        List<CommentEntity> comments = commentRepository.findByPost_id(postId);
        for (CommentEntity commentEntity : comments){
            UserEntity userEntity = commentEntity.getUser();
            if (userEntity != null) {
                userEntity.getComments().remove(commentEntity);
                userRepository.save(userEntity);
            }
            PostEntity postEntity1 = commentEntity.getPost();
            postEntity1.getComments().remove(commentEntity);
            postRepository.save(postEntity);
        }
        commentRepository.deleteAll(comments);
        UserEntity userEntity = postEntity.getUser();
        if (userEntity != null) {
            userEntity.getPosts().remove(postEntity);
            userRepository.save(userEntity);
        }
        postRepository.delete(postEntity);
    }
    @Override
    public List<PostResponse> getAllPost() {
        List<PostEntity> postEntities = postRepository.findAll();
        List<PostResponse> postResponses = PostConverter.toResponseList(postEntities);
        return postResponses;
    }
    @Override
    public List<PostResponse> getPostByCategory(ECategory category) {
        List<PostEntity> postEntities = postRepository.findByCategoriesContaining(category);
        List<PostResponse> postResponses = PostConverter.toResponseList(postEntities);
        return postResponses;
    }
    @Override
    public List<PostResponse> getPostByUserId(Long userId) {
        List<PostEntity> postEntities = postRepository.findByUserId(userId);
        List<PostResponse> postResponses = PostConverter.toResponseList(postEntities);
        return postResponses;
    }
}
