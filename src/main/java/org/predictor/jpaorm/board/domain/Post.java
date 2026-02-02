package org.predictor.jpaorm.board.domain;

import jakarta.persistence.*;
import lombok.*;
import org.predictor.jpaorm.global.BaseTimeEntity;
import org.predictor.jpaorm.member.domain.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT") // 긴 텍스트를 위한 설정
    private String content;

    @ManyToOne(fetch = FetchType.LAZY) // 게시글(N) : 회원(1) 관계 설정
    @JoinColumn(name = "member_id")   // DB 테이블의 외래키(FK) 컬럼명
    private Member member;

    @Builder
    public Post(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }

    // 엔티티 안에 비즈니스 로직을 넣는 것이 좋습니다.
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}