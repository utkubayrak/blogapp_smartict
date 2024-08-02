package com.utkubayrak.blogverse.business.services.impl;

import com.utkubayrak.blogverse.business.services.IAdminService;
import com.utkubayrak.blogverse.business.services.ICommentService;
import com.utkubayrak.blogverse.business.services.IPostService;
import com.utkubayrak.blogverse.business.services.IUserService;
import com.utkubayrak.blogverse.data.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements IAdminService {
    @Autowired
    private IPostService postService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ICommentService commentService;

    @Override
    public void deletePost(Long postId) {
        postService.deletePost(postId);
    }

    @Override
    public void deleteUser(Long userId) {
        userService.deleteUser(userId);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentService.deleteComment(commentId);
    }
}
