package org.predictor.jpaorm.like.domain;

import org.predictor.jpaorm.board.domain.Post;
import org.predictor.jpaorm.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    // 특정 회원과 게시글의 조합이 있는지 찾기 위해 필요
    Optional<Like> findByMemberAndPost(Member member, Post post);

    // 특정 게시글의 좋아요 개수 조회
    long countByPost(Post post);
}