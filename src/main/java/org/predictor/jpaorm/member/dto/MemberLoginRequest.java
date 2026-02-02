package org.predictor.jpaorm.member.dto;

/**
 * [DTO] 로그인 요청 데이터를 담는 가방
 */
public record MemberLoginRequest(
        String username,
        String password
) {
}