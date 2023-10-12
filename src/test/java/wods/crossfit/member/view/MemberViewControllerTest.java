package wods.crossfit.member.view;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wods.crossfit.common.CommonTestConfig;
import wods.crossfit.member.domain.Member;
import wods.crossfit.member.repository.MemberRepository;

public class MemberViewControllerTest extends CommonTestConfig {

    @Autowired
    protected MemberRepository memberRepository;

    @DisplayName("회원 조회 api 테스트")
    @Test
    public void findMembers() throws Exception {
        // Given

        // When
        List<Member> members = memberRepository.findAll();


        // Then
    }

    @DisplayName("회원 검색 api 테스트")
    @Test
    public void search() throws Exception {
        // Given
        final String url = "";

        // When
        Optional<Member> member = memberRepository.findById(7L);

        // Then
        System.out.println("member = " + member);
    }

}
