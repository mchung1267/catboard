package com.kitten.catboard.dto;

import com.kitten.catboard.domain.entity.PostEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String author;
    private String title;
    private String password;
    private String content;
    private LocalDateTime date;
    private int viewcount;


    public PostEntity toEntity() {
        PostEntity postEntity = PostEntity.builder()
                .id(id)
                .author(author)
                .title(title)
                .password(password)
                .content(content)
                .viewcount(viewcount)
                .build();
        return postEntity;
    }
    @Builder
    public PostDto(Long id, String author, String title, String password, String content, LocalDateTime date, int viewcount) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.password = password;
        this.content = content;
        this.date = date;
        this.viewcount = viewcount;
    }
}
