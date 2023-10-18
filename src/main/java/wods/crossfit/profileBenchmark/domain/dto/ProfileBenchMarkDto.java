package wods.crossfit.profileBenchmark.domain.dto;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wods.crossfit.benchmark.domain.Benchmark;
import wods.crossfit.benchmark.domain.dto.BenchmarkDto;
import wods.crossfit.profile.domain.Profile;
import wods.crossfit.profileBenchmark.domain.ProfileBenchmark;

public class ProfileBenchMarkDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProfileBenchmarkRequest {

        private long benchmarkId;
        private long profileId;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProfileBenchmarkRequestEntity {

        private Profile profile;
        private Benchmark benchmark;

        public ProfileBenchmark toEntity(Benchmark benchmark, Profile profile) {
            return ProfileBenchmark.builder()
                    .benchmark(benchmark)
                    .profile(profile)
                    .build();
        }

        public List<ProfileBenchmark> toEntity(
                List<ProfileBenchmarkRequestEntity> profileBenchmarkRequestEntities) {
            return profileBenchmarkRequestEntities.stream()
                    .map(x -> x.toEntity(x.getBenchmark(), x.getProfile()))
                    .collect(Collectors.toList());
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProfileBenchmarkRequestRecord {

        private String record;

        public ProfileBenchmark toEntity(Profile profile, Benchmark benchmark) {
            return ProfileBenchmark.builder()
                    .profile(profile)
                    .benchmark(benchmark)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProfileBenchmarkResponse {

        private long id;
        private BenchmarkDto.BenchmarkResponse benchmark;
        private String record;

        public ProfileBenchmarkResponse(ProfileBenchmark profileBenchmark) {
            this.id = profileBenchmark.getId();
            this.benchmark = new BenchmarkDto.BenchmarkResponse(profileBenchmark.getBenchmark());
            this.record = profileBenchmark.getRecord();
        }
    }

}
