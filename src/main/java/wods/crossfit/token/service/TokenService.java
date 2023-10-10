package wods.crossfit.token.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import wods.crossfit.global.config.jwt.TokenProvider;
import wods.crossfit.member.domain.Member;
import wods.crossfit.member.repository.MemberRepository;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final MemberRepository memberRepository;

    public String createNewAccessToken(String refreshToken) {
        // 토큰 유효성 검사에 실패하면 예외 발생
        if (!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        Long memberId = refreshTokenService.findByRefreshToken(refreshToken).getMemberId();
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new NotFoundException(String.format("해당 회원은 존재하지 않습니다. ID[%S]", memberId)));

        return tokenProvider.generateToken(member);
    }

}
