package wods.crossfit.profileBenchmark.service;

import java.util.List;
import wods.crossfit.profileBenchmark.domain.dto.ProfileBenchMarkDto.ProfileBenchmarkRequest;
import wods.crossfit.profileBenchmark.domain.dto.ProfileBenchMarkDto.ProfileBenchmarkRequestRecord;
import wods.crossfit.profileBenchmark.domain.dto.ProfileBenchMarkDto.ProfileBenchmarkResponse;

public interface ProfileBenchmarkService {

    void saveProfileBenchmark(List<ProfileBenchmarkRequest> dto);

    void saveProfileBenchmarkRecordRequest(long id, ProfileBenchmarkRequestRecord dto);

    void deleteProfileBenchmark(long id);

    List<ProfileBenchmarkResponse> findProfileBenchmark(long id);

}
