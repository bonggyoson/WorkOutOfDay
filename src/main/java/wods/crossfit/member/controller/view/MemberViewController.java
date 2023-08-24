package wods.crossfit.member.controller.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wods.crossfit.member.domain.dto.MemberDto.MemberRequest;
import wods.crossfit.member.service.MemberService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberViewController {

    private final MemberService memberService;

    /**
     * 로그인
     */
    @GetMapping("/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "exception", required = false) String exception,
            Model model) {

        model.addAttribute("error", error);
        model.addAttribute("exception", exception);

        return "login/login";
    }

    /**
     * 로그아웃
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response,
                SecurityContextHolder.getContext().getAuthentication());

        return "redirect:/login";
    }

    /**
     * 회원가입 페이지
     */
    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("member", new MemberRequest());
        return "login/signup";
    }

    /**
     * 패스워드 초기화 페이지
     */
    @GetMapping("/resetPassword")
    public String resetPassword() {
        return "login/resetPassword";
    }

    /**
     * 패스워드 초기화
     */
    @PostMapping("/resetPassword")
    public String resetPassword(
            @Validated @RequestParam @NotBlank(message = "이메일을 입력해주세요") String email,
            Model model) {

        model.addAttribute("newPassword", memberService.resetPassword(email));

        return "login/resetPassword";
    }

}
