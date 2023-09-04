package wods.crossfit.workout.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.xml.bind.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import wods.crossfit.global.common.CommonResponse;
import wods.crossfit.global.common.ErrorResponse;
import wods.crossfit.global.common.ResponseMessage;
import wods.crossfit.workout.domain.dto.WorkoutDto.WorkoutRequest;
import wods.crossfit.workout.domain.dto.WorkoutDto.WorkoutResponse;
import wods.crossfit.workout.service.WorkoutService;

@Tag(name = "오늘의 운동 API Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/workout")
@Slf4j
public class WorkoutApiController {

    private final WorkoutService workoutService;

    /**
     * 오늘의 운동 저장
     */
    @Operation(summary = "post save workout api", responses = {
            @ApiResponse(responseCode = "201", description = "오늘의 운동 저장 성공"),
            @ApiResponse(responseCode = "400", description = "오늘의 운동 저장 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "오늘의 운동 저장 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("")
    public ResponseEntity<CommonResponse<WorkoutResponse>> saveWorkout(
            @Validated @RequestBody final WorkoutRequest dto, BindingResult bindingResult)
            throws ValidationException {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(ResponseMessage.CREATED_WORKOUT_FAIL);
        }

        workoutService.saveWorkout(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.res(HttpStatus.CREATED, ResponseMessage.CREATED_WORKOUT));
    }

    /**
     * 오늘의 운동 수정
     */
    @Operation(summary = "put update workout api", responses = {
            @ApiResponse(responseCode = "200", description = "오늘의 운동 수정 성공"),
            @ApiResponse(responseCode = "400", description = "오늘의 운동 수정 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "오늘의 운동 수정 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<WorkoutResponse>> updateWorkout(@PathVariable long id,
            @Validated @RequestBody final WorkoutRequest dto, BindingResult bindingResult)
            throws ValidationException {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(ResponseMessage.UPDATE_COMMENT_FAIL);
        }

        workoutService.updateWorkout(id, dto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.res(HttpStatus.OK, ResponseMessage.UPDATE_WORKOUT));
    }

    /**
     * 오늘의 운동 삭제
     */
    @Operation(summary = "delete delete workout api", responses = {
            @ApiResponse(responseCode = "200", description = "오늘의 운동 삭제 성공"),
            @ApiResponse(responseCode = "500", description = "오늘의 운동 삭제 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<WorkoutResponse>> deleteWorkout(@PathVariable long id) {

        workoutService.deleteWorkout(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.res(HttpStatus.OK, ResponseMessage.DELETE_WORKOUT));
    }
}
