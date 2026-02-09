package org.predictor.jpaorm.board.presentation;

import lombok.RequiredArgsConstructor;
import org.predictor.jpaorm.board.application.PostService;
import org.predictor.jpaorm.board.dto.PostRequest;
import org.predictor.jpaorm.board.dto.PostResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 생성
    @PostMapping
    public ResponseEntity<Long> create(@RequestBody PostRequest request) {
        Long postId = postService.create(request);
        return ResponseEntity.ok(postId);
    }

    // 수정: PUT 메서드 사용
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody PostRequest request) {
        postService.update(id, request);
        return ResponseEntity.ok("수정 성공!");
    }

    // 삭제: DELETE 메서드 사용
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id, @RequestParam String username) {
        postService.delete(id, username);
        return ResponseEntity.ok("삭제 성공!");
    }

    // 게시글 조회 (단건)
    @GetMapping("/{id}") // GET http://localhost:8080/api/posts/1
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPost(id));
    }
}