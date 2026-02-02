package org.predictor.jpaorm.board.dto;

/**
 * 게시글 작성을 위한 DTO
 * username은 작성자를 찾기 위해 필요합니다.
 */
public record PostCreateRequest(
        String username,
        String title,
        String content
) { }