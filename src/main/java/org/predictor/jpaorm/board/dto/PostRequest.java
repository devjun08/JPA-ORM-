package org.predictor.jpaorm.board.dto;

import org.predictor.jpaorm.hashtag.domain.HashtagType;

import java.util.List;

/**
 * 게시글 작성을 위한 DTO
 * username은 작성자를 찾기 위해 필요합니다.
 */
public record PostRequest(
        String username,
        String title,
        String content,
        List<HashtagType> hashtags // 추가: 사용자가 선택한 태그들
) { }