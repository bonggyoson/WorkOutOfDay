package wods.crossfit.comment.domain;

import static javax.persistence.FetchType.LAZY;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import lombok.NoArgsConstructor;
import wods.crossfit.global.common.BaseEntity;
import wods.crossfit.member.domain.Member;
import wods.crossfit.workout.domain.Workout;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout workout;

    @Column(name = "comment_content", nullable = false)
    private String content;

    public void update(String content) {
        this.content = content;
    }

    @Builder
    public Comment(Member member, Workout workout, String content) {
        this.member = member;
        this.workout = workout;
        this.content = content;
    }
}
