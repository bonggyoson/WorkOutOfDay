package wods.crossfit.profileBenchmark.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import wods.crossfit.benchmark.domain.Benchmark;
import wods.crossfit.profile.domain.Profile;
import wods.crossfit.profileBenchmark.domain.ProfileBenchmark;
import wods.crossfit.profileBenchmark.domain.dto.ProfileBenchMarkDto.saveProfileBenchmarkRecordRequest;
import wods.crossfit.benchmark.repository.BenchmarkRepository;
import wods.crossfit.profileBenchmark.domain.dto.ProfileBenchMarkDto.saveProfileBenchmarkRequest;
import wods.crossfit.profileBenchmark.repository.ProfileBenchmarkRepository;
import wods.crossfit.profile.repository.ProfileRepository;
import wods.crossfit.profileBenchmark.service.ProfileBenchmarkService;

@Service
@RequiredArgsConstructor
public class ProfileBenchMarkServiceImpl implements ProfileBenchmarkService {

    private final ProfileBenchmarkRepository profileBenchMarkRepository;

    private final ProfileRepository profileRepository;

    private final BenchmarkRepository benchmarkRepository;

    @Override
    @Transactional
    public void save(saveProfileBenchmarkRequest dto) {
        Profile profile = profileRepository.findById(dto.getProfileId())
                .orElseThrow(() -> new NotFoundException("해당 프로필이 존재하지 않습니다."));

        for (long id : dto.getId()) {
            Benchmark benchmark = benchmarkRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("해당 벤치마크가 존재하지 않습니다."));

            profileBenchMarkRepository.save(dto.toEntity(profile, benchmark));
        }
    }

    @Override
    @Transactional
    public void update(long id, saveProfileBenchmarkRecordRequest dto) {
        ProfileBenchmark profileBenchmark = profileBenchMarkRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 벤치마크가 존재하지 않습니다."));

        profileBenchmark.updateRecord(dto.getRecord());
    }

    @Override
    @Transactional
    public void delete(long id) {
        profileBenchMarkRepository.deleteById(id);
    }
}
