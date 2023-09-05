package wods.crossfit.hashtag.service.impl;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import wods.crossfit.hashtag.domain.Hashtag;
import wods.crossfit.hashtag.domain.dto.HashtagDto;
import wods.crossfit.hashtag.repository.HashtagRepository;
import wods.crossfit.hashtag.service.HashtagService;
import wods.crossfit.workout.domain.Workout;
import wods.crossfit.workout.repository.WorkoutRepository;
import wods.crossfit.workoutHashtag.domain.WorkoutHashtag;
import wods.crossfit.workoutHashtag.domain.dto.WorkoutHashtagDto;
import wods.crossfit.workoutHashtag.repository.WorkoutHashtagRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HashtagServiceImpl implements HashtagService {

    private final HashtagRepository hashtagRepository;

    private final WorkoutRepository workoutRepository;

    private final WorkoutHashtagRepository workoutHashtagRepository;

    @Override
    @Transactional
    public void saveHashtag(List<HashtagDto.HashtagRequest> hashtags, long id) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 오늘의 운동이 존재하지 않습니다."));

        List<Hashtag> tags = new ArrayList<>();
        for (HashtagDto.HashtagRequest hashtag : hashtags) {
            tags.add(new HashtagDto.HashtagRequest().toEntity(hashtag.getContent()));
        }

        List<Hashtag> resultHashtag = hashtagRepository.saveAll(tags);
        List<WorkoutHashtag> workoutHashtags = new ArrayList<>();
        for (Hashtag hashtag : resultHashtag) {
            workoutHashtags.add(
                    new WorkoutHashtagDto.WorkoutHashtagRequest().toEntity(workout, hashtag));
        }

        workoutHashtagRepository.saveAll(workoutHashtags);

    }

    @Override
    public List<String> getHashtags() {
        return hashtagRepository.findHashtags();
    }
}
