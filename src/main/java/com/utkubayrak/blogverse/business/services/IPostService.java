package com.utkubayrak.blogverse.business.services;

import com.utkubayrak.blogverse.business.dto.request.PostSaveRequest;
import com.utkubayrak.blogverse.business.dto.response.PostResponse;
import com.utkubayrak.blogverse.data.entities.ECategory;
import com.utkubayrak.blogverse.data.entities.UserEntity;

import java.util.List;

public interface IPostService {

    public PostResponse createPost(PostSaveRequest postSaveRequest, UserEntity user);
    public PostResponse updatePost(Long postId, PostSaveRequest postSaveRequest);
    public void deletePost(Long postId);
    public List<PostResponse> getAllPost();
    public List<PostResponse> getPostByCategory(ECategory category);

    public PostResponse findPostById(Long id);
    public List<PostResponse> getPostByUserId(Long userId);
}
