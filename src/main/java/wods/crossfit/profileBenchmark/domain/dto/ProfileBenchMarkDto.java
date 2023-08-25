package wods.crossfit.profileBenchmark.domain.dto;

import java.util.List;
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
    public static class saveProfileBenchmarkRequest {

        private List<Long> id;
        private long profileId;

        public ProfileBenchmark toEntity(Profile profile, Benchmark benchmark) {
            return ProfileBenchmark.builder()
                    .profile(profile)
                    .benchmark(benchmark)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class saveProfileBenchmarkRecordRequest {

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
