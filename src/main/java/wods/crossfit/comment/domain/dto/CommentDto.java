package wods.crossfit.comment.domain.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wods.crossfit.comment.domain.Comment;
import wods.crossfit.member.domain.Member;
import wods.crossfit.member.domain.dto.MemberDto.MemberResponse;
import wods.crossfit.workout.domain.Workout;
import wods.crossfit.workout.domain.dto.WorkoutDto.WorkoutResponse;

public class CommentDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentRequest {

        @NotBlank(message = "댓글 내용은 필수 입니다.")
        private String content;
        private long memberId;
        private long workoutId;

        public Comment toEntity(Workout workout, Member member) {
            return Comment.builder()
                    .member(member)
                    .workout(workout)
                    .content(content)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentResponse {

        private Long id;
        private String content;
        private MemberResponse member;
        private WorkoutResponse workout;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public CommentResponse(Comment comment) {
            this.id = comment.getId();
            this.content = comment.getContent();
            this.member = new MemberResponse(comment.getMember());
            this.workout = WorkoutResponse.commentOf(comment.getWorkout());
            this.createdAt = comment.getCreatedAt();
            this.updatedAt = comment.getUpdatedAt();
        }
    }
}
