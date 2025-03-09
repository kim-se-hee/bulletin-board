package ksh.bulletinboard.domain.member.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberJoinRequest {

    @NotBlank(message = "아이디는 필수입니다")
    String nickname;

    @NotBlank(message = "비밀번호는 필수입니다")
    String password;

}
