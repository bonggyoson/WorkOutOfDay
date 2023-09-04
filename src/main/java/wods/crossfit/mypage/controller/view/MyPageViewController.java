package wods.crossfit.mypage.controller.view;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wods.crossfit.benchmark.domain.dto.BenchmarkDto.BenchmarkResponse;
import wods.crossfit.benchmark.service.BenchmarkService;
import wods.crossfit.comment.service.CommentService;
import wods.crossfit.hashtag.domain.dto.HashtagDto.HashtagResponse;
import wods.crossfit.profile.domain.dto.ProfileDto.ProfileResponse;
import wods.crossfit.profile.service.ProfileService;
import wods.crossfit.workout.service.WorkoutService;
import wods.crossfit.workoutHashtag.domain.dto.WorkoutHashtagDto.WorkoutHashtagResponse;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/myPage")
public class MyPageViewController {


    private final WorkoutService workoutService;

    private final CommentService commentService;

    private final BenchmarkService benchmarkService;

    private final ProfileService profileService;

    /**
     * 마이 페이지 내 프로필
     */
    @GetMapping("/myProfile")
    public String myProfile(Model model, @RequestParam long id) {

        ProfileResponse profile = profileService.findByMemberId(id);

        List<BenchmarkResponse> benchmarks = benchmarkService.getBenchmarks().stream()
                .filter(x -> profile.getProfileBenchmarks().stream()
                        .noneMatch(n -> x.getId() == n.getBenchmark()
                                .getId())).collect(Collectors.toList());

        model.addAttribute("benchmarks", benchmarks);
        model.addAttribute("profile", profile);

        return "mypage/myProfile";
    }

    /**
     * 마이 페이지 회원 정보 페이지
     */
    @GetMapping("")
    public String myPage() {
        return "mypage/myPage";
    }

    /**
     * 마이페이지 오늘의 운동 수정 페이지
     */
    @GetMapping("/updateWorkout/{id}")
    public String updateMyWorkout(Model model, @PathVariable long id) {

        model.addAttribute("workout", workoutService.getWorkout(id));
        model.addAttribute("hashtags", workoutService.getWorkout(id).getWorkoutHashtags().stream()
                .map(WorkoutHashtagResponse::getHashtag).map(HashtagResponse::getContent).collect(
                        Collectors.toList()));

        return "mypage/updateWorkoutForm";
    }

    /**
     * 마이 페이지 내 게시글 페이지
     */
    @GetMapping("/myWorkout")
    public String myWorkout(Model model, @RequestParam long id,
            Pageable pageable) {

        model.addAttribute("workouts", workoutService.findAllByMemberId(pageable, id));

        return "mypage/myWorkout";
    }

    /**
     * 마이 페이지 내 댓글 페이지
     */
    @GetMapping("/myComment")
    public String myComment(Model model, @RequestParam long id,
            Pageable pageable) {

        model.addAttribute("comments", commentService.getCommentsByMemberId(pageable, id));

        return "mypage/myComment";
    }
}
