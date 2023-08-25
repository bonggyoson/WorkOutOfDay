package wods.crossfit.member.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wods.crossfit.member.domain.Member;

public class MemberDto {

    @Getter
    @NoArgsConstructor
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

        public void encryptPassword(String BCryptpassword) {
            this.password = BCryptpassword;
        }

        public Member toEntity() {
            return Member.builder()
                    .email(email)
                    .password(password)
                    .name(name)
                    .box(box)
                    .build();
        }

        @Builder
        public MemberRequest(String email, String password, String name, String box) {
            this.email = email;
            this.password = password;
            this.name = name;
            this.box = box;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class MemberResponse {

        private Long id;
        private String email;
        private String password;
        private String name;
        private String box;

        public MemberResponse(Member member) {
            this.id = member.getId();
            this.email = member.getEmail();
            this.password = member.getPassword();
            this.name = member.getName();
            this.box = member.getBox();
        }
    }

}
