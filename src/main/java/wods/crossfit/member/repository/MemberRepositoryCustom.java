package wods.crossfit.member.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import wods.crossfit.member.domain.Member;
import wods.crossfit.member.domain.dto.MemberDto.MemberSearchCondition;

public interface MemberRepositoryCustom {

    Page<Member> findMember(Pageable pageable, MemberSearchCondition memberSearchCondition);

    Member findMember(long id);

}
