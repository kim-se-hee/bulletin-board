package ksh.bulletinboard.domain.member.service.dto.response;

import ksh.bulletinboard.domain.member.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemberJoinResponse {

    private String nickname;
    private LocalDateTime joinDate;

    public static MemberJoinResponse from(Member member) {
        return new MemberJoinResponse(member);
    }

    private MemberJoinResponse(Member member) {
        this.nickname = member.getNickname();
        this.joinDate = member.getCreatedAt();
    }

}
