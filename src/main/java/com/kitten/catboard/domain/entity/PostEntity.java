package com.kitten.catboard.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name= "posts")
public class PostEntity extends TimeEntity{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String author;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column
    private int viewcount;

    @Builder
    public PostEntity(Long id, String title, String author, String password, String content, int viewcount) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.password = password;
        this.content = content;
        this.viewcount = viewcount;
    }
}
