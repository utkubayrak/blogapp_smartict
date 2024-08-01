package com.utkubayrak.blogverse.data.repository;

import com.utkubayrak.blogverse.data.entities.ECategory;
import com.utkubayrak.blogverse.data.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity,Long> {

    Optional<PostEntity> findById(Long id);
    List<PostEntity> findByCategoriesContaining(ECategory category);

    List<PostEntity> findByUserId(Long userId);
}
