package wods.crossfit.profileBenchmark.service;

import wods.crossfit.profileBenchmark.domain.dto.ProfileBenchMarkDto.saveProfileBenchmarkRecordRequest;
import wods.crossfit.profileBenchmark.domain.dto.ProfileBenchMarkDto.saveProfileBenchmarkRequest;

public interface ProfileBenchmarkService {

    void save(saveProfileBenchmarkRequest dto);

    void update(long id, saveProfileBenchmarkRecordRequest dto);

    void delete(long id);
}
