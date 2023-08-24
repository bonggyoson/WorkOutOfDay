package wods.crossfit.heart.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wods.crossfit.heart.domain.Heart;
import wods.crossfit.member.domain.Member;
import wods.crossfit.workout.domain.Workout;

public class HeartDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HeartRequest {

        private long memberId;
        private long workoutId;

        public Heart toEntity(Member member, Workout workout) {
            return Heart.builder()
                    .member(member)
                    .workout(workout)
                    .build();
        }

    }

}
