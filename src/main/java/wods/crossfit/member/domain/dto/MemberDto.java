package wods.crossfit.member.domain.dto;

import static wods.crossfit.member.domain.Role.ROLE_MEMBER;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wods.crossfit.member.domain.Member;
import wods.crossfit.member.domain.Role;

public class MemberDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberRequest {

        @NotBlank(message = "이메일을 입력해주세요.")
        @Email
        @Schema(name = "email", description = "이메일", example = "test@test.com")
        private String email;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Schema(name = "password", description = "패스워드", example = "password")
        private String password;

        @NotBlank(message = "이름을 입력해주세요.")
        @Schema(name = "name", description = "이름", example = "홍길동")
        private String name;

        @Schema(name = "box", description = "박스", example = "골든크라운 크로스핏")
        private String box;

        @Schema(name = "role", description = "역할", example = "관리자, 일반 사용자")
        private Role role;

        public void encryptPassword(String BCryptpassword) {
            this.password = BCryptpassword;
        }

        public Member toEntity() {
            return Member.builder()
                    .email(email)
                    .password(password)
                    .name(name)
                    .box(box)
                    .role(ROLE_MEMBER)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberSearchCondition {

        private String email;
        private String name;
        private String box;

    }

    @Getter
    @NoArgsConstructor
    public static class MemberResponse {

        private Long id;
        private String email;
        private String name;
        private String box;
        private Role role;

        public MemberResponse(Member member) {
            this.id = member.getId();
            this.email = member.getEmail();
            this.name = member.getName();
            this.box = member.getBox();
            this.role = member.getRole();
        }

        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        public static class TokenInfo {

            private String accessToken;
            private String refreshToken;
        }
    }

}
