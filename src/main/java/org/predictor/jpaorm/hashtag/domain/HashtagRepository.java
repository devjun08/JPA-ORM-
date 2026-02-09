package org.predictor.jpaorm.hashtag.domain;

import org.predictor.jpaorm.board.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    /**
     * [과제 요구사항] JPQL을 사용하여 특정 해시태그가 달린 게시글만 조회합니다.
     * 해시태그(h) 엔티티를 뒤져서 매칭된 게시글(h.post)을 리스트로 반환합니다.
     */
    @Query("select h.post from Hashtag h where h.hashtag = :hashtagType")
    List<Post> findPostsByHashtag(@Param("hashtagType") HashtagType hashtagType);
}