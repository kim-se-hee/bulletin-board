package ksh.bulletinboard.domain.member.application;

import ksh.bulletinboard.domain.member.domain.Member;
import ksh.bulletinboard.domain.member.service.MemberService;
import ksh.bulletinboard.domain.member.service.dto.response.MemberJoinResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Getter
@RequiredArgsConstructor
public class MemberApplicationService {

    private final MemberService memberService;

    //TODO: 컨트롤러 계층 DTO에 대한 의존성 제거
    public MemberJoinResponse join(String nickname, String password) {
        Member member = memberService.join(nickname, password);
        return MemberJoinResponse.from(member);
    }

}
