package org.predictor.jpaorm.hashtag.application;


import lombok.RequiredArgsConstructor;
import org.predictor.jpaorm.board.domain.Post;
import org.predictor.jpaorm.hashtag.domain.Hashtag;
import org.predictor.jpaorm.hashtag.domain.HashtagRepository;
import org.predictor.jpaorm.hashtag.domain.HashtagType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HashtagService {

    private final HashtagRepository hashtagRepository;

    /**
     * 게시글에 해시태그 등록
     */
    public void addHashtag(Post post, List<HashtagType> hashtagTypes) {
        for (HashtagType type : hashtagTypes) {
            Hashtag hashtag = Hashtag.builder()
                    .hashtag(type)
                    .post(post)
                    .build();
            hashtagRepository.save(hashtag);
        }
    }

    /**
     * [과제 요구사항] JPQL 사용하여 해시태그 된 글만 조회하기
     */
    @Transactional(readOnly = true)
    public List<Post> findPostsByHashtag(HashtagType type) {
        return hashtagRepository.findPostsByHashtag(type);
    }
}
