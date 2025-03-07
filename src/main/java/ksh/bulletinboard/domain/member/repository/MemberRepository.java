package ksh.bulletinboard.domain.member.repository;

import ksh.bulletinboard.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
