package wods.crossfit.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wods.crossfit.member.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Boolean existsByEmail(String email);

}
