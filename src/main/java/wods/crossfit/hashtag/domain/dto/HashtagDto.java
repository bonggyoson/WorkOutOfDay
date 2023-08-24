package wods.crossfit.hashtag.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wods.crossfit.hashtag.domain.Hashtag;
import wods.crossfit.workout.domain.Workout;

public class HashtagDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HashtagRequest {

        private String content;
        public Hashtag toEntity(String content, Workout workout) {
            return Hashtag.builder()
                    .content(content)
                    .workout(workout)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HashtagResponse {

        private long id;
        private String content;
        private long workoutId;

        public HashtagResponse(Hashtag hashtag) {
            this.id = hashtag.getId();
            this.content = hashtag.getContent();
            this.workoutId = hashtag.getWorkout().getId();
        }
    }

}
