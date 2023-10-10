package wods.crossfit.member.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import wods.crossfit.member.domain.dto.MemberDto;
import wods.crossfit.member.domain.dto.MemberDto.MemberRequest;
import wods.crossfit.member.domain.dto.MemberDto.MemberResponse;
import wods.crossfit.member.domain.dto.MemberDto.MemberSearchCondition;

public interface MemberService {

    void saveMember(MemberRequest dto);

    void updateMember(long id, MemberRequest dto);

    void deleteMember(long id);

    String resetPassword(String email);

    Page<MemberResponse> findMember(Pageable pageable,
            MemberSearchCondition memberSearchCondition);

    MemberResponse findMember(long id);

    MemberResponse findByEmail(String email);

}
