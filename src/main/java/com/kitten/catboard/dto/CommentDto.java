package com.kitten.catboard.dto;

import com.kitten.catboard.domain.entity.CommentEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private int origin;
    private String author;
    private String password;
    private String content;
    private LocalDateTime date;

    public CommentEntity toEntity() {
        CommentEntity commentEntity = CommentEntity.builder()
                .id(id)
                .origin(origin)
                .author(author)
                .password(password)
                .content(content)
                .build();
        return commentEntity;
    }
    @Builder
    public CommentDto(Long id, int origin, String author, String password, String content, LocalDateTime date) {
        this.id = id;
        this.author = author;
        this.origin = origin;
        this.password = password;
        this.content = content;
        this.date = date;
    }
}
