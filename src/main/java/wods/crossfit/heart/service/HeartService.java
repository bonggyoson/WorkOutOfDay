package wods.crossfit.heart.service;

import wods.crossfit.heart.domain.dto.HeartDto;

public interface HeartService {

    void saveHeart(HeartDto.HeartRequest dto);

    void deleteHeart(HeartDto.HeartRequest dto);

    boolean checkLike(long memberId, long workoutId);

}
