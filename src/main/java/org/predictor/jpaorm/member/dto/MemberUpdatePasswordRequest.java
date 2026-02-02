package org.predictor.jpaorm.member.dto;

/**
 * [DTO] 비밀번호 변경 요청 데이터를 담는 가방
 */
public record MemberUpdatePasswordRequest(
        String username,
        String oldPassword,
        String newPassword
) { }