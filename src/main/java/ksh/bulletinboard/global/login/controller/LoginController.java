package ksh.bulletinboard.global.login.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import ksh.bulletinboard.domain.member.service.MemberService;
import ksh.bulletinboard.global.login.controller.dto.request.LoginInfoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@Valid @RequestBody LoginInfoRequest loginInfo, HttpServletRequest request){
        long memberId = memberService.getLoginMemberId(loginInfo.getNickname(), loginInfo.getPassword());

        HttpSession session = request.getSession();
        session.setAttribute("memberId", memberId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(true);
    }

}
