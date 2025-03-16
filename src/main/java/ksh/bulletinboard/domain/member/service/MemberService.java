package ksh.bulletinboard.domain.member.service;

import ksh.bulletinboard.domain.member.domain.Member;
import ksh.bulletinboard.domain.member.repository.MemberRepository;
import ksh.bulletinboard.domain.member.service.dto.response.MemberJoinResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member join(String nickname, String password) {
        if(memberRepository.existsByNickname(nickname)) {
            throw new IllegalArgumentException("사용할 수 없는 아이디입니다");
        }

        Member member = Member.builder()
                .nickname(nickname)
                .password(password)
                .build();
        return memberRepository.save(member);
    }

    public long getLoginMemberId(String nickname, String password) {
        return memberRepository.findByNicknameAndPassword(nickname, password)
                .map(Member::getId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보가 일치하지 않습니다"));
    }

    public Member getById(long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));
    }

}
