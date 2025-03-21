package ksh.bulletinboard.domain.member.service;

import ksh.bulletinboard.domain.member.domain.Member;
import ksh.bulletinboard.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        Member response = memberService.join(nickname, password);

        //then
        assertThat(response.getNickname()).isEqualTo(nickname);

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

      @DisplayName("이미 가입한 회원의 id를 아이디와 비밀번호로 조회한다")
      @Test
      void getByNicknameAndPassword(){
          //given
          String nickname = "이름";
          String password = "123456";

          Member member = Member.builder()
                  .nickname(nickname)
                  .password(password)
                  .build();
          memberRepository.save(member);

          //when
          long loginMemberId = memberService.getLoginMemberId(nickname, password);

          //then
          assertThat(loginMemberId).isEqualTo(member.getId());

       }

    @DisplayName("아이디를 잘못 입력한 경우 가입한 회원을 조회 시 예외가 발생한다")
    @Test
    void getByNicknameAndPasswordWithException1(){
        //given
        String nickname = "이름";
        String password = "123456";

        Member member = Member.builder()
                .nickname(nickname)
                .password(password)
                .build();
        memberRepository.save(member);

        //when //then
        assertThatThrownBy(() -> memberService.getLoginMemberId("잘못된 이름", password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("회원 정보가 일치하지 않습니다");

    }

    @DisplayName("비밀번호를 잘못 입력한 경우 가입한 회원을 조회 시 예외가 발생한다")
    @Test
    void getByNicknameAndPasswordWithException2(){
        //given
        String nickname = "이름";
        String password = "123456";

        Member member = Member.builder()
                .nickname(nickname)
                .password(password)
                .build();
        memberRepository.save(member);

        //when //then
        assertThatThrownBy(() -> memberService.getLoginMemberId(nickname, "48545"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("회원 정보가 일치하지 않습니다");

    }

}
