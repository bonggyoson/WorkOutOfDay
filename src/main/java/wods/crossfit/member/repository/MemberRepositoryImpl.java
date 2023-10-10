package wods.crossfit.member.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import wods.crossfit.member.domain.Member;
import wods.crossfit.member.domain.dto.MemberDto.MemberSearchCondition;

import static wods.crossfit.member.domain.QMember.member;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public MemberRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    private BooleanExpression emailEq(String email) {
        if (email == null) {
            return null;
        }
        return member.email.eq(email);
    }

    private BooleanExpression nameEq(String name) {
        if (name == null) {
            return null;
        }
        return member.name.eq(name);
    }

    private BooleanExpression boxEq(String box) {
        if (box == null) {
            return null;
        }
        return member.box.eq(box);
    }

    /**
     * 회원 조회
     *
     * @return
     */
    @Override
    public Page<Member> findMember(Pageable pageable, MemberSearchCondition memberSearchCondition) {
        QueryResults<Member> result = jpaQueryFactory
                .selectFrom(member)
                .where(emailEq(memberSearchCondition.getEmail()), nameEq(
                                memberSearchCondition.getName()),
                        boxEq(memberSearchCondition.getBox()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(member.id.asc())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    /**
     * 회원 단건 조회
     *
     * @param id
     * @return
     */
    @Override
    public Member findMember(long id) {
        return jpaQueryFactory
                .selectFrom(member)
                .where(member.id.eq(id))
                .fetchOne();
    }
}
