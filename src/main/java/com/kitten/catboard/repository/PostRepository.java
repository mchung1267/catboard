package com.kitten.catboard.repository;

import com.kitten.catboard.domain.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    List<PostEntity> findByTitleContaining(String keyword);
    List<PostEntity> findByAuthorContaining(String keyword);
}
