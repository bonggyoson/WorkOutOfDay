//package wods.crossfit.global.config.oauth;
//
//import java.util.Collection;
//import java.util.Map;
//import java.util.Optional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//import wods.crossfit.member.domain.Member;
//import wods.crossfit.member.domain.Role;
//import wods.crossfit.member.repository.MemberRepository;
//
//@RequiredArgsConstructor
//@Service
//public class OAuth2UserCustomService extends DefaultOAuth2UserService {
//
//    private final MemberRepository memberRepository;
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        // 요청을 바탕으로 유저 정보를 담은 객체 반환
//        OAuth2User member = super.loadUser(userRequest);
//        saveOrUpdate(member);
//        return member;
//    }
//
//    // 유저가 있으면 업데이트, 없으면 유저 생성
//    private Member saveOrUpdate(OAuth2User oAuth2User) {
//        Map<String, Object> attributes = oAuth2User.getAttributes();
//        String email = (String) attributes.get("email");
//        String name = (String) attributes.get("name");
//
//        Member member = memberRepository.findByEmail(email).map(entity -> entity.updateName(name))
//                .orElse(Member.builder().email(email).name(name).build());
//
//        return memberRepository.save(member);
//    }
//
//}
