package wods.crossfit.benchmark.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wods.crossfit.benchmark.domain.Benchmark;

public class BenchmarkDto {

    @Getter
    @NoArgsConstructor
    public static class BenchmarkRequest {

        private String title;
        private String content;

        public Benchmark toEntity() {
            return Benchmark.builder()
                    .title(title)
                    .content(content)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class BenchmarkResponse {

        private long id;
        private String title;
        private String content;

        public BenchmarkResponse(Benchmark benchmark) {
            this.id = benchmark.getId();
            this.title = benchmark.getTitle();
            this.content = benchmark.getContent();
        }
    }

}
