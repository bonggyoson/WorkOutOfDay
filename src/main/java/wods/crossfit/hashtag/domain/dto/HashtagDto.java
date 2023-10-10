package wods.crossfit.hashtag.domain.dto;

import java.util.List;
import java.util.stream.Collectors;
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

        public Hashtag toEntity(String content) {
            return Hashtag.builder()
                    .content(content)
                    .build();
        }

        public List<Hashtag> toEntity(List<HashtagRequest> hashtags) {
            return hashtags.stream().map(x -> x.toEntity(x.getContent()))
                    .collect(Collectors.toList());
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HashtagResponse {

        private long id;
        private String content;

        public HashtagResponse(Hashtag hashtag) {
            this.id = hashtag.getId();
            this.content = hashtag.getContent();
        }
    }

}
