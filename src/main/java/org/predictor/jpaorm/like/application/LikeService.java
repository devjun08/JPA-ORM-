package org.predictor.jpaorm.like.application;

import lombok.RequiredArgsConstructor;
import org.predictor.jpaorm.board.domain.Post;
import org.predictor.jpaorm.board.domain.PostRepository;
import org.predictor.jpaorm.like.domain.Like;
import org.predictor.jpaorm.like.domain.LikeRepository;
import org.predictor.jpaorm.like.dto.LikeCountResponse;
import org.predictor.jpaorm.member.domain.Member;
import org.predictor.jpaorm.member.domain.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public String toggleLike(Long postId, String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));

        // 이미 눌렀는지 확인 (토글 로직의 핵심)
        return likeRepository.findByMemberAndPost(member, post)
                .map(like -> {
                    likeRepository.delete(like);
                    return "좋아요 취소";
                })
                .orElseGet(() -> {
                    likeRepository.save(new Like(member, post));
                    return "좋아요 추가";
                });
    }

    @Transactional(readOnly = true)
    public LikeCountResponse getLikeCount(Long postId) {
        // 1. 게시글 존재 확인
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));

        // 2. 개수 조회
        long count = likeRepository.countByPost(post);

        // 3. DTO에 담아서 반환 (배달 상자에 포장!)
        return new LikeCountResponse(postId, count);
    }
}
