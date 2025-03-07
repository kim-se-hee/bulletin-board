package ksh.bulletinboard.domain.member.domain;

import jakarta.persistence.*;
import ksh.bulletinboard.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    private String nickname;

    private String password;

    private String name;

    @Builder
    private Member(String nickname, String password, String name) {
        this.nickname = nickname;
        this.password = password;
        this.name = name;
    }

}
