package com.yzpocket.blog.controller;

import com.yzpocket.blog.dto.BlogRequestDto;
import com.yzpocket.blog.dto.BlogResponseDto;
import com.yzpocket.blog.entity.Blog;
import com.yzpocket.blog.entity.User;
import com.yzpocket.blog.service.BlogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BlogController {
    private final BlogService blogService;

    // 글 작성
    @PostMapping("/blog")
    public BlogResponseDto createBlog(@RequestBody BlogRequestDto requestDto, HttpServletRequest req) {
        // HttpServletRequest에서 사용자 정보 추출
        User user = (User) req.getAttribute("user");
        return blogService.createBlog(requestDto, user.getUsername());
    }

    // 글 목록 보기
    @GetMapping("/blogs")
    public List<BlogResponseDto> getBlogs() {
        return blogService.getBlogs();
    }

    // 선택 글 보기
    @GetMapping("/blog/{id}")
    public ResponseEntity<?> getOneBlog(@PathVariable Long id) {
        try {
            BlogResponseDto responseDto = blogService.getOneBlog(id);
            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException e) {
            String jsonResponse = "{\"msg\": \"" + e.getMessage() + "\", \"statusCode\": 404}";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonResponse);
        }
    }

    // 선택 글 수정
    @PutMapping("/blog/{id}")
    public Blog updateBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto, HttpServletRequest req) {
        // HttpServletRequest에서 사용자 정보 추출
        User user = (User) req.getAttribute("user");
        String username = user.getUsername();
        return blogService.updateBlog(id, requestDto, username);
    }

    // 선택 글 삭제
    @DeleteMapping("/blog/{id}")
    public ResponseEntity<String> deleteBlog(@PathVariable Long id, HttpServletRequest req) {
        // HttpServletRequest에서 사용자 정보 추출
        User user = (User) req.getAttribute("user");
        String username = user.getUsername();
        return blogService.deleteBlog(id, username);
    }
}