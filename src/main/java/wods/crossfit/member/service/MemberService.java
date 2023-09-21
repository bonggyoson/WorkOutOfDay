package wods.crossfit.member.service;

import java.util.List;
import wods.crossfit.member.domain.dto.MemberDto.MemberRequest;
import wods.crossfit.member.domain.dto.MemberDto.MemberResponse;

public interface MemberService {

    void saveMember(MemberRequest dto);

    void updateMember(long id, MemberRequest dto);

    void deleteMember(long id);

    String resetPassword(String email);

    List<MemberResponse> findMembers();

    MemberResponse findById(long id);

    MemberResponse findByEmail(String email);

}
