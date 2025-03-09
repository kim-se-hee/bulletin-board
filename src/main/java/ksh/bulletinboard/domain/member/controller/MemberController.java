package ksh.bulletinboard.domain.member.controller;

import jakarta.validation.Valid;
import ksh.bulletinboard.domain.member.controller.dto.request.MemberJoinRequest;
import ksh.bulletinboard.domain.member.service.MemberService;
import ksh.bulletinboard.domain.member.service.dto.response.MemberJoinResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/member")
    public ResponseEntity<MemberJoinResponse> member(@Valid @RequestBody MemberJoinRequest request) {
        MemberJoinResponse response = memberService.join(request.getNickname(), request.getPassword());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}
