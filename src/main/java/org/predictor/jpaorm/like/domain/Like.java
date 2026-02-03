package org.predictor.jpaorm.like.domain;

import jakarta.persistence.*;
import lombok.*;
import org.predictor.jpaorm.board.domain.Post;
import org.predictor.jpaorm.member.domain.Member;

@Entity
@Table(name = "likes") // SQL 예약어 'LIKE'와의 충돌을 피하기 위해 테이블명 변경
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩은 선택이 아닌 필수!
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Like(Member member, Post post) {
        this.member = member;
        this.post = post;
    }
}