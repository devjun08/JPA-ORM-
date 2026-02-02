package org.predictor.jpaorm.member.application;

import lombok.RequiredArgsConstructor;
import org.predictor.jpaorm.member.domain.Member;
import org.predictor.jpaorm.member.domain.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor // Repository 주입을 위한 생성자를 자동으로 만들어줍니다.
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입 서비스
     */
    public Long join(String username, String password) {
        // 1. 중복 회원 검증 (username이 이미 있는지 확인)
        memberRepository.findByUsername(username)
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 아이디입니다.");
                });

        // 2. 빌더 패턴을 사용해 Member 객체 생성 (배운 내용 적용!)
        Member member = Member.builder()
                .username(username)
                .password(password)
                .build();

        // 3. DB에 저장
        return memberRepository.save(member).getId();
    }

    /**
     * 로그인 로직
     * @return 로그인 성공 시 회원 ID 반환
     */
    public Long login(String username, String password) {
        // 1. 아이디로 회원 찾기 (없으면 에러 던지기)
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 2. 비밀번호 비교하기 (틀리면 에러 던지기)
        if (!member.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 3. 성공하면 회원의 고유 ID 반환
        return member.getId();
    }

    /**
     * 비밀번호 변경 서비스
     */
    @Transactional
    public void updatePassword(String username, String oldPassword, String newPassword) {
        // 1. 회원 찾기
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 2. 기존 비밀번호 확인 (본인 인증)
        if (!member.getPassword().equals(oldPassword)) {
            throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
        }

        // 3. 새로운 비밀번호로 변경
        member.updatePassword(newPassword);

        // 따로 save()를 안 해도 @Transactional 덕분에 메서드가 끝날 때 DB에 반영 (더티 체킹)
    }

    /**
     * 회원 탈퇴 서비스
     */
    @Transactional
    public void withdraw(String username, String password) {
        // 1. 회원 확인
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 2. 본인 확인 (비밀번호 검증)
        if (!member.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않아 탈퇴할 수 없습니다.");
        }

        // 3. 삭제 진행
        memberRepository.delete(member);
    }
}