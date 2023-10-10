package wods.crossfit.workoutHashtag.service;

import java.util.List;
import wods.crossfit.hashtag.domain.Hashtag;

public interface WorkoutHashtagService {

    void saveWorkoutHashtag(long savedWorkoutId, List<Hashtag> hashtags);
}
