//package wods.crossfit.global.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.oauth2.client.OAuth2AuthorizationSuccessHandler;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.HttpStatusEntryPoint;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import wods.crossfit.global.config.jwt.TokenProvider;
//import wods.crossfit.global.config.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
//import wods.crossfit.global.config.oauth.OAuth2SuccessHandler;
//import wods.crossfit.global.config.oauth.OAuth2UserCustomService;
//import wods.crossfit.member.repository.MemberRepository;
//import wods.crossfit.member.service.MemberDetailService;
//import wods.crossfit.member.service.MemberService;
//import wods.crossfit.token.repository.RefreshTokenRepository;
//
//@RequiredArgsConstructor
//@Configuration
//public class WebOAuthSecurityConfig {
//
//    private final OAuth2UserCustomService oAuth2UserCustomService;
//    private final TokenProvider tokenProvider;
//    private final RefreshTokenRepository refreshTokenRepository;
//    private final MemberRepository memberRepository;
//    private final MemberDetailService memberService;
//
//    // 스프링 시큐리티 기능 비활성화
//    @Bean
//    public WebSecurityCustomizer configure() {
//        return (web) -> web.ignoring()
//                .antMatchers("/static/**", "/api*", "/api-docs/**",
//                        "/swagger-ui/**");
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        // 토큰 방식으로 인증을 하기 때문에 기존에 사용하던 폼로그인, 세션 비활성화
//        http.csrf().disable()
//                .httpBasic().disable()
//                .formLogin().disable()
//                .logout().disable();
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        // 헤더를 확일할 커스텀 필터 추가
//        http.addFilterBefore(tokenAuthenticationFilter(),
//                UsernamePasswordAuthenticationFilter.class);
//
//        // 토큰 재발급 URL은 인증 없이 접근 가능하도록 설정. 나머지 API URL은 인증 필요
//        http.authorizeHttpRequests()
//                .antMatchers("/login", "/api/members", "/", "/workout", "/signup", "/qa",
//                        "/resetPassword", "/member/**", "/box", "/api/tokens").permitAll()
//                .antMatchers("/api/**").authenticated()
//                .anyRequest().permitAll();
//
//        http.oauth2Login().loginPage("/login")
//                .authorizationEndpoint()
//                // Authorization 요청과 관련된 상태 저장
//                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
//                .and()
//                .successHandler(oAuthSuccessHandler())
//                .userInfoEndpoint()
//                .userService(oAuth2UserCustomService);
//
//        http.logout()
//                .logoutSuccessUrl("/login");
//
//        // /api로 시작하는 url인 경우 401 상태 코드를 반환하도록 예외 처리
//        http.exceptionHandling().defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(
//                HttpStatus.UNAUTHORIZED), new AntPathRequestMatcher("/api/**"));
//
//        return http.build();
//    }
//
//    @Bean
//    public OAuth2SuccessHandler oAuthSuccessHandler() {
//        return new OAuth2SuccessHandler(tokenProvider, refreshTokenRepository,
//                oAuth2AuthorizationRequestBasedOnCookieRepository(), memberRepository);
//    }
//
//    // 인증 관리자 관련 설정
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http,
//            BCryptPasswordEncoder bCryptPasswordEncoder, MemberDetailService memberDetailService)
//            throws Exception {
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(memberService)    // 사용자 정보 서비스 설정
//                .passwordEncoder(bCryptPasswordEncoder)
//                .and()
//                .build();
//    }
//
//    @Bean
//    public JwtAuthenticationFilter tokenAuthenticationFilter() {
//        return new JwtAuthenticationFilter(tokenProvider);
//    }
//
//    @Bean
//    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
//        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
//    }
//
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//}
