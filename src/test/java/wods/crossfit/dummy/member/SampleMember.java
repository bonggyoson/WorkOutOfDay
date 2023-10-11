package wods.crossfit.dummy.member;

import net.datafaker.Faker;
import wods.crossfit.member.domain.Role;
import wods.crossfit.member.domain.dto.MemberDto.MemberRequest;

public class SampleMember {

    public static MemberRequest sampleSaveMemberData() {
        Faker faker = new Faker();
        final String email = faker.expression("#{examplify 'EAMIL'}") + "@gamil.com";
        final String password = faker.expression("#{examplify 'password'}");
        final String name = faker.name().fullName();
        final String box = "골든 크라운 크로스핏 서대문점";

        return new MemberRequest(email, password, name, box, Role.ROLE_MEMBER);
    }

    public static MemberRequest sampleSaveMemberFailedData() {
        Faker faker = new Faker();
        final String password = faker.expression("#{examplify 'password'}");
        final String name = faker.name().fullName();
        final String box = "골든 크라운 크로스핏 서대문점";

        return new MemberRequest("", password, name, box, Role.ROLE_MEMBER);
    }

    public static MemberRequest sampleUpdateMemberData() {
        Faker faker = new Faker();
        final String email = faker.expression("#{examplify 'EAMIL'}") + "@gamil.com";
        final String password = faker.expression("#{examplify 'password'}");
        final String name = faker.name().fullName();
        final String box = "골든 크라운 크로스핏 서대문점";

        return new MemberRequest(email, password, name, box, Role.ROLE_MEMBER);
    }



}
