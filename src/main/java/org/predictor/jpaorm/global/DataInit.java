package org.predictor.jpaorm.global;

import lombok.RequiredArgsConstructor;
import org.predictor.jpaorm.board.domain.Post;
import org.predictor.jpaorm.board.domain.PostRepository;
import org.predictor.jpaorm.hashtag.domain.Hashtag;
import org.predictor.jpaorm.hashtag.domain.HashtagRepository;
import org.predictor.jpaorm.hashtag.domain.HashtagType;
import org.predictor.jpaorm.member.domain.Member;
import org.predictor.jpaorm.member.domain.MemberRepository;
import org.predictor.jpaorm.like.domain.Like;
import org.predictor.jpaorm.like.domain.LikeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataInit {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final HashtagRepository hashtagRepository;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // 1. 테스트 회원 생성
            Member member1 = Member.builder().username("abc").password("1234").build();
            Member member2 = Member.builder().username("abc2").password("1234").build();
            memberRepository.save(member1);
            memberRepository.save(member2);

            // 2. 테스트 게시글 생성
            Post post1 = Post.builder().title("JPA 공부 중").content("내용1").member(member1).build();
            Post post2 = Post.builder().title("QueryDSL 최고").content("내용2").member(member1).build();
            postRepository.save(post1);
            postRepository.save(post2);

            hashtagRepository.save(Hashtag.builder()
                    .hashtag(HashtagType.LIVERPOOL) // Enum 상수는 보통 대문자입니다.
                    .post(post2)
                    .build());

            // 3. 테스트 좋아요 생성
            likeRepository.save(Like.builder().member(member1).post(post1).build());
            likeRepository.save(Like.builder().member(member1).post(post2).build());
            likeRepository.save(Like.builder().member(member2).post(post2).build());
        };
    }
}