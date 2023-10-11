package wods.crossfit.member.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static wods.crossfit.dummy.member.SampleMember.sampleSaveMemberData;
import static wods.crossfit.dummy.member.SampleMember.sampleSaveMemberFailedData;
import static wods.crossfit.dummy.member.SampleMember.sampleUpdateMemberData;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;
import org.webjars.NotFoundException;
import wods.crossfit.common.CommonTestConfig;
import wods.crossfit.member.domain.Member;
import wods.crossfit.member.domain.dto.MemberDto.MemberRequest;
import wods.crossfit.member.repository.MemberRepository;
import wods.crossfit.member.service.MemberService;

class MemberApiControllerTest extends CommonTestConfig {

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected MemberService memberService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @DisplayName("회원 가입 api 테스트")
    @Test
    public void saveMember() throws Exception {
        // Given
        final String url = "/api/members";

        final MemberRequest requestDto = sampleSaveMemberData();

        final String requestBody = objectMapper.writeValueAsString(requestDto);

        // When
        ResultActions result = mockMvc.perform(post(url)
                .contentType(APPLICATION_JSON_VALUE)
                .content(requestBody));

        // Then
        result.andExpect(status().isCreated());

        Member members = memberRepository.findByEmail(requestDto.getEmail()).get();

        assertThat(members.getEmail()).isEqualTo(requestDto.getEmail());
        assertThat(members.getName()).isEqualTo(requestDto.getName());
    }

    @DisplayName("회원 가입 실패 api 테스트 - dto 빈 값 존재")
    @Test
    public void saveMemberFailedNull() throws Exception {
        final String url = "/api/members";

        final MemberRequest requestDto = sampleSaveMemberFailedData();

        final String requestBody = objectMapper.writeValueAsString(requestDto);

        // When
        ResultActions result = mockMvc.perform(post(url)
                .contentType(APPLICATION_JSON_VALUE)
                .content(requestBody));

        result.andExpect(status().is5xxServerError())
                .andExpect(r -> assertTrue(
                        r.getResolvedException() instanceof IllegalArgumentException));
    }

    @DisplayName("회원 가입 실패 api 테스트 - 아이디 중복")
    @Test
    public void saveMemberFailedDuplicateEmail() throws Exception {
        // Given
        final String url = "/api/members";

        final MemberRequest requestDto = sampleSaveMemberData();

        memberRepository.save(requestDto.toEntity());

        final String requestBody = objectMapper.writeValueAsString(requestDto);

        // When
        ResultActions result = mockMvc.perform(post(url)
                .contentType(APPLICATION_JSON_VALUE)
                .content(requestBody));

        result.andExpect(status().is4xxClientError())
                .andExpect(r -> assertTrue(
                        r.getResolvedException() instanceof DuplicateKeyException));
    }

    @DisplayName("회원 수정 api 테스트")
    @Test
    public void updateMember() throws Exception {
        // Given
        final String url = "/api/members/{id}";

        Member savedMember = memberRepository.save(Member.builder()
                .email(sampleSaveMemberData().getEmail())
                .password(sampleSaveMemberData().getPassword())
                .name(sampleSaveMemberData().getName())
                .box(sampleSaveMemberData().getBox())
                .build());

        MemberRequest requestDto = sampleUpdateMemberData();

        // When
        ResultActions result = mockMvc.perform(put(url, savedMember.getId())
                .contentType(APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestDto)));

        // Then
        result.andExpect(status().isOk());

        Member member = memberRepository.findById(savedMember.getId()).get();

        assertThat(member.getEmail()).isEqualTo(requestDto.getEmail());
        assertTrue(bCryptPasswordEncoder.matches(requestDto.getPassword(), member.getPassword()));
    }

    @DisplayName("회원 삭제 api 테스트")
    @Test
    public void deleteMember() throws Exception {
        // Given
        final String url = "/api/members/{id}";

        Member savedMember = memberRepository.save(Member.builder()
                .email(sampleSaveMemberData().getEmail())
                .password(sampleSaveMemberData().getPassword())
                .name(sampleSaveMemberData().getName())
                .box(sampleSaveMemberData().getBox())
                .build());

        // When
        mockMvc.perform(delete(url, savedMember.getId())).andExpect(status().isOk());

        assertThat(memberRepository.findMember(savedMember.getId())).isNull();

    }
}