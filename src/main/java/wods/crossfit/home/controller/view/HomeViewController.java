package wods.crossfit.home.controller.view;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import wods.crossfit.workout.service.WorkoutService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeViewController {

    private final WorkoutService workoutService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("workout", workoutService.findHotWorkout());

        return "home";
    }
}