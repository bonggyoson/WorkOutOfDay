package wods.crossfit.global.config.jwt;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.webjars.NotFoundException;
import wods.crossfit.member.domain.Member;
import wods.crossfit.member.repository.MemberRepository;

// 시큐리티가 filter를 가지고 있는데 그 필터중에 BasicAuthenticationFilter 라는 것이 있다.
// 권한이나 인증이 필요한 특정 주소를 요청했을 때 위 필터를 무조건 타게 되어 있다.
// 만약에 권한이나 인증이 필요한 주소가 아니라면 이 필터를 안탄다.
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final MemberRepository memberRepository;

    private final TokenProvider tokenProvider;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
            MemberRepository memberRepository, TokenProvider tokenProvider) {
        super(authenticationManager);
        this.memberRepository = memberRepository;
        this.tokenProvider = tokenProvider;
    }

    // 인증이나 권한이 필요한 주소요청이 있을 때 해당 필터를 타게 됨.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        System.out.println("인증이나 권한이 필요한 주소 요청");

        if (request.getCookies() != null) {
            Cookie[] cookies = request.getCookies();
            Cookie cookie = cookies[0];
            String token = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
            log.info("token : {}", token);

            if (token == null || !token.startsWith("Bearer")) {
                chain.doFilter(request, response);
                return;
            }
            // JWT 토큰을 검증을 해서 정상적인 사용자인지 확인
            String jwtToken = token
                    .replace(JwtProperties.TOKEN_PREFIX, "");

            if (tokenProvider.validToken(jwtToken)) {
                Long memberId = tokenProvider.getMemberId(jwtToken);

                // 서명이 정상적으로 됨
                if (memberId != null) {
                    Member memberEntity = memberRepository.findById(memberId).orElseThrow(
                            () -> new NotFoundException(
                                    String.format("해당 회원은 존재하지 않습니다. [%S]", memberId)));

                    // Jwt 토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어 준다.
                    Authentication authentication =
                            new UsernamePasswordAuthenticationToken(memberEntity, null,
                                    memberEntity.getAuthorities());

                    // 강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장.
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
                System.out.println("인증 성공");
                chain.doFilter(request, response);
            } else {
                System.out.println("인증 실패");
                chain.doFilter(request, response);
            }
        } else {
            System.out.println("쿠키 없음");
            chain.doFilter(request, response);
        }

    }
}
