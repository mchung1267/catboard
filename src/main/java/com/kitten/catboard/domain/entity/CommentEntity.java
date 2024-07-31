package com.kitten.catboard.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name= "comments")
public class CommentEntity extends TimeEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
    private int origin;

    @Column(length = 20, nullable = false)
    private String author;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Builder
    public CommentEntity(Long id, int origin, String author, String password, String content) {
        this.id = id;
        this.origin = origin;
        this.author = author;
        this.password = password;
        this.content = content;
    }
}
