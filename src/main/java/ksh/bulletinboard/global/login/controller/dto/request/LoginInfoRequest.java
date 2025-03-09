package ksh.bulletinboard.global.login.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginInfoRequest {

    @NotBlank
    private String nickname;

    @NotBlank
    private String password;

    public LoginInfoRequest(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }

}
