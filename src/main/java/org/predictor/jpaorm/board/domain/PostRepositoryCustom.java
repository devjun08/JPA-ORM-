package org.predictor.jpaorm.board.domain;

import org.predictor.jpaorm.hashtag.domain.HashtagType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PostRepositoryCustom {
    // 과제 요구사항: 다중 검색 및 Slice 페이징
    Slice<Post> searchPosts(String keyword, HashtagType hashtag, Pageable pageable, String sortType);
}