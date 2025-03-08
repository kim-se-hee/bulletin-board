package ksh.bulletinboard.domain.member.repository;

import ksh.bulletinboard.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByNickname(String nickname);

    Optional<Member> findByNicknameAndPassword(String nickname, String password);

}
