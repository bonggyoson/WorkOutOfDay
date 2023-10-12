package wods.crossfit.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static wods.crossfit.dummy.member.SampleMember.sampleSaveMemberData;
import static wods.crossfit.dummy.member.SampleMember.sampleUpdateMemberData;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import wods.crossfit.common.CommonTestConfig;
import wods.crossfit.dummy.member.SampleMember;
import wods.crossfit.member.domain.Member;
import wods.crossfit.member.domain.dto.MemberDto.MemberRequest;
import wods.crossfit.member.domain.dto.MemberDto.MemberResponse;
import wods.crossfit.member.repository.MemberRepository;

public class MemberServiceTest extends CommonTestConfig {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @DisplayName("회원 가입 서비스 로직 테스트")
    @Test
    void saveMember() {
        // Given
        final MemberRequest requestDto = sampleSaveMemberData();

        // When
        memberService.saveMember(requestDto);

        // Then
        assertThat(requestDto.getEmail()).isEqualTo(memberRepository.findByEmail(
                requestDto.getEmail()).get().getEmail());
    }

    @DisplayName("회원 가입 서비스 로직 실패 테스트 - 아이디 중복")
    @Test
    void saveMemberFailedDuplicateEmail() {
        // Given
        final MemberRequest requestDto = sampleSaveMemberData();

        memberRepository.save(requestDto.toEntity());

        // Then
        assertThatThrownBy(() -> memberService.saveMember(requestDto))
                .isInstanceOf(DuplicateKeyException.class)
                .hasMessageContaining(
                        String.format("Not Found Email : [%S]", requestDto.getEmail()));
    }

    @DisplayName("회원 수정 서비스 로직 테스트")
    @Test
    void updateMember() {
        // Given
        final MemberRequest saveRequestDto = sampleSaveMemberData();
        final MemberRequest updateRequestDto = sampleUpdateMemberData();

        Member savedMember = memberRepository.save(saveRequestDto.toEntity());

        // When
        memberService.updateMember(savedMember.getId(), updateRequestDto);

        // Then
        assertThat(savedMember.getEmail()).isEqualTo(updateRequestDto.getEmail());
        assertThat(savedMember.getName()).isEqualTo(updateRequestDto.getName());
        assertThat(savedMember.getBox()).isEqualTo(updateRequestDto.getBox());
    }

    @DisplayName("회원 삭제 서비스 로직 테스트")
    @Test
    void deleteMember() {
        // Given
        final MemberRequest saveRequestDto = sampleSaveMemberData();
        Member savedMember = memberRepository.save(saveRequestDto.toEntity());

        // When
        memberService.deleteMember(savedMember.getId());

        // Then
        assertThat(memberRepository.findById(savedMember.getId())).isEmpty();
    }
}
