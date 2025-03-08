package ksh.bulletinboard.domain.member.service;

import ksh.bulletinboard.domain.member.domain.Member;
import ksh.bulletinboard.domain.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("요청한 아이디와 비밀번호로 새로운 회원을 생성한다")
    @Test
    void join(){

        //given
        String nickname = "아이디";
        String password = "123456";

        //when
        Member member = memberService.join(nickname, password);

        //then
        assertThat(member)
                .extracting("nickname", "password")
                .containsExactly(nickname, password);

        assertThat(member.getId()).isNotNull();

     }

     @DisplayName("이미 사용 중인 아이디를 요청한 경우 예외가 발생한다")
     @Test
     void joinWithDuplicateNickname(){

         //given
         String nickname = "이름1";
         Member member = Member.builder()
                 .nickname(nickname)
                 .build();
         memberRepository.save(member);

         //when //then
         assertThatThrownBy(() -> memberService.join(nickname, "123456"))
                 .isInstanceOf(IllegalArgumentException.class)
                 .hasMessage("사용할 수 없는 아이디입니다");

      }

}
