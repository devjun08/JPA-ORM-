package org.predictor.jpaorm.member.domain;

import jakarta.persistence.*;
import lombok.*;
import org.predictor.jpaorm.global.BaseTimeEntity;

/**
 * [도메인] 회원 정보를 담는 실제 DB 테이블 매핑 클래스
 */
@Entity // 1. 이 클래스는 JPA가 관리하는 엔티티이며, DB의 테이블과 1:1로 매핑됩니다.
@Getter // 2. 모든 필드에 대한 Getter를 자동으로 만듭니다.
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 3. JPA는 기본 생성자가 필수입니다. 다만, 무분별한 객체 생성을 막기 위해 접근 제어자를 PROTECTED로 둡니다.
public class Member extends BaseTimeEntity { // 4. 아까 만든 BaseTimeEntity를 상속받아 생성/수정 시간을 물려받습니다.

    @Id // 5. PK(기본키)를 설정합니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 6. PK 생성을 DB에 맡깁니다. (MySQL의 Auto-increment와 같습니다.)
    private Long id;

    @Column(unique = true, nullable = false) // 7. 아이디는 중복될 수 없고(unique), 비어있을 수 없습니다(nullable = false).
    private String username;

    @Column(nullable = false) // 8. 비밀번호도 필수 값입니다.
    private String password;

    @Builder // 9. 빌더 패턴을 사용하여 객체 생성 시점에 어떤 필드에 어떤 값이 들어가는지 명확하게 해줍니다.
    public Member(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}