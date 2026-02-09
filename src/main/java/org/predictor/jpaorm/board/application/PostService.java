package org.predictor.jpaorm.board.application;

import lombok.RequiredArgsConstructor;
import org.predictor.jpaorm.board.domain.Post;
import org.predictor.jpaorm.board.domain.PostRepository;
import org.predictor.jpaorm.board.dto.PostCreateRequest;
import org.predictor.jpaorm.board.dto.PostResponse;
import org.predictor.jpaorm.board.dto.PostUpdateRequest;
import org.predictor.jpaorm.hashtag.application.HashtagService;
import org.predictor.jpaorm.member.domain.Member;
import org.predictor.jpaorm.member.domain.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository; // 작성자 검증을 위해 주입
    private final HashtagService hashtagService;

    // 게시글 생성
    public Long create(PostCreateRequest request) {
        // 1. 작성자 존재 확인 (제미나이 주석 삭제 ㅎㅎ..)
        Member member = memberRepository.findByUsername(request.username())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 2. 게시글 생성 (제미나이 주석 삭제 ㅎㅎ..)
        Post post = Post.builder()
                .title(request.title())
                .content(request.content())
                .member(member)
                .build();

        Post savedPost = postRepository.save(post); // 게시글 저장

        // hashtag 생성
        if (request.hashtags() != null && !request.hashtags().isEmpty()) {
            hashtagService.addHashtag(savedPost, request.hashtags());
        }

        // 3. DB 저장 및 생성된 ID 반환
        return postRepository.save(post).getId();
    }

    // 게시글 수정
    public void update(Long id, PostUpdateRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        // 본인 확인 로직 (이걸 빼먹으면 남의 글을 막 고칠 수 있음)
        if (!post.getMember().getUsername().equals(request.username())) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }

        // save()를 안 불러도 트랜잭션 종료 시점에 DB에 반영됨 (더티 체킹)
        post.update(request.title(), request.content());
    }

    // 게시글 삭제
    public void delete(Long id, String username) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        // [포인트] 삭제할 때도 본인인지 반드시 확인!
        if (!post.getMember().getUsername().equals(username)) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }

        postRepository.delete(post);
    }

    // 게시글 단일건 조회
    @Transactional(readOnly = true) // 조회 전용으로 성능 최적화
    public PostResponse getPost(Long id) {
        // 예외 메시지를 직접 적어주는 방식
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 게시글이 존재하지 않습니다. id=" + id));

        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getMember().getUsername(),
                post.getCreatedAt()
        );
    }
}