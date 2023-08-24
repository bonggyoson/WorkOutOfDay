package wods.crossfit.heart.domain;

import static javax.persistence.FetchType.LAZY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wods.crossfit.member.domain.Member;
import wods.crossfit.workout.domain.Workout;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "heart_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "workout_id")
    private Workout workout;

    @Builder
    public Heart(Member member, Workout workout) {
        this.member = member;
        this.workout = workout;
    }
}
