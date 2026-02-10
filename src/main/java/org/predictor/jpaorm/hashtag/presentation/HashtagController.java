package org.predictor.jpaorm.hashtag.presentation;

import lombok.RequiredArgsConstructor;
import org.predictor.jpaorm.board.domain.Post;
import org.predictor.jpaorm.board.dto.PostResponse;
import org.predictor.jpaorm.hashtag.application.HashtagService;
import org.predictor.jpaorm.hashtag.domain.HashtagType;
import org.predictor.jpaorm.like.domain.LikeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HashtagController {

    private final HashtagService hashtagService;
    private final LikeRepository likeRepository;

    /**
     * [과제 요구사항] 해시태그로 게시글 조회 API
     * 특정 태그가 달린 게시글만 필터링하여 반환합니다.
     */
    @GetMapping("/api/posts/hashtag")
    public ResponseEntity<List<PostResponse>> getPostsByHashtag(@RequestParam HashtagType type) {
        List<Post> posts = hashtagService.findPostsByHashtag(type);

        // 엔티티 -> DTO 변환 (책 13장 권장 사항)
        List<PostResponse> responses = posts.stream()
                .map(post -> new PostResponse(
                        post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getMember().getUsername(),
                        post.getCreatedAt(),
                        likeRepository.countByPost(post) // [수정] 6번째 인자인 좋아요 수를 추가!
                ))
                .toList();

        return ResponseEntity.ok(responses);
    }
}