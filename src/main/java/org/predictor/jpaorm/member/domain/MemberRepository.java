package org.predictor.jpaorm.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * [Repository] DB에 접근하는 인터페이스 (창고지기)
 * JpaRepository를 상속받으면 기본적인 CRUD 기능을 자동으로 제공받습니다.
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    // 사용자 아이디(username)로 회원이 존재하는지 찾는 기능입니다.
    // 중복 가입 방지 로직에서 사용될 예정입니다.
    Optional<Member> findByUsername(String username);
}