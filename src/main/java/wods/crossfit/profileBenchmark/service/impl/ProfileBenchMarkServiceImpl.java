package wods.crossfit.profileBenchmark.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wods.crossfit.benchmark.domain.Benchmark;
import wods.crossfit.profile.domain.Profile;
import wods.crossfit.profileBenchmark.domain.ProfileBenchmark;
import wods.crossfit.profileBenchmark.domain.dto.ProfileBenchMarkDto.ProfileBenchmarkRequest;
import wods.crossfit.profileBenchmark.domain.dto.ProfileBenchMarkDto.ProfileBenchmarkRequestEntity;
import wods.crossfit.profileBenchmark.domain.dto.ProfileBenchMarkDto.ProfileBenchmarkRequestRecord;
import wods.crossfit.profileBenchmark.domain.dto.ProfileBenchMarkDto.ProfileBenchmarkResponse;
import wods.crossfit.benchmark.repository.BenchmarkRepository;
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
    public void saveProfileBenchmark(List<ProfileBenchmarkRequest> profileBenchmarks) {

        List<ProfileBenchmarkRequestEntity> profileBenchmarkRequestEntities = new ArrayList<>();

        for (ProfileBenchmarkRequest profileBenchmarkRequest : profileBenchmarks) {
            Profile profile = profileRepository.findById(
                            profileBenchmarkRequest.getProfileId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            String.format("Not Found Profile Id : [%S]",
                                    profileBenchmarkRequest.getProfileId())
                    ));

            Benchmark benchmark = benchmarkRepository.findById(
                            profileBenchmarkRequest.getBenchmarkId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            String.format("Not Found Benchmark Id : [%S]",
                                    profileBenchmarkRequest.getBenchmarkId())));

            profileBenchmarkRequestEntities.add(
                    new ProfileBenchmarkRequestEntity(profile, benchmark));
        }

        profileBenchMarkRepository.saveAll(
                new ProfileBenchmarkRequestEntity().toEntity(profileBenchmarkRequestEntities));
    }

    @Override
    @Transactional
    public void saveProfileBenchmarkRecordRequest(long id, ProfileBenchmarkRequestRecord dto) {
        ProfileBenchmark profileBenchmark = profileBenchMarkRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Not Found Id : [%S]", id)));

        profileBenchmark.updateRecord(dto.getRecord());
    }

    @Override
    @Transactional
    public void deleteProfileBenchmark(long id) {
        profileBenchMarkRepository.deleteById(id);
    }

    @Override
    public List<ProfileBenchmarkResponse> findProfileBenchmark(long id) {
        return profileBenchMarkRepository.findByProfileId(id).stream()
                .map(ProfileBenchmarkResponse::new).
                collect(Collectors.toList());
    }
}
