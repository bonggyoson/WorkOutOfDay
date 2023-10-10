package wods.crossfit.admin.controller.view;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wods.crossfit.member.domain.dto.MemberDto.MemberSearchCondition;
import wods.crossfit.member.service.MemberService;
import wods.crossfit.workout.service.WorkoutService;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin")
public class AdminViewController {

    private final MemberService memberService;

    private final WorkoutService workoutService;

    /**
     * 관리자 - 회원 관리
     */
    @GetMapping("/members")
    public String memberList(Model model,
            @PageableDefault(sort = "id", direction = Direction.ASC) Pageable pageable,
            final MemberSearchCondition memberSearchCondition,
            @RequestParam(required = false) String type) {

        model.addAttribute("members", memberService.findMember(pageable, memberSearchCondition));
        model.addAttribute("type", type);

        return "admin/memberManagement";
    }

    /**
     * 관리자 - 회원 상세 보기
     */
    @GetMapping("/members/{id}")
    public String memberDetails(@PathVariable long id, Model model) {

        model.addAttribute("member", memberService.findMember(id));

        return "admin/memberDetail";
    }


    /**
     * 관리자 - 게시글 관리
     *
     * @param model
     * @return
     */
    @GetMapping("/workouts")
    public String findWorkouts(Model model) {

        model.addAttribute("workouts", workoutService.findWorkouts());

        return "admin/workoutManagement";
    }

    // 관리자 - 박스 관리
    @GetMapping("/boxes")
    public String findBoxes(Model model) {

//        model.addAttribute("boxes", box)

        return "admin/boxManagement";
    }
}
