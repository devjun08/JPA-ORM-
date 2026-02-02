package org.predictor.jpaorm.board.dto;

/**
 * [포인트] 수정할 때도 username을 받습니다.
 * 시큐리티가 없으므로 "내가 이 글의 주인이다"라고 주장하는 증표로 사용합니다.
 */
public record PostUpdateRequest(
        String username,
        String title,
        String content
) { }