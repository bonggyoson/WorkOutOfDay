package wods.crossfit.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.filter.CorsFilter;
import wods.crossfit.global.config.jwt.JwtAuthorizationFilter;
import wods.crossfit.global.config.jwt.JwtProperties;
import wods.crossfit.global.config.jwt.JwtAuthenticationFilter;
import wods.crossfit.global.config.jwt.TokenProvider;
import wods.crossfit.member.repository.MemberRepository;
import wods.crossfit.member.service.MemberDetailService;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final MemberDetailService memberService;

    private final LoginFailHandler loginFailHandler;

    private final JwtProperties jwtProperties;

    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    private final CorsFilter corsFilter;

    private final TokenProvider tokenProvider;

    private final MemberRepository memberRepository;

    // 스프링 시큐리티 기능 비활성화
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .antMatchers("/static/**", "/api*", "/api-docs/**",
                        "/swagger-ui/**");
    }

    // 특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic().disable()
                .formLogin().disable()
                .addFilter(corsFilter)

                .exceptionHandling()
//                .authenticationEntryPoint(customAuthenticationEntryPoint)
//                .accessDeniedHandler(customAccessDeniedHandler)a
                .and()
                .authorizeRequests()
                .antMatchers("/login", "/signup", "/resetPassword",
                        "/", "/workout", "/qa", "/box",
                        "/api/members/**").permitAll()
                .antMatchers("/admin").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilter(
                        new JwtAuthenticationFilter(
                                authenticationManager(http, bCryptPasswordEncoder(), null),
                                tokenProvider, jwtProperties))
                .addFilter(
                        new JwtAuthorizationFilter(
                                authenticationManager(http, bCryptPasswordEncoder(), null),
                                memberRepository, tokenProvider));

        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        CustomAccessDeniedHandler accessDeniedHandler = new CustomAccessDeniedHandler();
        accessDeniedHandler.setErrorURL("/auth/denied");

        return accessDeniedHandler;
    }


    // 인증 관리자 관련 설정
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
            BCryptPasswordEncoder bCryptPasswordEncoder, MemberDetailService memberDetailService)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(memberService)    // 사용자 정보 서비스 설정
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

    // 패스워드 인코더로 사용할 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
