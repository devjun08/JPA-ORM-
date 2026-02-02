package org.predictor.jpaorm.member.presentation;

import lombok.RequiredArgsConstructor;
import org.predictor.jpaorm.member.application.MemberService;
import org.predictor.jpaorm.member.dto.MemberJoinRequest;
import org.predictor.jpaorm.member.dto.MemberLoginRequest;
import org.predictor.jpaorm.member.dto.MemberUpdatePasswordRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * [Presentation 계층]
 * 외부(브라우저, Postman 등)에서 들어오는 HTTP 요청을 가장 먼저 맞이하는 곳입니다.
 */
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    // 우리가 만든 비즈니스 로직(Service)을 사용하기 위해 불러옵니다.
    private final MemberService memberService;

    /**
     * [회원가입 기능]/
     * 사용자가 보낸 아이디와 비번을 받아 실제 가입 처리를 진행합니다.
     */
    @PostMapping("/join")
    // 1. HTTP POST 메서드 요청을 처리합니다. (데이터를 '생성'할 때는 주로 POST를 씁니다.)
    // 2. 최종 주소: POST http://localhost:8080/api/members/join
    public ResponseEntity<Long> join(@RequestBody MemberJoinRequest request) {
        // @RequestBody: 웹에서 보낸 JSON 데이터를 우리가 만든 '가방(MemberJoinRequest DTO)'에 쏙 담아주는 역할입니다.

        // 1. 서비스의 join 기능을 호출합니다.
        // 2. record로 만든 DTO이므로 request.username() 처럼 메서드 형태로 값을 꺼냅니다.
        Long id = memberService.join(request.username(), request.password());

        // 3. ResponseEntity: 응답할 때 데이터뿐만 아니라 '상태 코드(200 OK 등)'를 같이 보내기 위해 씁니다.
        // 4. .ok(id): "성공(200 OK)했어! 그리고 방금 가입한 유저의 번호는 이거야!"라고 답장하는 것입니다.
        return ResponseEntity.ok(id);
    }

    // MemberController 로그인 요청 처리
    @PostMapping("/login")
    public ResponseEntity<Long> login(@RequestBody MemberLoginRequest request) {
        Long memberId = memberService.login(request.username(), request.password());
        return ResponseEntity.ok(memberId);
    }

    /**
     * 비밀번호 변경 API
     * PATCH http://localhost:8080/api/members/password
     */
    @PatchMapping("/password")
    public ResponseEntity<String> updatePassword(@RequestBody MemberUpdatePasswordRequest request) {
        memberService.updatePassword(request.username(), request.oldPassword(), request.newPassword());
        return ResponseEntity.ok("비밀번호 변경이 완료되었습니다.");
    }

    /**
     * 회원 탈퇴 API
     * DELETE http://localhost:8080/api/members/withdraw
     */
    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody MemberLoginRequest request) {
        memberService.withdraw(request.username(), request.password());
        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다. 그동안 이용해주셔서 감사합니다.");
    }
}