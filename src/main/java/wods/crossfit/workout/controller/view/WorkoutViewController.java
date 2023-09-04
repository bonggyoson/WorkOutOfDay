package wods.crossfit.workout.controller.view;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wods.crossfit.member.domain.Member;
import wods.crossfit.heart.service.HeartService;
import wods.crossfit.profile.domain.dto.ProfileDto.ProfileResponse;
import wods.crossfit.profile.service.ProfileService;
import wods.crossfit.workout.domain.dto.WorkoutDto.WorkoutRequest;
import wods.crossfit.workout.domain.dto.WorkoutDto.WorkoutResponse;
import wods.crossfit.workout.service.WorkoutService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/workout")
@Slf4j
public class WorkoutViewController {

    private final WorkoutService workoutService;

    private final HeartService heartService;

    private final ProfileService profileService;

    /**
     * 오늘의 운동 페이지
     */
    @GetMapping("")
    public String getWorkouts(Model model, Pageable pageable,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false, defaultValue = "") String hashtag) {

        model.addAttribute("workouts", workoutService.getWorkouts(pageable, keyword,
                type, sort, hashtag));
        model.addAttribute("keyword", keyword);
        model.addAttribute("type", type);
        model.addAttribute("sort", sort);

        return "workout/workout";
    }

    /**
     * 오늘의 운동 상세 페이지
     */
    @GetMapping("/{id}")
    public String getWorkout(@PathVariable long id, Model model, Authentication auth) {

        WorkoutResponse workout = workoutService.getWorkout(id);
        ProfileResponse profile = profileService.findByMemberId(workout.getMember().getId());
        Member member = (Member) auth.getPrincipal();

        if (!heartService.checkLike(member.getId(), id)) {
            model.addAttribute("heartCheck", false);
        }

        model.addAttribute("workout", workout);
        model.addAttribute("profile", profile);

        return "workout/workoutDetails";
    }

    /**
     * 오늘의 운동 등록 페이지
     */
    @GetMapping("/addWorkout")
    public String addWorkout(Model model) {
        model.addAttribute("workout", new WorkoutRequest());

        return "workout/addWorkoutForm";
    }

}
