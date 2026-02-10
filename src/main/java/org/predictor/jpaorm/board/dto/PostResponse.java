package org.predictor.jpaorm.board.dto;

import java.time.LocalDateTime;

/**
 * 게시글 응답 DTO
 */
public record PostResponse(
        Long id,
        String title,
        String content,
        String writer,
        LocalDateTime createdAt, // 생성시간 추가
        long likeCount
) { }