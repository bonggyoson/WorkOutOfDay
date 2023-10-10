package wods.crossfit.hashtag.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wods.crossfit.hashtag.domain.Hashtag;
import wods.crossfit.hashtag.domain.dto.HashtagDto.HashtagRequest;
import wods.crossfit.hashtag.repository.HashtagRepository;
import wods.crossfit.hashtag.service.HashtagService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HashtagServiceImpl implements HashtagService {

    private final HashtagRepository hashtagRepository;

    @Override
    @Transactional
    public List<Hashtag> saveHashtag(List<HashtagRequest> hashtags) {

        return hashtagRepository.saveAll(
                new HashtagRequest().toEntity(hashtags));
    }

    @Override
    public List<Hashtag> updateHashtag(long workoutId, List<HashtagRequest> hashtags) {

        return hashtagRepository.saveAll(
                new HashtagRequest().toEntity(hashtags));
    }

    @Override
    public List<String> getHashtags() {
        return hashtagRepository.findHashtags();
    }
}
