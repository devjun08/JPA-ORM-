package org.predictor.jpaorm.board.domain;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.predictor.jpaorm.hashtag.domain.HashtagType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.predictor.jpaorm.board.domain.QPost.post;
import static org.predictor.jpaorm.member.domain.QMember.member;
import static org.predictor.jpaorm.hashtag.domain.QHashtag.hashtag1;
import static org.predictor.jpaorm.like.domain.QLike.like;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<Post> searchPosts(String keyword, HashtagType hashtagType, Pageable pageable, String sortType) {

        // 1. 데이터 조회 (pageSize + 1 전략)
        List<Post> content = queryFactory
                .selectFrom(post)
                .leftJoin(post.member, member).fetchJoin()
                .leftJoin(hashtag1).on(hashtag1.post.eq(post))
                // [수정 포인트] 좋아요순 정렬을 위해 like 엔티티와 조인을 맺어줍니다.
                .leftJoin(like).on(like.post.eq(post))
                .where(
                        searchCond(keyword),
                        hashtagEq(hashtagType)
                )
                // [수정 포인트] 정렬 시 '좋아요 개수'를 세기 위해 그룹화가 필요할 수 있습니다.
                .groupBy(post.id)
                .orderBy(createOrderSpecifier(sortType))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        // 2. Slice 처리를 위한 다음 페이지 체크
        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    // --- 동적 조건을 위한 도우미 메서드 (책 10장 권장 사항) ---

    private BooleanExpression searchCond(String keyword) {
        if (!StringUtils.hasText(keyword)) return null;
        return post.title.contains(keyword)
                .or(post.content.contains(keyword))
                .or(post.member.username.contains(keyword));
    }

    private BooleanExpression hashtagEq(HashtagType hashtagType) {
        return hashtagType != null ? hashtag1.hashtag.eq(hashtagType) : null;
    }

    private OrderSpecifier<?> createOrderSpecifier(String sortType) {
        // 과제 요구사항: 좋아요 기준 내림차순
        if ("likes".equals(sortType)) {
            // [수정 포인트] 조인된 like 엔티티의 개수를 셉니다.
            return like.count().desc();
        }
        return post.createdAt.desc();
    }
}