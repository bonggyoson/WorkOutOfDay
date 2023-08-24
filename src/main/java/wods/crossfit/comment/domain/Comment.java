package wods.crossfit.comment.domain;

import static javax.persistence.FetchType.LAZY;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "workout_id")
    private Workout workout;

    @Column(name = "comment_content")
    private String content;

    public void update(String content) {
        this.content = content;
    }
}
