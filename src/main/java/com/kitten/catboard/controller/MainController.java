package com.kitten.catboard.controller;

import com.kitten.catboard.dto.CommentDto;
import com.kitten.catboard.dto.PostDto;
import com.kitten.catboard.service.CommentService;
import com.kitten.catboard.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.stream.events.Comment;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@Slf4j
public class MainController {
    private PostService postService;
    private CommentService commentService;
    //패스워드 암호화용 키
    private final String key = "agreycatmeowing";
    
    @GetMapping("/")
    public String index() {
      return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    //게시글 리스트 표시
    @GetMapping("/list")
    public String list(Model model, @RequestParam(value="page",defaultValue = "1") Integer pageNum) {
        List<PostDto> postList = postService.getPostlist(pageNum);
        Integer[] pageList = postService.getPageList(pageNum);
        model.addAttribute("postList", postList);
        model.addAttribute("pageList", pageList);
        return "/list.html";
    }

    //게시글 CRUD기능
    @GetMapping("/new")
    public String newpost() { return "new"; }

    @PostMapping("/post")
    public String submit(PostDto postDto) {
        postService.submitPost(postDto);

        return "redirect:/list";
    }
    @GetMapping("/post/{no}")
    public String article(@PathVariable("no") Long no, Model model,@RequestParam(value="page",defaultValue = "1") Integer pageNum) {
        List<CommentDto> commentList = commentService.getCommentList(pageNum, no.intValue());
        PostDto postDto = postService.getPost(no);
        postDto.setViewcount(postDto.getViewcount() + 1);
        postService.submitPost(postDto);
        model.addAttribute("postDto", postDto);
        model.addAttribute("commentList", commentList);
        return "/article.html";
    }
    @GetMapping("/confirmdelete/{no}")
    public String confirmDelete(@PathVariable("no") Long no,Model model) {
        PostDto postDto = postService.getPost(no);
        model.addAttribute("postDto", postDto);
        return "/deletepass.html";
    }
    @PostMapping("/delete/{no}")
    public String delete(@PathVariable("no") Long no, @RequestParam Map<String, String> requestParams) {
        String password = requestParams.get("password");
        int result = postService.deletePost(no, password);
        if(result == 1) {
            return "redirect:/list";
        }
        return "/wrongpass.html";
    }
    @GetMapping("/edit/{no}")
    public String editForm(@PathVariable("no") Long no,Model model) {
        PostDto postDto = postService.getPost(no);
        model.addAttribute("postDto", postDto);
        return "/editform.html";
    }
    @PutMapping("/update/{no}")
    public String updatePost(@PathVariable("no") Long no, Model model, @RequestParam Map<String, String> requestParams, PostDto postDto) {
        PostDto currDto = postService.getPost(no);
        model.addAttribute("postDto", currDto);
        String password = requestParams.get("password");
        if(postService.updatePost(no, password, currDto) == 1) {
            postDto.setPassword(password);
            postService.submitPost(postDto);
            return "redirect:/post/" + no;
        }
        return "/wrongpass.html";
    }

    //게시글 검색
    @GetMapping("/search")
    public String search(@RequestParam(value="keyword") String keyword, @RequestParam(value="category") String category, Model model, @RequestParam(value="page",defaultValue = "1") Integer pageNum) {
        List<PostDto> postDtoList = postService.searchPosts(keyword, category);
        model.addAttribute("postList", postDtoList);
        return "/list.html";
    }

    //댓글 기능 CRUD용 컨트롤러
    @PostMapping("/addcomment/{no}")
    public String submitComment(CommentDto commentDto) {
        commentService.submitComment(commentDto);
        return "redirect:/post/{no}";
    }

    @GetMapping("/removecomment/{no}")
    public String confirmRemove(@PathVariable("no") Long no, Model model) {
        CommentDto commentDto = commentService.getComment(no);
        model.addAttribute("commentDto", commentDto);
        return "/removepass.html";
    }

    @PostMapping("/remove/{no}")
    public String remove(@PathVariable("no") Long no, @RequestParam Map<String, String> requestParams) {
        String password = requestParams.get("password");
        CommentDto commentDto = commentService.getComment(no);
        int result = commentService.deleteComment(no, password);
        if(result != 0) {
            return "redirect:/post/" + result;
        }
        return "/wrongpass.html";
    }

    @GetMapping("/editcomment/{no}")
    public String editComment(@PathVariable("no") Long no,Model model) {
        CommentDto commentDto = commentService.getComment(no);
        model.addAttribute("commentDto", commentDto);
        return "/editcomment.html";
    }

    @PutMapping("/updatecomment/{no}")
    public String updateComment(@PathVariable("no") Long no, Model model, @RequestParam Map<String, String> requestParams, CommentDto commentDto) {
        CommentDto currDto = commentService.getComment(no);
        model.addAttribute("commentDto", currDto);
        String password = requestParams.get("password");
        int result = commentService.updateComment(no, password, currDto);
        if(result != 0) {
            commentDto.setOrigin(result);
            commentService.submitComment(commentDto);
            return "redirect:/post/" + result;
        }
        return "/wrongpass.html";
    }



}
