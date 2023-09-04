package wods.crossfit.workout.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wods.crossfit.comment.domain.dto.CommentDto.CommentResponse;
import wods.crossfit.hashtag.domain.dto.HashtagDto;
import wods.crossfit.member.domain.Member;
import wods.crossfit.member.domain.dto.MemberDto.MemberResponse;
import wods.crossfit.workout.domain.Workout;
import wods.crossfit.workoutHashtag.domain.dto.WorkoutHashtagDto.WorkoutHashtagResponse;

public class WorkoutDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WorkoutRequest {

        @NotBlank(message = "제목을 입력해주세요")
        @Schema(name = "title", example = "FRAN")
        private String title;

        @NotBlank(message = "본문을 입력해주세요")
        @Schema(name = "content", example = "21-15-9 THRUSTERS PULL-UPS ")
        private String content;

        @Schema(name = "hashtag", example = "Crossfit")
        private List<HashtagDto.HashtagRequest> hashtag;

        @NotNull
        @Schema(name = "memberId", example = "1")
        private Long memberId;

        public Workout toEntity(Member member) {
            return Workout.builder()
                    .title(title)
                    .content(content)
                    .member(member)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class WorkoutResponse {

        private Long id;
        private String title;
        private String content;
        private Long views;
        private Long heartCount;
        private MemberResponse member;
        private List<CommentResponse> comments;
        private LocalDateTime createdAt;
        private Long commentCount;
        private List<WorkoutHashtagResponse> workoutHashtags;

        public static WorkoutResponse of(Workout workout) {
            return WorkoutResponse.builder()
                    .id(workout.getId())
                    .title(workout.getTitle())
                    .content(workout.getContent())
                    .views(workout.getViews())
                    .heartCount(workout.getHeartCount())
                    .member(new MemberResponse(workout.getMember()))
                    .comments(workout.getComments().stream()
                            .map(CommentResponse::new)
                            .collect(Collectors.toList()))
                    .createdAt(workout.getCreatedAt())
                    .commentCount(workout.getCommentCount())
                    .workoutHashtags(workout.getHashtags().stream()
                            .map(WorkoutHashtagResponse::new)
                            .collect(Collectors.toList()))
                    .build();
        }

        public static WorkoutResponse commentOf(Workout workout) {
            return WorkoutResponse.builder()
                    .id(workout.getId())
                    .title(workout.getTitle())
                    .content(workout.getContent())
                    .views(workout.getViews())
                    .heartCount(workout.getHeartCount())
                    .member(new MemberResponse(workout.getMember()))
                    .createdAt(workout.getCreatedAt())
                    .commentCount(workout.getCommentCount())
                    .build();
        }
    }

}
