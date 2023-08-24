package wods.crossfit.global.config;

import static java.nio.charset.StandardCharsets.*;

import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Service;

@Service("loginFailHandler")
@Slf4j
public class LoginFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        String errorMsg = "";

        log.info("로그인 실패");
        if (exception instanceof AuthenticationServiceException) {
            errorMsg = "죄송합니다. 시스템에 오류가 발생했습니다.";
        } else if (exception instanceof BadCredentialsException) {
            errorMsg = "아이디 또는 비밀번호가 일치하지 않습니다.";
        } else if (exception instanceof DisabledException) {
            errorMsg = "현재 사용할 수 없는 계정입니다.";
        } else if (exception instanceof LockedException) {
            errorMsg = "현재 잠긴 계정입니다.";
        } else if (exception instanceof AccountExpiredException) {
            errorMsg = "이미 만료된 계정입니다.";
        } else if (exception instanceof CredentialsExpiredException) {
            errorMsg = "비밀번호가 만료된 계정입니다.";
        } else {
            errorMsg = "계정을 찾을 수 없습니다.";
        }

        errorMsg = URLEncoder.encode(errorMsg, UTF_8);
        setDefaultFailureUrl("/login?error=true&exception=" + errorMsg);

        super.onAuthenticationFailure(request, response, exception);

    }
}
