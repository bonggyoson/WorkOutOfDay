package wods.crossfit.benchmark.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wods.crossfit.global.common.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Benchmark extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "benchmark_id")
    private Long id;

    @Column(name = "benchmark_title", nullable = false)
    private String title;

    @Column(name = "benchmark_content", nullable = false)
    private String content;

    @Builder
    public Benchmark(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
