package wods.crossfit.member.domain;

public enum Grade {
    MEMBER("회원"), ADMIN("관리자"), USER("사용자");

    private final String description;

    Grade(String description) {
        this.description = description;
    }
}
