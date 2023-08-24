package wods.crossfit.hashtag.service.impl;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import wods.crossfit.hashtag.domain.Hashtag;
import wods.crossfit.hashtag.domain.dto.HashtagDto;
import wods.crossfit.hashtag.repository.HashtagRepository;
import wods.crossfit.hashtag.service.HashtagService;
import wods.crossfit.workout.domain.Workout;
import wods.crossfit.workout.repository.WorkoutRepository;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

    private final HashtagRepository hashtagRepository;

    private final WorkoutRepository workoutRepository;

    @Override
    public void saveHashtag(List<HashtagDto.HashtagRequest> hashtags, long id) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 오늘의 운동이 존재하지 않습니다."));

        List<Hashtag> tags = new ArrayList<>();
        for (HashtagDto.HashtagRequest hashtag : hashtags) {
            tags.add(new HashtagDto.HashtagRequest().toEntity(hashtag.getContent(),
                    workout));
        }
        hashtagRepository.saveAll(tags);
    }

    @Override
    public List<String> getHashtags() {
        return hashtagRepository.findHashtags();
    }
}
