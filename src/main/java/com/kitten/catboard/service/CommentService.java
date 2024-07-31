package com.kitten.catboard.service;

import com.kitten.catboard.domain.entity.CommentEntity;
import com.kitten.catboard.dto.CommentDto;
import com.kitten.catboard.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CommentService {
    private CommentRepository commentRepository;

    private static final int commentsPerPage = 10;
    @Transactional
    public List<CommentDto> getCommentList(Integer pageNum, Integer origin) {
        List<CommentEntity> commentEntityList = commentRepository.findAll();

        List<CommentDto> commentDtoList = new ArrayList<>();

        for(CommentEntity commentEntity : commentEntityList) {
            if(commentEntity.getOrigin() == origin) {

                commentDtoList.add(this.convertEntityToDto(commentEntity));
            }
        }
        return commentDtoList;
    }
    @Transactional
    public CommentDto getComment(Long id) {
        Optional<CommentEntity> commentEntityWrapper = commentRepository.findById(id);
        CommentEntity commentEntity = commentEntityWrapper.get();

        CommentDto commentDto = CommentDto.builder()
                .id(commentEntity.getId())
                .content(commentEntity.getContent())
                .author(commentEntity.getAuthor())
                .date(commentEntity.getDate())
                .password(commentEntity.getPassword())
                .build();
        return commentDto;
    }

    @Transactional
    public Long submitComment(CommentDto commentDto) {
        return commentRepository.save(commentDto.toEntity()).getId();
    }
    

    private CommentDto convertEntityToDto(CommentEntity commentEntity) {
        return CommentDto.builder()
                .id(commentEntity.getId())
                .author(commentEntity.getAuthor())
                .content(commentEntity.getContent())
                .date(commentEntity.getDate())
                .password((commentEntity.getPassword()))
                .build();
    }

    @Transactional
    public Long getCount() {
        return commentRepository.count();
    }


    public int deleteComment(Long id, String password) {
        CommentEntity comment = commentRepository.getReferenceById(id);
        int origin = comment.getOrigin();
        String correctPassWord = comment.getPassword();
        if(password.equals(correctPassWord)) {
            commentRepository.deleteById(id);
            return origin;
        } else {
            return 0;
        }
    }
    public int updateComment(Long id, String password, CommentDto commentDto) {
        CommentEntity comment = commentRepository.getReferenceById(id);
        String correctPassWord = commentDto.getPassword();
        int origin = comment.getOrigin();
        System.out.println(password);
        System.out.println(correctPassWord);
        if(password.equals(correctPassWord)) {
            System.out.println(origin);
            commentDto.setPassword(correctPassWord);
            return origin;
        } else {
            return 0;
        }
    }
}
