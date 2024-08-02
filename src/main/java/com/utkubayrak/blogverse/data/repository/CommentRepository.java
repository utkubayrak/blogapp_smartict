package com.utkubayrak.blogverse.data.repository;

import com.utkubayrak.blogverse.data.entities.CommentEntity;
import com.utkubayrak.blogverse.data.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByPost_id(Long postId);

}
