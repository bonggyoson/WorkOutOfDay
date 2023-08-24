package wods.crossfit.hashtag.service;

import java.util.List;
import wods.crossfit.hashtag.domain.dto.HashtagDto;

public interface HashtagService {

    void saveHashtag(List<HashtagDto.HashtagRequest> hashtags, long id);

    List<String> getHashtags();
}
