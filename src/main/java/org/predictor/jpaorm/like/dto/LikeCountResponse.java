package org.predictor.jpaorm.like.dto;

/**
 * 게시글 좋아요 개수 응답 DTO
 * [포인트] record를 사용하여 불변 객체로 깔끔하게 만듭니다.
 */
public record LikeCountResponse(
        Long postId,
        long likeCount
) { }