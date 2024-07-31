package com.kitten.catboard.repository;

import com.kitten.catboard.domain.entity.CommentEntity;
import com.kitten.catboard.domain.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
