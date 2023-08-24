package wods.crossfit.member.service;

import wods.crossfit.member.domain.dto.MemberDto.MemberRequest;

public interface MemberService {

    void saveMember(MemberRequest dto);

    void updateMember(long id, MemberRequest dto);

    void deleteMember(long id);

    String resetPassword(String email);
}
