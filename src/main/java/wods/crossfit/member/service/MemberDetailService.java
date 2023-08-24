package wods.crossfit.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import wods.crossfit.member.domain.Member;
import wods.crossfit.member.repository.MemberRepository;

@RequiredArgsConstructor
@Service
public class MemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    // 사용자 이메일로 사용자의 정보를 가져오는 메서드
    @Override
    public Member loadUserByUsername(String email) {
        return memberRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("사용자가 존재하지 않습니다."));
    }
}
