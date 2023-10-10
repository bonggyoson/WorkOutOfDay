package wods.crossfit.hashtag.service;

import java.util.List;
import wods.crossfit.hashtag.domain.Hashtag;
import wods.crossfit.hashtag.domain.dto.HashtagDto.HashtagRequest;

public interface HashtagService {

    List<Hashtag> saveHashtag(List<HashtagRequest> hashtags);

    List<Hashtag> updateHashtag(long workoutId, List<HashtagRequest> hashtags);

    List<String> getHashtags();

}
