package wods.crossfit.member.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import wods.crossfit.global.config.jwt.TokenProvider;
import wods.crossfit.member.domain.Member;
import wods.crossfit.member.domain.dto.MemberDto.MemberRequest;
import wods.crossfit.member.domain.dto.MemberDto.MemberResponse;
import wods.crossfit.profile.domain.dto.ProfileDto;
import wods.crossfit.profile.repository.ProfileRepository;
import wods.crossfit.member.repository.MemberRepository;
import wods.crossfit.member.service.MemberService;
import wods.crossfit.token.repository.RefreshTokenRepository;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ProfileRepository profileRepository;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void saveMember(MemberRequest dto) {
        if (memberRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateKeyException("이미 존재하는 이메일입니다.");
        } else {
            dto.encryptPassword(bCryptPasswordEncoder.encode(dto.getPassword()));

            // 회원 가입
            Member savedMember = memberRepository.save(dto.toEntity());

            // 프로필 생성
            profileRepository.save(
                    new ProfileDto.ProfileRequest(null, null, null, null,
                            savedMember.getId()).toEntity(savedMember));
        }
    }

    @Override
    @Transactional
    public void updateMember(long id, MemberRequest dto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 회원은 존재하지 않습니다."));

        member.update(
                dto.getEmail(),
                bCryptPasswordEncoder.encode(dto.getPassword()),
                dto.getName(), dto.getBox(), dto.getRole());
    }

    @Override
    @Transactional
    public void deleteMember(long id) {
        memberRepository.deleteById(id);
    }

    @Override
    @Transactional
    public String resetPassword(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("해당 이메일은 존재하지 않습니다."));

        // 랜덤 알파벳 + 숫자로만 이뤄진 8개 문자열 반환
        String resetPassword = RandomStringUtils.randomAlphanumeric(8);
        member.resetPassword(bCryptPasswordEncoder.encode(resetPassword));

        return resetPassword;
    }

    @Override
    public List<MemberResponse> findMembers() {
        return memberRepository.findAll().stream().map(MemberResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public MemberResponse findById(long id) {
        return memberRepository.findById(id).map(MemberResponse::new)
                .orElseThrow(
                        () -> new NotFoundException(String.format("해당 회원은 존재하지 않습니다. ID[%S]", id)));
    }

    @Override
    public MemberResponse findByEmail(String email) {
        return memberRepository.findByEmail(email).map(MemberResponse::new)
                .orElseThrow(() -> new NotFoundException(
                        String.format("해당 회원은 존재하지 않습니다. EMAIL[%S]", email)));
    }

}