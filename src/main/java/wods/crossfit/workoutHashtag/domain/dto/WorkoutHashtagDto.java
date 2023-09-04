package wods.crossfit.workoutHashtag.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wods.crossfit.hashtag.domain.Hashtag;
import wods.crossfit.hashtag.domain.dto.HashtagDto.HashtagRequest;
import wods.crossfit.hashtag.domain.dto.HashtagDto.HashtagResponse;
import wods.crossfit.workout.domain.Workout;
import wods.crossfit.workout.domain.dto.WorkoutDto.WorkoutRequest;
import wods.crossfit.workoutHashtag.domain.WorkoutHashtag;

public class WorkoutHashtagDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WorkoutHashtagRequest {

        private WorkoutRequest workout;
        private HashtagRequest hashtag;

        public WorkoutHashtag toEntity(Workout workout, Hashtag hashtag) {
            return WorkoutHashtag.builder()
                    .workout(workout)
                    .hashtag(hashtag)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WorkoutHashtagResponse {

        private HashtagResponse hashtag;

        public WorkoutHashtagResponse(WorkoutHashtag workoutHashtag) {
            this.hashtag = new HashtagResponse(workoutHashtag.getHashtag());
        }
    }

}
