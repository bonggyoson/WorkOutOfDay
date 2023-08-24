package wods.crossfit.profileBenchmark.service;

import wods.crossfit.profileBenchmark.domain.dto.ProfileBenchMarkDto.recordRequest;
import wods.crossfit.profileBenchmark.domain.dto.ProfileBenchMarkDto.saveRequest;

public interface ProfileBenchmarkService {

    void save(saveRequest dto);

    void update(long id, recordRequest dto);

    void delete(long id);
}
