package org.predictor.jpaorm.like.presentation;

import lombok.RequiredArgsConstructor;
import org.predictor.jpaorm.like.application.LikeService;
import org.predictor.jpaorm.like.dto.LikeCountResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts/{postId}/like")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    // 좋아요 토글
    @PostMapping
    public ResponseEntity<String> toggleLike(@PathVariable Long postId, @RequestParam String username) {
        return ResponseEntity.ok(likeService.toggleLike(postId, username));
    }

    // 좋아요 조회
    @GetMapping
    public ResponseEntity<LikeCountResponse> getLikeCount(@PathVariable Long postId) {
        // { "postId": 1, "likeCount": 5 }
        return ResponseEntity.ok(likeService.getLikeCount(postId));
    }
}