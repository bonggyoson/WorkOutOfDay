package wods.crossfit.workout.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import wods.crossfit.comment.domain.Comment;
import wods.crossfit.heart.domain.Heart;
import wods.crossfit.global.common.BaseEntity;

import javax.persistence.*;
import wods.crossfit.member.domain.Member;
import wods.crossfit.workoutHashtag.domain.WorkoutHashtag;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class Workout extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workout_id")
    private Long id;

    @Column(name = "workout_title", nullable = false)
    private String title;

    @Column(name = "workout_content", nullable = false)
    private String content;

    @Column(name = "workout_views", columnDefinition = "BIGINT default 0")
    private Long views;

    @Column(name = "workout_heart_count", columnDefinition = "BIGINT default 0")
    private Long heartCount;

    @Column(name = "workout_comment_count", columnDefinition = "BIGINT default 0")
    private Long commentCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OrderBy("id desc")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "workout", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "workout", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OrderBy("id asc")
    private List<WorkoutHashtag> hashtags = new ArrayList<>();

    @Builder
    public Workout(String title, String content, Member member, long views,
            long heartCount, long commentCount) {
        this.title = title;
        this.content = content;
        this.member = member;
        this.views = views;
        this.heartCount = heartCount;
        this.commentCount = commentCount;
    }

    // 오늘의 운동 수정
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 오늘의 운동 조회수 증가
    public void updateViewCount(long views) {
        this.views = views + 1L;
    }

    // 오늘의 운동 좋아요
    public void increaseHeartCount() {
        this.heartCount++;
    }

    // 오늘의 운동 좋아요 취소
    public void decreaseHeartCount() {
        this.heartCount--;
    }

    // 오늘의 운동 댓글 추가
    public void increaseCommentCount() {
        this.commentCount++;
    }

    // 오늘의 운동 댓글 삭제
    public void decreaseCommentCount() {
        this.commentCount--;
    }
}
