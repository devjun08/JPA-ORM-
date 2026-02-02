package org.predictor.jpaorm.member.dto;

/**
 * [DTO] 회원가입 요청 데이터를 담는 가방 (Record 사용)
 */
public record MemberJoinRequest(
        String username,
        String password
) {
}