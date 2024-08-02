package com.utkubayrak.blogverse.business.services;

import com.utkubayrak.blogverse.business.dto.response.CommentResponse;
import com.utkubayrak.blogverse.business.dto.response.PostResponse;
import com.utkubayrak.blogverse.data.entities.PostEntity;
import com.utkubayrak.blogverse.data.entities.UserEntity;

public interface ICommentService {
    public PostResponse addComment(Long postId, CommentResponse commentResponse, Long userId);
    public PostResponse deleteComment(Long commentId);
    public CommentResponse findByCommentId(Long commentId);
}
