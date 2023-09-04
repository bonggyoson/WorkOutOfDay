package wods.crossfit.workoutHashtag.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wods.crossfit.workoutHashtag.domain.WorkoutHashtag;
import wods.crossfit.workoutHashtag.domain.dto.WorkoutHashtagDto.WorkoutHashtagResponse;

public interface WorkoutHashtagRepository extends JpaRepository<WorkoutHashtag, Long> {

    List<WorkoutHashtagResponse> findByWorkoutId(long id);

    void deleteByWorkoutId(long id);
}
