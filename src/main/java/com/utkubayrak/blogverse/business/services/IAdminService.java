package com.utkubayrak.blogverse.business.services;

public interface IAdminService {

    public void deletePost(Long postId);
    public void deleteUser(Long userId);
    public void deleteComment(Long commentId);

}
