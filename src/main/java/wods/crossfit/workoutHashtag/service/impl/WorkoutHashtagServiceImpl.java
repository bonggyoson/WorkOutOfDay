package wods.crossfit.workoutHashtag.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wods.crossfit.hashtag.domain.Hashtag;
import wods.crossfit.workout.domain.Workout;
import wods.crossfit.workout.repository.WorkoutRepository;
import wods.crossfit.workoutHashtag.domain.dto.WorkoutHashtagDto.WorkoutHashtagRequest;
import wods.crossfit.workoutHashtag.repository.WorkoutHashtagRepository;
import wods.crossfit.workoutHashtag.service.WorkoutHashtagService;

@Service
@RequiredArgsConstructor
public class WorkoutHashtagServiceImpl implements WorkoutHashtagService {

    private final WorkoutRepository workoutRepository;

    private final WorkoutHashtagRepository workoutHashtagRepository;

    @Override
    public void saveWorkoutHashtag(long savedWorkoutId, List<Hashtag> hashtags) {
        Workout workout = workoutRepository.findById(savedWorkoutId)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Not Found Id : [%s]", savedWorkoutId)));

        workoutHashtagRepository.saveAll(
                new WorkoutHashtagRequest().toEntity(workout, hashtags));
    }
}
