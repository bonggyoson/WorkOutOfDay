package wods.crossfit.workout.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import wods.crossfit.workout.domain.dto.WorkoutDto;
import wods.crossfit.workout.domain.dto.WorkoutDto.WorkoutRequest;
import wods.crossfit.workout.domain.dto.WorkoutDto.WorkoutResponse;

public interface WorkoutService {

    Page<WorkoutResponse> getWorkouts(Pageable pageable, String keyword, String type,
            String sort, String hashtag);

    WorkoutResponse getWorkout(long id);

    long saveWorkout(WorkoutDto.WorkoutRequest dto);

    void updateWorkout(long id, WorkoutRequest request);

    void deleteWorkout(long id);

    Page<WorkoutResponse> findAllByMemberId(Pageable pageable, long id);
}
