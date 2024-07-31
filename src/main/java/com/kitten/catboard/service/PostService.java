package com.kitten.catboard.service;

import com.kitten.catboard.domain.entity.PostEntity;
import com.kitten.catboard.dto.PostDto;
import com.kitten.catboard.repository.PostRepository;
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
public class PostService {
    private PostRepository postRepository;

    private static final int postsPerPage = 10;
    @Transactional
    public List<PostDto> getPostlist(Integer pageNum) {
        Page<PostEntity> page = postRepository.findAll(PageRequest.of(pageNum - 1, postsPerPage, Sort.by(Sort.Direction.DESC, "date")));
        List<PostEntity> postEntities = page.getContent();
        List<PostDto> postDtoList = new ArrayList<>();

        for(PostEntity postEntity : postEntities) {
            postDtoList.add(this.convertEntityToDto(postEntity));
        }
        return postDtoList;
    }
    @Transactional
    public PostDto getPost(Long id) {
        Optional<PostEntity> postEntityWrapper = postRepository.findById(id);
        PostEntity postEntity = postEntityWrapper.get();

        PostDto postDto = PostDto.builder()
                .id(postEntity.getId())
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .author(postEntity.getAuthor())
                .date(postEntity.getDate())
                .password(postEntity.getPassword())
                .viewcount(postEntity.getViewcount())
                .build();
        return postDto;
    }

    @Transactional
    public Long submitPost(PostDto postDto) {
        return postRepository.save(postDto.toEntity()).getId();
    }

    @Transactional
    public List<PostDto> searchPosts(String keyword, String category) {
        List<PostEntity> postEntities;
        List<PostDto> postDtoList = new ArrayList<>();
        switch(category) {
            case "title":
                postEntities = postRepository.findByTitleContaining(keyword);
                if(postEntities.isEmpty()) {
                    return postDtoList;
                }
                for(PostEntity postEntity : postEntities) {
                    postDtoList.add(this.convertEntityToDto(postEntity));
                }
                break;
            case "author":
                postEntities = postRepository.findByAuthorContaining(keyword);
                if(postEntities.isEmpty()) {
                    return postDtoList;
                }
                for(PostEntity postEntity : postEntities) {
                    postDtoList.add(this.convertEntityToDto(postEntity));
                }
                break;
        }
        return postDtoList;
    }

    private PostDto convertEntityToDto(PostEntity postEntity) {
        return PostDto.builder()
                .id(postEntity.getId())
                .title(postEntity.getTitle())
                .author(postEntity.getAuthor())
                .content(postEntity.getContent())
                .date(postEntity.getDate())
                .viewcount((postEntity.getViewcount()))
                .password((postEntity.getPassword()))
                .build();
    }

    @Transactional
    public Long getCount() {
        return postRepository.count();
    }

    public Integer[] getPageList(Integer pageNum) {
        Integer[] realPageList;

        int numOfPost = Math.toIntExact(getCount());
        int numOfPage = numOfPost / postsPerPage;
        if(numOfPost%10 != 0) {
            numOfPage++;
        }
        ArrayList<Integer> pageNumList = new ArrayList<>();
        for(int i = 0; i < numOfPage; i++) {
            pageNumList.add(i+1);
        }
        realPageList = new Integer[pageNumList.size()];
        for(int i = 0; i < realPageList.length; i++) {
            realPageList[i] = pageNumList.get(i);
        }

        return realPageList;
    }
    public int deletePost(Long id, String password) {
        PostEntity post = postRepository.getReferenceById(id);
        String correctPassWord = post.getPassword();
        if(password.equals(correctPassWord)) {
            postRepository.deleteById(id);
            return 1;
        } else {
            return 2;
        }
    }
    public int updatePost(Long id, String password, PostDto postDto) {
        PostEntity post = postRepository.getReferenceById(id);
        String correctPassWord = post.getPassword();
        if(password.equals(correctPassWord)) {
            postDto.setPassword(correctPassWord);
            return 1;
        } else {
            return 2;
        }
    }
}
